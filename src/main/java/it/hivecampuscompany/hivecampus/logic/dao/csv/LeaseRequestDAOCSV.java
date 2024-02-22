package it.hivecampuscompany.hivecampus.logic.dao.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import it.hivecampuscompany.hivecampus.logic.dao.LeaseRequestDAO;
import it.hivecampuscompany.hivecampus.logic.facade.DAOFactoryFacade;
import it.hivecampuscompany.hivecampus.logic.model.Account;
import it.hivecampuscompany.hivecampus.logic.model.LeaseRequest;
import it.hivecampuscompany.hivecampus.logic.model.Room;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LeaseRequestDAOCSV implements LeaseRequestDAO {
    private Properties properties;
    File fd;
    private static final Logger LOGGER = Logger.getLogger(LeaseRequestDAOCSV.class.getName());

    public LeaseRequestDAOCSV() {
        try (InputStream input = new FileInputStream("properties/csv.properties")) {
            properties = new Properties();
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
            String[] leaseRequestRecord = new String[6];
            leaseRequestRecord[LeaseRoomAttributesOrder.GET_INDEX_REQUEST_ID] = String.valueOf(lastId);
            leaseRequestRecord[LeaseRoomAttributesOrder.GET_INDEX_ROOM_ID] = String.valueOf(leaseRequest.getRoom().getIdRoom());
            leaseRequestRecord[LeaseRoomAttributesOrder.GET_INDEX_EMAIL] = leaseRequest.getAccount().getEmail();
            leaseRequestRecord[LeaseRoomAttributesOrder.GET_INDEX_INIT_START] = leaseRequest.getStartPermanence();
            leaseRequestRecord[LeaseRoomAttributesOrder.GET_INDEX_STAY_TYPE] = leaseRequest.getTypePermanence();
            leaseRequestRecord[LeaseRoomAttributesOrder.GET_INDEX_STATUS] = leaseRequest.getStatusRequest();
            writer.writeNext(leaseRequestRecord);

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save lease request", e);
            System.exit(2);
        }
    }

    public void updateLeaseRequest(LeaseRequest updatedLeaseRequest) {
        String filePath = properties.getProperty("LEASE_REQUEST_PATH");
        File inputFile = new File(filePath);
        File tempFile = new File(inputFile.getAbsolutePath() + ".tmp");

        try (CSVReader reader = new CSVReader(new FileReader(inputFile));
             CSVWriter writer = new CSVWriter(new FileWriter(tempFile))) {

            List<String[]> allRows = reader.readAll();
            for (String[] row : allRows) {
                if (Integer.parseInt(row[LeaseRoomAttributesOrder.GET_INDEX_REQUEST_ID]) == updatedLeaseRequest.getId()) {
                    row[LeaseRoomAttributesOrder.GET_INDEX_STATUS] = updatedLeaseRequest.getStatusRequest();
                }
            }
            writer.writeAll(allRows);
        } catch (IOException | CsvException e) {
            LOGGER.log(Level.SEVERE, "Failed to update lease request", e);
            System.exit(2);
        }

        // Sostituisci il file originale con il file temporaneo aggiornato
        try {
            Files.move(tempFile.toPath(), Path.of(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save lease request", e);
            System.exit(2);
        }
    }

    @Override
    public void retrieveLeaseRequestsByRoom(Room room) {
        List<LeaseRequest> leaseRequests = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            String[] nextRecord;
            while ((nextRecord = reader.readNext()) != null) {

                String storedRoomId = nextRecord[LeaseRoomAttributesOrder.GET_INDEX_ROOM_ID].trim();

                if (room.getIdRoom().equals(Integer.parseInt(storedRoomId))) {
                    int requestId = Integer.parseInt(nextRecord[LeaseRoomAttributesOrder.GET_INDEX_REQUEST_ID].trim());
                    String tenantEmail = nextRecord[LeaseRoomAttributesOrder.GET_INDEX_EMAIL].trim();
                    String startDate = nextRecord[LeaseRoomAttributesOrder.GET_INDEX_INIT_START].trim();
                    String duration = nextRecord[LeaseRoomAttributesOrder.GET_INDEX_STAY_TYPE].trim();
                    String leaseStatus = nextRecord[LeaseRoomAttributesOrder.GET_INDEX_STATUS].trim();

                    if (!leaseStatus.equals("rifiutata") && !leaseStatus.equals("affittata")) {
                        DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
                        Account tenant = daoFactoryFacade.getAccountDAO().retrieveAccountDetails(tenantEmail);
                        LeaseRequest leaseRequest = new LeaseRequest(requestId, tenant, startDate, duration, leaseStatus);
                        leaseRequests.add(leaseRequest);
                    }
                }
            }

        } catch (IOException | CsvValidationException e) {
            LOGGER.log(Level.SEVERE, "Failed to load CSV properties", e);
            System.exit(1);
        }
        room.setLeaseRequests(leaseRequests);
    }



    @Override
    public boolean hasActiveLeaseRequest(Account tenant, Integer idRoom) {
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            String[] nextRecord;
            while ((nextRecord = reader.readNext()) != null) {
                String storedEmail = nextRecord[LeaseRoomAttributesOrder.GET_INDEX_EMAIL].trim();
                String storedRoomId = nextRecord[LeaseRoomAttributesOrder.GET_INDEX_ROOM_ID].trim();
                if (tenant.getEmail().equals(storedEmail) && idRoom.equals(Integer.parseInt(storedRoomId))) {
                    return true;
                }
            }
        } catch (IOException | CsvValidationException e) {
            LOGGER.log(Level.SEVERE, "Failed to retrieve account by credentials", e);
        }

        return false;
    }


    @Override
    public List<LeaseRequest> retrieveLeaseRequestsByTenant(Account tenant) {
        List<LeaseRequest> leaseRequests = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            String[] nextRecord;
            while ((nextRecord = reader.readNext()) != null) {
                String storedEmail = nextRecord[LeaseRoomAttributesOrder.GET_INDEX_EMAIL].trim();

                if (tenant.getEmail().equals(storedEmail)){
                    int idRequest = Integer.parseInt(nextRecord[LeaseRoomAttributesOrder.GET_INDEX_REQUEST_ID].trim());
                    String type = nextRecord[LeaseRoomAttributesOrder.GET_INDEX_STAY_TYPE].trim();
                    String start = nextRecord[LeaseRoomAttributesOrder.GET_INDEX_INIT_START].trim();
                    String status = nextRecord[LeaseRoomAttributesOrder.GET_INDEX_STATUS].trim();

                    LeaseRequest leaseRequest = new LeaseRequest(idRequest, tenant, start, type, status);
                    leaseRequests.add(leaseRequest);
                }
            } return leaseRequests;
        } catch (IOException | CsvValidationException e) {
            LOGGER.log(Level.SEVERE, "Failed to read CSV file", e);
        }
        return Collections.emptyList();
    }

    private static class LeaseRoomAttributesOrder{
        static final int GET_INDEX_REQUEST_ID= 0;
        static final int GET_INDEX_ROOM_ID = 1;
        static final int GET_INDEX_EMAIL = 2;
        static final int GET_INDEX_INIT_START = 3;
        static final int GET_INDEX_STAY_TYPE = 4;
        static final int GET_INDEX_STATUS = 5;
    }

    private int findLastRowIndex() {
        int lastID = 0;
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            String[] nextRecord;
            while ((nextRecord = reader.readNext()) != null) {
                lastID = Integer.parseInt(nextRecord[LeaseRoomAttributesOrder.GET_INDEX_REQUEST_ID].trim());
            }
        } catch (IOException | CsvValidationException e) {
            LOGGER.log(Level.SEVERE, "Failed to retrieve account by credentials", e);
        }
        if (lastID == 0) {
            return 0;
        }
        else {
            return lastID + 1;
        }
    }

}