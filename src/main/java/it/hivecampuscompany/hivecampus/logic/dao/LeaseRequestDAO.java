package it.hivecampuscompany.hivecampus.logic.dao;

import it.hivecampuscompany.hivecampus.logic.model.LeaseRequest;
import it.hivecampuscompany.hivecampus.logic.model.Room;
import it.hivecampuscompany.hivecampus.logic.model.Tenant;

import java.util.List;

public interface LeaseRequestDAO {
    void saveLeaseRequest(LeaseRequest leaseRequest);
    List<LeaseRequest> retrieveLeaseRequestsByRoom(Room room);
    List<LeaseRequest> retrieveLeaseRequestsByTenant(Tenant tenant);
}
