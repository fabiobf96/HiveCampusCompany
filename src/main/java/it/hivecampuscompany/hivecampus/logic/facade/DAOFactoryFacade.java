package it.hivecampuscompany.hivecampus.logic.facade;

import it.hivecampuscompany.hivecampus.logic.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.logic.dao.LeaseDAO;
import it.hivecampuscompany.hivecampus.logic.dao.LeaseRequestDAO;
import it.hivecampuscompany.hivecampus.logic.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.logic.factory.AccountDAOFactory;
import it.hivecampuscompany.hivecampus.logic.factory.LeaseDAOFactory;
import it.hivecampuscompany.hivecampus.logic.factory.LeaseRequestDAOFactory;
import it.hivecampuscompany.hivecampus.logic.factory.RoomDAOFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOFactoryFacade {
    private static final Logger LOGGER = Logger.getLogger(DAOFactoryFacade.class.getName());
    private static DAOFactoryFacade instance;
    private String persistenceType;
    private AccountDAO accountDAO;
    private LeaseDAO leaseDAO;
    private RoomDAO roomDAO;
    private LeaseRequestDAO leaseRequestDAO;

    private DAOFactoryFacade() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("properties/config.properties")) {
            properties.load(input);
            persistenceType = properties.getProperty("PERSISTENCE_MYSQL");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load config.properties file", e);
            System.exit(1);
        }
    }

    public static synchronized DAOFactoryFacade getInstance() {
        if (instance == null) {
            instance = new DAOFactoryFacade();
        }
        return instance;
    }

    public AccountDAO getAccountDAO() throws IllegalArgumentException{
        if (accountDAO == null) {
            AccountDAOFactory accountDAOFactory = new AccountDAOFactory();
            accountDAO = accountDAOFactory.getDAO(persistenceType);
        }
        return accountDAO;
    }

    public LeaseDAO getLeaseDAO() throws IllegalArgumentException{
        if (leaseDAO == null) {
            LeaseDAOFactory leaseDAOFactory = new LeaseDAOFactory();
            leaseDAO = leaseDAOFactory.getDAO(persistenceType);
        }
        return leaseDAO;
    }

    public LeaseRequestDAO getLeaseRequestDAO() throws IllegalArgumentException{
        if (leaseRequestDAO == null) {
            LeaseRequestDAOFactory leaseRequestDAOFactory = new LeaseRequestDAOFactory();
            leaseRequestDAO = leaseRequestDAOFactory.getDAO(persistenceType);
        }
        return leaseRequestDAO;
    }

    public RoomDAO getRoomDAO() throws IllegalArgumentException{
        if (roomDAO == null){
            RoomDAOFactory roomDAOFactory = new RoomDAOFactory();
            roomDAO = roomDAOFactory.getDAO(persistenceType);
        }
        return roomDAO;
    }
    public static void main(String[] args){
        DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
        daoFactoryFacade.getAccountDAO();
    }
}
