package it.hivecampuscompany.hivecampus.logic.control;

import it.hivecampuscompany.hivecampus.logic.bean.*;
import it.hivecampuscompany.hivecampus.logic.boundary.OpenAPIBoundary;
import it.hivecampuscompany.hivecampus.logic.dao.LeaseDAO;
import it.hivecampuscompany.hivecampus.logic.dao.LeaseRequestDAO;
import it.hivecampuscompany.hivecampus.logic.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.logic.exception.EmptyFieldsException;
import it.hivecampuscompany.hivecampus.logic.exception.EmptyListException;
import it.hivecampuscompany.hivecampus.logic.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.logic.exception.OTPDismatchException;
import it.hivecampuscompany.hivecampus.logic.facade.DAOFactoryFacade;
import it.hivecampuscompany.hivecampus.logic.model.Account;
import it.hivecampuscompany.hivecampus.logic.model.Lease;
import it.hivecampuscompany.hivecampus.logic.model.LeaseRequest;
import it.hivecampuscompany.hivecampus.logic.model.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LeaseManager {
    private final LeaseDAO leaseDAO;
    private final RoomDAO roomDAO;
    private final LeaseRequestDAO leaseRequestDAO;
    private final SessionManager sessionManager;
    private final Account owner;
    private List<Room> rooms;

    private static final Logger LOGGER = Logger.getLogger(LeaseManager.class.getName());

    public LeaseManager(SessionBean sessionBean) throws InvalidSessionException {
        sessionManager = SessionManager.getInstance();
        verifySessionBean(sessionBean);
        owner = sessionManager.getAccount(sessionBean);
        DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
        leaseDAO = daoFactoryFacade.getLeaseDAO();
        leaseRequestDAO = daoFactoryFacade.getLeaseRequestDAO();
        roomDAO = daoFactoryFacade.getRoomDAO();
    }

    public List<PreviewRoomBean> fetchLeaseRequest(SessionBean sessionBean) throws InvalidSessionException, EmptyFieldsException, EmptyListException {
        verifySessionBean(sessionBean);
        List<PreviewRoomBean> previewRoomBeans = new ArrayList<>();
        rooms = (roomDAO.retrieveRoomsByOwner(owner));
        if (rooms.isEmpty()){
            throw new EmptyFieldsException("You haven't posted any ads yet");
        }
        for (Room room : rooms){
            if (Boolean.TRUE.equals(room.getAvailable())){
                leaseRequestDAO.retrieveLeaseRequestsByRoom(room);
                setAcceptedRequest(room);
            }
        }
        for (Room room : rooms){
            PreviewRoomBean previewRoomBean = getPreviewRoomBean(room);
            previewRoomBeans.add(previewRoomBean);
        }

        if (previewRoomBeans.isEmpty()){
            throw new EmptyListException("There are no rooms to rent");
        }
        return previewRoomBeans;
    }

    private static PreviewRoomBean getPreviewRoomBean(Room room) {
        PreviewRoomBean previewRoomBean = new PreviewRoomBean(room);
        List<LeaseRequestBean> leaseRequestBeans = new ArrayList<>();

        for (LeaseRequest leaseRequest : room.getLeaseRequests()){
            AccountBean accountBean = new AccountBean(leaseRequest.getAccount());
            LeaseRequestBean leaseRequestBean = new LeaseRequestBean(leaseRequest);
            leaseRequestBean.setTenant(accountBean);
            leaseRequestBeans.add(leaseRequestBean);
        }
        previewRoomBean.setLeaseRequestBean(leaseRequestBeans);
        return previewRoomBean;
    }

    public List<PreviewRoomBean> manageLeaseRequest(SessionBean sessionBean, LeaseRequestBean leaseRequestBean) throws InvalidSessionException {
        verifySessionBean(sessionBean);
        List <PreviewRoomBean> previewRoomBeans = new ArrayList<>();
        for (Room room : rooms){
            for (LeaseRequest leaseRequest : room.getLeaseRequests()){
                if(leaseRequest.getId() == leaseRequestBean.getId()){
                    leaseRequest.setStatusRequest(leaseRequestBean.getStatusRequest());
                    setAcceptedRequest(room);
                    leaseRequestDAO.updateLeaseRequest(leaseRequest);
                }
            }
            PreviewRoomBean previewRoomBean = getPreviewRoomBean(room);
            previewRoomBeans.add(previewRoomBean);
        }
        return previewRoomBeans;
    }

    private void setAcceptedRequest(Room room) {
        for(LeaseRequest leaseRequest : room.getLeaseRequests()){
            if(leaseRequest.getStatusRequest().equals("accettata")){
                List<LeaseRequest> leaseRequests = new ArrayList<>();
                leaseRequests.add(leaseRequest);
                room.setLeaseRequests(leaseRequests);
                return;
            }
        }
    }

    public void createLease(SessionBean sessionBean, LeaseBean leaseBean) throws InvalidSessionException, EmptyListException {
        verifySessionBean(sessionBean);
        Lease lease;
        for (Room room : rooms){
            if(room.getIdRoom().equals(leaseBean.getPreviewRoomBean().getIdRoom())){
                leaseRequestDAO.retrieveLeaseRequestsByRoom(room);
                List<LeaseRequest> leaseRequests = room.getLeaseRequests();
                lease = new Lease(room, leaseRequests.getFirst().getAccount(), leaseBean.getFile(), leaseBean.isActive());
                for (LeaseRequest leaseRequest : leaseRequests){
                    lease.registerObserver(leaseRequest);
                }
                leaseDAO.saveLease(lease);
                room.setLease(lease);
                //Simulazione firma contratto
                retrieveContract(sessionBean, room);
                return;
            }
        }
    }

    public void retrieveContract(SessionBean sessionBean, Room room){
        try {
            verifySessionBean(sessionBean);
            Lease lease = leaseDAO.retrieveLeaseRoom(room);
            signContract(sessionBean, lease);
        } catch (EmptyListException | InvalidSessionException | OTPDismatchException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            System.exit(2);
        }
    }
    public void signContract(SessionBean sessionBean, Lease lease) throws OTPDismatchException, InvalidSessionException {
        verifySessionBean(sessionBean);
        OpenAPIBoundary openAPIBoundary = new OpenAPIBoundary();
        lease.setSignedContract(openAPIBoundary.mokeFirmaDigitale(lease.getFile(), "AAAAAAAA"));
        Room room = lease.getRoom();
        room.setAvailable(false);
        roomDAO.updateAvailability(room);
    }

    private void verifySessionBean(SessionBean sessionBean) throws InvalidSessionException {
        if(sessionManager.isInvalid(sessionBean)){
            throw new InvalidSessionException();
        }
    }

}
