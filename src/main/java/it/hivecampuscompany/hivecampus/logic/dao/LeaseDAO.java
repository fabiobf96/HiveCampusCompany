package it.hivecampuscompany.hivecampus.logic.dao;

import it.hivecampuscompany.hivecampus.logic.model.Account;
import it.hivecampuscompany.hivecampus.logic.model.Lease;

public interface LeaseDAO {
    void saveLease(Lease lease);
    void retrieveLeaseRoom(Account account);
}
