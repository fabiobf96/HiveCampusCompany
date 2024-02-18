package it.hivecampuscompany.hivecampus.logic.factory;

import it.hivecampuscompany.hivecampus.logic.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.logic.dao.csv.AccountDAOCSV;
import it.hivecampuscompany.hivecampus.logic.dao.mysql.AccountDAOMySql;

public class AccountDAOFactory implements Factory{
    @Override
    public AccountDAO getDAO(String typePersistence) throws IllegalArgumentException{
        return switch (typePersistence){
            case "csv" -> createAccountDAOCSV();
            case "mysql" -> createAccountDAOMySql();
            default -> throw new IllegalArgumentException("Unsupported persistence type: " + typePersistence);
        };
    }
    public AccountDAO createAccountDAOCSV(){
        return new AccountDAOCSV();
    }
    public AccountDAO createAccountDAOMySql(){
        return new AccountDAOMySql();
    }
}
