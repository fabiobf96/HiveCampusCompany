package it.hivecampuscompany.hivecampus.logic.dao;

import it.hivecampuscompany.hivecampus.logic.bean.AccountBean;
import it.hivecampuscompany.hivecampus.logic.bean.CredentialsBean;
import it.hivecampuscompany.hivecampus.logic.exception.AuthenticateException;
import it.hivecampuscompany.hivecampus.logic.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.logic.model.Account;

import javax.security.auth.login.FailedLoginException;
import java.sql.SQLException;

public interface AccountDAO {
    Account retrieveAccountByCredentials(CredentialsBean credentialsBean) throws AuthenticateException, SQLException, FailedLoginException;
    void saveAccount(Account account) throws DuplicateRowException, SQLException;
    Account retrieveAccountDetails(String email);
}
