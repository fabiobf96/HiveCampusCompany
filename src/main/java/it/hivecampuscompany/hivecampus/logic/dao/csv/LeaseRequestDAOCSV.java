package it.hivecampuscompany.hivecampus.logic.dao.csv;

import it.hivecampuscompany.hivecampus.logic.dao.LeaseRequestDAO;
import it.hivecampuscompany.hivecampus.logic.exception.EmptyListException;
import it.hivecampuscompany.hivecampus.logic.facade.DAOFactoryFacade;
import it.hivecampuscompany.hivecampus.logic.model.Account;
import it.hivecampuscompany.hivecampus.logic.model.LeaseRequest;
import it.hivecampuscompany.hivecampus.logic.model.Room;
import it.hivecampuscompany.hivecampus.logic.model.Tenant;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LeaseRequestDAOCSV implements LeaseRequestDAO {

    File fd;
    private static final Logger LOGGER = Logger.getLogger(LeaseRequestDAOCSV.class.getName());

    public LeaseRequestDAOCSV() {
        try (InputStream input = new FileInputStream("properties/csv.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            fd = new File(properties.getProperty("LEASE_REQUEST_PATH"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load CSV properties", e);
            System.exit(1);
        }
    }
    @Override
    public void saveLeaseRequest(LeaseRequest leaseRequest) {
        //this method is empty perchè mi va
    }

    @Override
    public void retrieveLeaseRequestsByRoom(Room room) throws EmptyListException {
        String csvSplitBy = ",";
        List<LeaseRequest> leaseRequests = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fd))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] rentalRequest = line.split(csvSplitBy);

                int roomId = Integer.parseInt(rentalRequest[1].trim());

                if(room.getIdRoom() == roomId) {
                    int requestId = Integer.parseInt(rentalRequest[0].trim());
                    String tenantEmail = rentalRequest[2].trim();
                    String startDate = LocalDate.parse(rentalRequest[3].trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString();
                    String duration = rentalRequest[4].trim();
                    DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
                    Account tenant = daoFactoryFacade.getAccountDAO().retrieveAccountDetails(tenantEmail);
                    leaseRequests.add(new LeaseRequest(requestId, tenant, startDate, duration));
                }
            }
            if (leaseRequests.isEmpty()){
                throw new EmptyListException("There are any Lease Request specified room");
            }
        }catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load CSV properties", e);
            System.exit(1);
        }
        room.setLeaseRequests(leaseRequests);
    }

    @Override
    public List<LeaseRequest> retrieveLeaseRequestsByTenant(Tenant tenant) {
        return null;
    }
}
