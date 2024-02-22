package it.hivecampuscompany.hivecampus.logic.dao.mysql;

import it.hivecampuscompany.hivecampus.logic.control.ConnectionManager;
import it.hivecampuscompany.hivecampus.logic.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.logic.dao.LeaseRequestDAO;
import it.hivecampuscompany.hivecampus.logic.dao.csv.AccountDAOCSV;
import it.hivecampuscompany.hivecampus.logic.facade.DAOFactoryFacade;
import it.hivecampuscompany.hivecampus.logic.model.Account;
import it.hivecampuscompany.hivecampus.logic.model.LeaseRequest;
import it.hivecampuscompany.hivecampus.logic.model.Room;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LeaseRequestDAOMySql implements LeaseRequestDAO {
    private final Connection conn;
    private final Properties properties;
    private static final String LEASE_STATUS = "statoRichiesta";
    private static final Logger LOGGER = Logger.getLogger(AccountDAOCSV.class.getName());


    public LeaseRequestDAOMySql(){
        conn = ConnectionManager.getConnection();
        properties = new Properties();
        try (InputStream input = new FileInputStream("properties/db.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error loading database properties.", e);
        }
    }
    @Override
    public void saveLeaseRequest(LeaseRequest leaseRequest) {
        String query = "INSERT INTO hivecampus.richiesta_di_affitto(stanza, affittuario, inizioPermanenza, tipoPermanenza) VALUES (?, ?, ?, ?);";
        System.out.println(leaseRequest.getRoom().getIdRoom() + " " + leaseRequest.getAccount().getEmail() + " " + leaseRequest.getStartPermanence() + " " + leaseRequest.getTypePermanence());
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, leaseRequest.getRoom().getIdRoom());
            stmt.setString(2, leaseRequest.getAccount().getEmail());
            stmt.setString(3, leaseRequest.getStartPermanence());
            stmt.setString(4, leaseRequest.getTypePermanence());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateLeaseRequest(LeaseRequest updatedLeaseRequest) {
        String query = properties.getProperty("UPDATE_LEASE_REQUEST");
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, updatedLeaseRequest.getRoom().getIdRoom());
            stmt.setString(2, updatedLeaseRequest.getTypePermanence());

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to load MySql properties", e);
            System.exit(1);
        }
    }

    @Override
    public void retrieveLeaseRequestsByRoom(Room room){
        List<LeaseRequest> leaseRequests = new ArrayList<>();
        String query = properties.getProperty("QUERY_LEASE_ROOM");
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, room.getIdRoom());
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                if (!resultSet.getString(LEASE_STATUS).equals("rifiutato")) {
                    leaseRequests.add(fillLeaseRequest(resultSet));
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        room.setLeaseRequests(leaseRequests);
    }

    private LeaseRequest fillLeaseRequest(ResultSet resultSet) throws SQLException {
        DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
        AccountDAO accountDAO = daoFactoryFacade.getAccountDAO();
        LeaseRequest leaseRequest = new LeaseRequest();
        leaseRequest.setId(resultSet.getInt("id"));
        leaseRequest.setAccount(accountDAO.retrieveAccountDetails(resultSet.getString("affittuario")));
        leaseRequest.setStartPermanence(resultSet.getDate("inizioPermanenza").toString());
        leaseRequest.setTypePermanence(resultSet.getString("tipoPermanenza"));
        leaseRequest.setStatusRequest(resultSet.getString(LEASE_STATUS));
        return leaseRequest;
    }

    @Override
    public List<LeaseRequest> retrieveLeaseRequestsByTenant(Account tenant) {
        return Collections.emptyList();
    }

    @Override
    public boolean hasActiveLeaseRequest(Account tenant, Integer idRoom) { ////////
        return false;
    }
}
