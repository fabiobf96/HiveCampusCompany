package it.hivecampuscompany.hivecampus.logic.dao.mysql;

import it.hivecampuscompany.hivecampus.logic.dao.LeaseRequestDAO;
import it.hivecampuscompany.hivecampus.logic.model.LeaseRequest;
import it.hivecampuscompany.hivecampus.logic.model.Room;
import it.hivecampuscompany.hivecampus.logic.model.Tenant;

import java.util.List;

public class LeaseRequestDAOMySql implements LeaseRequestDAO {
    @Override
    public void saveLeaseRequest(LeaseRequest leaseRequest) {

    }

    @Override
    public List<LeaseRequest> retrieveLeaseRequestsByRoom(Room room) {
        return null;
    }

    @Override
    public List<LeaseRequest> retrieveLeaseRequestsByTenant(Tenant tenant) {
        return null;
    }
}
