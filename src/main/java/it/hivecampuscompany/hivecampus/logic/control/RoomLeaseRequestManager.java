package it.hivecampuscompany.hivecampus.logic.control;

import it.hivecampuscompany.hivecampus.logic.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.logic.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.logic.bean.RoomBean;
import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import it.hivecampuscompany.hivecampus.logic.dao.LeaseRequestDAO;
import it.hivecampuscompany.hivecampus.logic.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.logic.exception.EmptyListException;
import it.hivecampuscompany.hivecampus.logic.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.logic.facade.DAOFactoryFacade;
import it.hivecampuscompany.hivecampus.logic.model.Account;
import it.hivecampuscompany.hivecampus.logic.model.LeaseRequest;
import it.hivecampuscompany.hivecampus.logic.model.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoomLeaseRequestManager {

    private final RoomDAO roomDAO;
    private final LeaseRequestDAO leaseRequestDAO;
    private final SessionManager sessionManager;
    List<Room> rooms;
    Account tenant;
    public RoomLeaseRequestManager(SessionBean sessionBean) throws InvalidSessionException {
        sessionManager = SessionManager.getInstance();
        verifySessionBean(sessionBean);
        tenant = SessionManager.getInstance().getAccount(sessionBean);
        DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
        roomDAO = daoFactoryFacade.getRoomDAO();
        leaseRequestDAO = daoFactoryFacade.getLeaseRequestDAO();
    }
    public List<RoomBean> searchRoomsByFilters(SessionBean sessionBean, FiltersBean filtersBean) throws EmptyListException, InvalidSessionException {
        verifySessionBean(sessionBean);
        List<RoomBean> roomBeans = new ArrayList<>();
        rooms = roomDAO.retrieveRoomsByFilters(filtersBean);
        if (rooms.isEmpty()){
            throw new EmptyListException("No result for this result set");
        }
        for (Room room : rooms){
            roomBeans.add(new RoomBean(room));
        }
        return roomBeans;
    }

    public void sendLeaseRequest(SessionBean sessionBean, LeaseRequestBean leaseRequestBean) throws InvalidSessionException {
        verifySessionBean(sessionBean);
        LeaseRequest leaseRequest = new LeaseRequest(leaseRequestBean);
        leaseRequest.setAccount(tenant);
        for (Room room : rooms){
            if (Objects.equals(room.getIdRoom(), leaseRequestBean.getRoomBean())){
                leaseRequest.setRoom(room);
            }
        }
        leaseRequestDAO.saveLeaseRequest(leaseRequest);
    }

    private void verifySessionBean(SessionBean sessionBean) throws InvalidSessionException {
        if(!sessionManager.isValid(sessionBean)){
            throw new InvalidSessionException();
        }
    }
}
