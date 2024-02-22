package it.hivecampuscompany.hivecampus.logic.dao;

import it.hivecampuscompany.hivecampus.logic.exception.EmptyListException;
import it.hivecampuscompany.hivecampus.logic.model.Lease;
import it.hivecampuscompany.hivecampus.logic.model.Room;

public interface LeaseDAO {
    void saveLease(Lease lease);
    Lease retrieveLeaseRoom(Room room) throws EmptyListException;
}
