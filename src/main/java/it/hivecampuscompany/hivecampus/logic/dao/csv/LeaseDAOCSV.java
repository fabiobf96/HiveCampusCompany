package it.hivecampuscompany.hivecampus.logic.dao.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import it.hivecampuscompany.hivecampus.logic.dao.LeaseDAO;
import it.hivecampuscompany.hivecampus.logic.exception.EmptyListException;
import it.hivecampuscompany.hivecampus.logic.facade.DAOFactoryFacade;
import it.hivecampuscompany.hivecampus.logic.model.Account;
import it.hivecampuscompany.hivecampus.logic.model.Lease;
import it.hivecampuscompany.hivecampus.logic.model.Room;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LeaseDAOCSV implements LeaseDAO {
    File fd;
    private static final Logger LOGGER = Logger.getLogger(LeaseDAOCSV.class.getName());

    public LeaseDAOCSV() {
        try (InputStream input = new FileInputStream("properties/csv.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            fd = new File(properties.getProperty("LEASE_PATH"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load CSV properties", e);
            System.exit(1);
        }
    }

    @Override
    public void saveLease(Lease lease) {
        int lastId = findLastRowIndex();
        try (CSVWriter writer = new CSVWriter(new FileWriter(fd, true))) {
            String[] leaseRecord = new String[5];
            leaseRecord[LeaseAttributesOrder.GET_INDEX_ID] = String.valueOf(lastId);
            leaseRecord[LeaseAttributesOrder.GET_INDEX_TENANT] = lease.getTenant().getEmail();
            leaseRecord[LeaseAttributesOrder.GET_INDEX_ROOM] = String.valueOf(lease.getRoom().getIdRoom());
            leaseRecord[LeaseAttributesOrder.GET_INDEX_ACTIVE] = String.valueOf(lease.isActive());
            leaseRecord[LeaseAttributesOrder.GET_INDEX_CONTRACT] = serializeFile(lease.getFile());
            writer.writeNext(leaseRecord);
            deserializeFile(leaseRecord[LeaseAttributesOrder.GET_INDEX_CONTRACT]);
            // Lease save successfully
        } catch (IOException  e) {
            LOGGER.log(Level.SEVERE, "Failed to save lease request", e);
            System.exit(2);
        }
    }

    @Override
    public Lease retrieveLeaseRoom(Room room) throws EmptyListException {
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            String[] nextRecord;
            while ((nextRecord = reader.readNext()) != null) {

                String storedRoomId = nextRecord[LeaseAttributesOrder.GET_INDEX_ROOM].trim();

                if (room.getIdRoom().equals(Integer.parseInt(storedRoomId))) {
                    int storedLeaseId = Integer.parseInt(nextRecord[LeaseAttributesOrder.GET_INDEX_ID].trim());
                    String storedTenantEmail = nextRecord[LeaseAttributesOrder.GET_INDEX_TENANT].trim();
                    Boolean storedActive = Boolean.valueOf(nextRecord[LeaseAttributesOrder.GET_INDEX_ACTIVE].trim());
                    File storedContract = deserializeFile(nextRecord[LeaseAttributesOrder.GET_INDEX_CONTRACT].trim());
                    DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
                    Account account = daoFactoryFacade.getAccountDAO().retrieveAccountDetails(storedTenantEmail);

                    return new Lease(storedLeaseId, room, account, storedActive, storedContract);
                }
            }

        } catch (IOException | CsvValidationException e) {
            LOGGER.log(Level.SEVERE, "Failed to load CSV properties", e);
            System.exit(1);
        }
        throw new EmptyListException("there are no contracts");
    }

    private String serializeFile(File file) throws IOException {
        // Crea un buffer per leggere il file
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            // Legge ogni riga del file e la aggiunge al buffer
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        }
        // Restituisce il contenuto del buffer come stringa
        return builder.toString();
    }

    private File deserializeFile(String content) throws IOException {
        // Crea un buffer per scrivere sul file
        File file = new File("db/csv/contract");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Scrive la stringa sul file
            writer.write(content);
        }
        return file;
    }

    private int findLastRowIndex() {
        int lastID = 0;
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            String[] nextRecord;
            while ((nextRecord = reader.readNext()) != null) {
                lastID = Integer.parseInt(nextRecord[LeaseAttributesOrder.GET_INDEX_ID]);
            }
        } catch (IOException | CsvValidationException e) {
            LOGGER.log(Level.SEVERE, "Failed to retrieve account by credentials", e);
        }
        if (lastID == 0){
            return lastID;
        }
        else {
            return lastID + 1;
        }
    }
    private static class LeaseAttributesOrder{
        static final int GET_INDEX_ID= 0;
        static final int GET_INDEX_TENANT = 1;
        static final int GET_INDEX_ROOM = 2;
        static final int GET_INDEX_ACTIVE = 3;
        static final int GET_INDEX_CONTRACT = 4;
    }
}
