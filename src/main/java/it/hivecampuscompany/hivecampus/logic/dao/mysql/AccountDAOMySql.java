package it.hivecampuscompany.hivecampus.logic.dao.mysql;

import it.hivecampuscompany.hivecampus.logic.bean.CredentialsBean;
import it.hivecampuscompany.hivecampus.logic.control.ConnectionManager;
import it.hivecampuscompany.hivecampus.logic.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.logic.dao.queries.CRUDQueries;
import it.hivecampuscompany.hivecampus.logic.dao.queries.SimpleQueries;
import it.hivecampuscompany.hivecampus.logic.model.Account;

import javax.security.auth.login.FailedLoginException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountDAOMySql implements AccountDAO {

    private final Connection connection;
    public AccountDAOMySql(){
        connection = ConnectionManager.getConnection();
    }

    @Override
    public Account retrieveAccountByCredentials(CredentialsBean credentialsBean) throws SQLException, FailedLoginException {

        Statement statement = connection.createStatement();
        ResultSet res = SimpleQueries.checkAccount(statement, credentialsBean);

        if (!res.first()) { // se res è vuoto, non esiste un account con le credenziali passate
            throw new FailedLoginException("Login failed.");
        }

        // riposizionamento del cursore
        res.first();

        String storedEmail = res.getString("username");
        String storedPassword = res.getString("password");
        String storedAccountType = res.getString("ruolo");
        String storedName = res.getString("nome");
        String storedSurname = res.getString("cognome");
        String storedPhoneNumber = res.getString("telefono");

        // chiudo risorsa ResulSet
        res.close();

        // Se account non è null, restituisci l'oggetto Account
        return new Account(storedEmail, storedPassword, storedName, storedSurname, storedAccountType, storedPhoneNumber);
    }

    @Override
    public void saveAccount(Account account) throws SQLException {
        CRUDQueries.insertAccount(connection, account);

        String typeAccount = account.getTypeAccount();

        if (typeAccount.equalsIgnoreCase("owner")) {
            CRUDQueries.insertOwner(connection, account);
        } else {
            CRUDQueries.insertTenant(connection, account);
        }
    }

    @Override
    public Account retrieveAccountDetails(String email) {
        return null;
    }
}
