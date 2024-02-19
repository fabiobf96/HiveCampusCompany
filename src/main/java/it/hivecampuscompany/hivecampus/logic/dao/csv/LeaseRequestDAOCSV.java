package it.hivecampuscompany.hivecampus.logic.dao.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import it.hivecampuscompany.hivecampus.logic.dao.LeaseRequestDAO;
import it.hivecampuscompany.hivecampus.logic.exception.EmptyListException;
import it.hivecampuscompany.hivecampus.logic.facade.DAOFactoryFacade;
import it.hivecampuscompany.hivecampus.logic.model.Account;
import it.hivecampuscompany.hivecampus.logic.model.LeaseRequest;
import it.hivecampuscompany.hivecampus.logic.model.Room;
import it.hivecampuscompany.hivecampus.logic.model.Tenant;

import java.io.*;
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
        int lastId = findLastRowIndex();
        try (CSVWriter writer = new CSVWriter(new FileWriter(fd, true))) {
            String[] leaseRequestRecord = new String[5];
            leaseRequestRecord[LeaseRoomAttributesOrder.GET_INDEX_REQUEST_ID] = String.valueOf(lastId);
            leaseRequestRecord[LeaseRoomAttributesOrder.GET_INDEX_ROOM_ID] = String.valueOf(leaseRequest.getRoom().getIdRoom());
            leaseRequestRecord[LeaseRoomAttributesOrder.GET_INDEX_EMAIL] = leaseRequest.getAccount().getEmail();
            leaseRequestRecord[LeaseRoomAttributesOrder.GET_INDEX_INIT_DATE] = leaseRequest.getDate();
            leaseRequestRecord[LeaseRoomAttributesOrder.GET_INDEX_STAY_TYPE] = leaseRequest.getTypePermanence();
            writer.writeNext(leaseRequestRecord);
            // User created successfully
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save lease request", e);
            System.exit(2);
        }
    }

    @Override
    public void retrieveLeaseRequestsByRoom(Room room) throws EmptyListException {
        String csvSplitBy = ",";
        List<LeaseRequest> leaseRequests = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fd))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] rentalRequest = line.split(csvSplitBy);

                int roomId = Integer.parseInt(rentalRequest[LeaseRoomAttributesOrder.GET_INDEX_ROOM_ID].trim());

                if(room.getIdRoom() == roomId) {
                    int requestId = Integer.parseInt(rentalRequest[LeaseRoomAttributesOrder.GET_INDEX_REQUEST_ID].trim());
                    String tenantEmail = rentalRequest[LeaseRoomAttributesOrder.GET_INDEX_EMAIL].trim();
                    String startDate = rentalRequest[LeaseRoomAttributesOrder.GET_INDEX_INIT_DATE].trim();
                    String duration = rentalRequest[LeaseRoomAttributesOrder.GET_INDEX_STAY_TYPE].trim();
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
    private static class LeaseRoomAttributesOrder{
        static final int GET_INDEX_REQUEST_ID= 0;
        static final int GET_INDEX_ROOM_ID = 1;
        static final int GET_INDEX_EMAIL = 2;
        static final int GET_INDEX_INIT_DATE = 3;
        static final int GET_INDEX_STAY_TYPE = 4;
    }
    private int findLastRowIndex() {
        int lastID = 0;
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            String[] nextRecord;
            while ((nextRecord = reader.readNext()) != null) {
                lastID = Integer.parseInt(nextRecord[LeaseRoomAttributesOrder.GET_INDEX_ROOM_ID]);
            }
        } catch (IOException | CsvValidationException e) {
            LOGGER.log(Level.SEVERE, "Failed to retrieve account by credentials", e);
        }
        return lastID;
    }

    public static void main(String[] args){
        Account tenant = new Account();
        tenant.setEmail("marta.rossi@gmail.com");
        Room room = new Room();
        room.setIdRoom(3);
        String typePermanence = "6mesi";
        String startPermanence = "Settembre";
        LeaseRequest leaseRequest = new LeaseRequest();
    }
}