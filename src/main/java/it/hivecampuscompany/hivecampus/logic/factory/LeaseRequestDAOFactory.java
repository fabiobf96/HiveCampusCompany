package it.hivecampuscompany.hivecampus.logic.factory;

import it.hivecampuscompany.hivecampus.logic.dao.LeaseRequestDAO;
import it.hivecampuscompany.hivecampus.logic.dao.csv.LeaseRequestDAOCSV;
import it.hivecampuscompany.hivecampus.logic.dao.mysql.LeaseRequestDAOMySql;

public class LeaseRequestDAOFactory implements Factory{
    @Override
    public LeaseRequestDAO getDAO(String typePersistence) throws IllegalArgumentException{
        return switch (typePersistence){
            case "csv" -> createLeaseRequestDAOCSV();
            case "mysql" -> createLeaseRequestDAOMySql();
            default -> throw new IllegalArgumentException("Unsupported persistence type: " + typePersistence);
        };
    }
    public LeaseRequestDAO createLeaseRequestDAOCSV(){
        return new LeaseRequestDAOCSV();
    }
    public LeaseRequestDAO createLeaseRequestDAOMySql(){
        return new LeaseRequestDAOMySql();
    }

}
