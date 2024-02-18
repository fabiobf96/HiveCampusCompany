package it.hivecampuscompany.hivecampus.logic.factory;

import it.hivecampuscompany.hivecampus.logic.dao.LeaseDAO;
import it.hivecampuscompany.hivecampus.logic.dao.csv.LeaseDAOCSV;
import it.hivecampuscompany.hivecampus.logic.dao.mysql.LeaseDAOMySql;

public class LeaseDAOFactory implements Factory{
    @Override
    public LeaseDAO getDAO(String typePersistence) throws IllegalArgumentException{
        return switch (typePersistence){
            case "csv" -> createLeaseDAOCSV();
            case "mysql" -> createLeaseDAOMySql();
            default -> throw new IllegalArgumentException("Unsupported persistence type: " + typePersistence);
        };
    }
    public LeaseDAO createLeaseDAOCSV(){
        return new LeaseDAOCSV();
    }
    public LeaseDAO createLeaseDAOMySql(){
        return new LeaseDAOMySql();
    }

}
