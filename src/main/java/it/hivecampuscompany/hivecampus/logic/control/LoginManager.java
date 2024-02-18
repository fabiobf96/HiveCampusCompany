package it.hivecampuscompany.hivecampus.logic.control;

import it.hivecampuscompany.hivecampus.logic.bean.AccountBean;
import it.hivecampuscompany.hivecampus.logic.bean.CredentialsBean;
import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import it.hivecampuscompany.hivecampus.logic.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.logic.exception.AuthenticateException;
import it.hivecampuscompany.hivecampus.logic.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.logic.facade.DAOFactoryFacade;
import it.hivecampuscompany.hivecampus.logic.model.Account;
import it.hivecampuscompany.hivecampus.logic.model.Session;
import it.hivecampuscompany.hivecampus.logic.utility.EncryptionPassword;

import javax.security.auth.login.FailedLoginException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;

public class LoginManager {
    AccountDAO accountDAO;
    Session session;
    Account account;


    public LoginManager(){
        DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
        accountDAO = daoFactoryFacade.getAccountDAO();
    }

    public SessionBean searchAccountByCredentials(CredentialsBean credentials) throws AuthenticateException, FailedLoginException, SQLException, NoSuchAlgorithmException {
        credentials.setPassword(Objects.requireNonNull(EncryptionPassword.hashPasswordMD5(credentials.getPassword())));
        account = accountDAO.retrieveAccountByCredentials(credentials);
        SessionManager sessionManager = SessionManager.getInstance();
        this.session = sessionManager.createSession(account);
        return new SessionBean(session);
    }
    public void createAccount(AccountBean accountBean) throws DuplicateRowException, SQLException, NoSuchAlgorithmException {
        accountBean.setPassword(Objects.requireNonNull(EncryptionPassword.hashPasswordMD5(accountBean.getPassword())));
        account = new Account(accountBean);
        accountDAO.saveAccount(account);
    }

}
