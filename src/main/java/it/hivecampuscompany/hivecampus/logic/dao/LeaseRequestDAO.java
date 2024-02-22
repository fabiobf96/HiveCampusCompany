package it.hivecampuscompany.hivecampus.logic.dao;

import it.hivecampuscompany.hivecampus.logic.exception.EmptyListException;
import it.hivecampuscompany.hivecampus.logic.model.Account;
import it.hivecampuscompany.hivecampus.logic.model.LeaseRequest;
import it.hivecampuscompany.hivecampus.logic.model.Room;


public interface LeaseRequestDAO {
    void saveLeaseRequest(LeaseRequest leaseRequest);
    void retrieveLeaseRequestsByRoom(Room room) throws EmptyListException;

    void updateLeaseRequest(LeaseRequest updatedLeaseRequest);

    boolean hasActiveLeaseRequest(Account account, Integer idRoom);
}
