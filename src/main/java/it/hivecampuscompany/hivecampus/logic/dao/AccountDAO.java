package it.hivecampuscompany.hivecampus.logic.dao;

import it.hivecampuscompany.hivecampus.logic.bean.AccountBean;
import it.hivecampuscompany.hivecampus.logic.bean.CredentialsBean;
import it.hivecampuscompany.hivecampus.logic.exception.AuthenticateException;
import it.hivecampuscompany.hivecampus.logic.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.logic.model.Account;

public interface AccountDAO {
    Account retrieveAccountByCredentials(CredentialsBean credentialsBean) throws AuthenticateException;
    void saveAccount(Account account) throws DuplicateRowException;
    AccountBean retrieveAccountDetails(CredentialsBean credentialsBean);
}
