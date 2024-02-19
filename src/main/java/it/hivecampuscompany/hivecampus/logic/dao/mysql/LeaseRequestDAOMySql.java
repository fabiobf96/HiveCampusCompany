package it.hivecampuscompany.hivecampus.logic.dao.mysql;

import it.hivecampuscompany.hivecampus.logic.control.ConnectionManager;
import it.hivecampuscompany.hivecampus.logic.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.logic.dao.LeaseRequestDAO;
import it.hivecampuscompany.hivecampus.logic.facade.DAOFactoryFacade;
import it.hivecampuscompany.hivecampus.logic.model.LeaseRequest;
import it.hivecampuscompany.hivecampus.logic.model.Room;
import it.hivecampuscompany.hivecampus.logic.model.Tenant;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LeaseRequestDAOMySql implements LeaseRequestDAO {
    private final Connection conn;
    private final Properties properties;
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

    }

    @Override
    public void retrieveLeaseRequestsByRoom(Room room) {
        List<LeaseRequest> leaseRequests = new ArrayList<>();
        String query = properties.getProperty("QUERY_LEASE_ROOM");
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, room.getIdRoom());
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                leaseRequests.add(fillLeaseRequest(resultSet));
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
        leaseRequest.setDate(resultSet.getDate("inizioPermanenza").toString());
        leaseRequest.setTypePermanence(resultSet.getString("tipoPermanenza"));
        return leaseRequest;
    }

    @Override
    public List<LeaseRequest> retrieveLeaseRequestsByTenant(Tenant tenant) {
        return null;
    }
}
