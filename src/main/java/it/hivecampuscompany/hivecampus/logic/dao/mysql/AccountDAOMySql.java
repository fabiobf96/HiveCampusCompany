package it.hivecampuscompany.hivecampus.logic.dao.mysql;

import it.hivecampuscompany.hivecampus.logic.bean.AccountBean;
import it.hivecampuscompany.hivecampus.logic.bean.CredentialsBean;
import it.hivecampuscompany.hivecampus.logic.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.logic.model.Account;

public class AccountDAOMySql implements AccountDAO {
    public AccountDAOMySql(){
        System.out.println("AccountDAO mysql");
    }
    @Override
    public Account retrieveAccountByCredentials(CredentialsBean credentialsBean) {
        return null;
    }

    @Override
    public void saveAccount(Account account) {

    }

    @Override
    public AccountBean retrieveAccountDetails(CredentialsBean credentialsBean) {
        return null;
    }
}
