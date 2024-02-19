package it.hivecampuscompany.hivecampus.logic.dao.csv;

import it.hivecampuscompany.hivecampus.logic.dao.LeaseDAO;
import it.hivecampuscompany.hivecampus.logic.model.Account;
import it.hivecampuscompany.hivecampus.logic.model.Lease;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LeaseDAOCSV implements LeaseDAO {
    File fd;
    private static final Logger LOGGER = Logger.getLogger(AccountDAOCSV.class.getName());

    public LeaseDAOCSV(){

    }

    @Override
    public void saveLease(Lease lease) {

    }

    @Override
    public void retrieveLeaseRoom(Account account) {

    }
}
