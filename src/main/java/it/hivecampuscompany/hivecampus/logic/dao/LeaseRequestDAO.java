package it.hivecampuscompany.hivecampus.logic.dao;

import it.hivecampuscompany.hivecampus.logic.exception.EmptyListException;
import it.hivecampuscompany.hivecampus.logic.model.LeaseRequest;
import it.hivecampuscompany.hivecampus.logic.model.Room;
import it.hivecampuscompany.hivecampus.logic.model.Tenant;

import java.util.List;

public interface LeaseRequestDAO {
    void saveLeaseRequest(LeaseRequest leaseRequest);
    void retrieveLeaseRequestsByRoom(Room room) throws EmptyListException;
    List<LeaseRequest> retrieveLeaseRequestsByTenant(Tenant tenant);
}
