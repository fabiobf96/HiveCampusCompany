package it.hivecampuscompany.hivecampus.logic.control;

import it.hivecampuscompany.hivecampus.logic.dao.LeaseDAO;
import it.hivecampuscompany.hivecampus.logic.facade.DAOFactoryFacade;
import it.hivecampuscompany.hivecampus.logic.model.Lease;
import it.hivecampuscompany.hivecampus.logic.model.Owner;
import it.hivecampuscompany.hivecampus.logic.model.Room;

import java.util.List;

public class LeaseManager {
    LeaseDAO leaseDAO;
    public LeaseManager(){
        DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
        leaseDAO = daoFactoryFacade.getLeaseDAO();
    }

    public List<Room> searchAvailableRoom(Owner owner){
        return null;
    }

}
