package it.hivecampuscompany.hivecampus.logic.dao.csv;

import it.hivecampuscompany.hivecampus.logic.dao.LeaseRequestDAO;
import it.hivecampuscompany.hivecampus.logic.model.LeaseRequest;
import it.hivecampuscompany.hivecampus.logic.model.Room;
import it.hivecampuscompany.hivecampus.logic.model.Tenant;

import java.util.List;

public class LeaseRequestDAOCSV implements LeaseRequestDAO {
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
