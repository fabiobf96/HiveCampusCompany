package it.hivecampuscompany.hivecampus.logic.dao.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import it.hivecampuscompany.hivecampus.logic.bean.CredentialsBean;
import it.hivecampuscompany.hivecampus.logic.dao.AccountDAO;
import it.hivecampuscompany.hivecampus.logic.exception.AuthenticateException;
import it.hivecampuscompany.hivecampus.logic.exception.DuplicateRowException;
import it.hivecampuscompany.hivecampus.logic.model.Account;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountDAOCSV implements AccountDAO {
    File fd;
    private static final Logger LOGGER = Logger.getLogger(AccountDAOCSV.class.getName());

    public AccountDAOCSV(){
        try (InputStream input = new FileInputStream("properties/csv.properties")){
            Properties properties = new Properties();
            properties.load(input);
            fd = new File(properties.getProperty("CREDENTIALS_PATH"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load CSV properties", e);
            System.exit(1);
        }
    }
    @Override
    public Account retrieveAccountByCredentials(CredentialsBean credentialsBean) throws AuthenticateException {
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
        String[] nextRecord;
        while ((nextRecord = reader.readNext()) != null) {
            String storedEmail = nextRecord[AccountAttributesOrder.GET_INDEX_EMAIL].trim();
            String storedPassword = nextRecord[AccountAttributesOrder.GET_INDEX_PASSWORD].trim();

            if (credentialsBean.getEmail().equals(storedEmail) && credentialsBean.getPassword().equals(storedPassword)) {
                String storedName = nextRecord[AccountAttributesOrder.GET_INDEX_NAME].trim();
                String storedSurname = nextRecord[AccountAttributesOrder.GET_INDEX_SURNAME].trim();
                String storedAccountType = nextRecord[AccountAttributesOrder.GET_INDEX_ACCOUNT_TYPE].trim();
                String storedPhoneNumber = nextRecord[AccountAttributesOrder.GET_INDEX_PHONE_NUMBER].trim();
                return new Account(storedEmail, storedPassword, storedName, storedSurname, storedAccountType, storedPhoneNumber);
            }
        }
        } catch (IOException | CsvValidationException e) {
            LOGGER.log(Level.SEVERE, "Failed to retrieve account by credentials", e);
        }
        throw new AuthenticateException("Credentials are not valid."); // Exception for not valid credentials
    }

    @Override
    public void saveAccount(Account account) throws DuplicateRowException {
        if (userExists(account.getEmail())) {
            throw new DuplicateRowException("An email account already exists.");
        }
        try (CSVWriter writer = new CSVWriter(new FileWriter(fd, true))) {
            String[] accountRecord = new String[6];
            accountRecord[AccountAttributesOrder.GET_INDEX_EMAIL] = account.getEmail();
            accountRecord[AccountAttributesOrder.GET_INDEX_PASSWORD] = account.getPassword();
            accountRecord[AccountAttributesOrder.GET_INDEX_NAME] = account.getName();
            accountRecord[AccountAttributesOrder.GET_INDEX_SURNAME] = account.getSurname();
            accountRecord[AccountAttributesOrder.GET_INDEX_ACCOUNT_TYPE] = account.getTypeAccount();
            accountRecord[AccountAttributesOrder.GET_INDEX_PHONE_NUMBER] = account.getPhoneNumber();
            writer.writeNext(accountRecord);
            // User created successfully
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save account", e);
            System.exit(2);
        }
    }

    @Override
    public Account retrieveAccountDetails(String email) {
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            String[] nextRecord;
            while ((nextRecord = reader.readNext()) != null) {
                String storedEmail = nextRecord[AccountAttributesOrder.GET_INDEX_EMAIL].trim();
                if (email.equals(storedEmail)) {
                    String storedName = nextRecord[AccountAttributesOrder.GET_INDEX_NAME].trim();
                    String storedSurname = nextRecord[AccountAttributesOrder.GET_INDEX_SURNAME].trim();
                    String storedPhoneNumber = nextRecord[AccountAttributesOrder.GET_INDEX_PHONE_NUMBER].trim();
                    return new Account(storedEmail, null, storedName, storedSurname, null, storedPhoneNumber);
                }
            }
        } catch (IOException | CsvValidationException e) {
            LOGGER.log(Level.SEVERE, "Failed to retrieve account by credentials", e);
        }
        return null;
    }

    private boolean userExists(String email) {
        try (CSVReader reader = new CSVReader(new FileReader(fd))) {
            String[] nextRecord;
            while ((nextRecord = reader.readNext()) != null) {
                String storedEmail = nextRecord[0].trim();
                if (email.equals(storedEmail)) {
                    return true; // User found
                }
            }
        } catch (IOException | CsvValidationException e) {
            LOGGER.log(Level.SEVERE, "Failed to check if user exists", e);
        }

        return false; // User not found
    }

    private static class AccountAttributesOrder{
        static final int GET_INDEX_EMAIL = 0;
        static final int GET_INDEX_PASSWORD = 1;
        static final int GET_INDEX_NAME = 2;
        static final int GET_INDEX_SURNAME = 3;
        static final int GET_INDEX_ACCOUNT_TYPE = 4;
        static final int GET_INDEX_PHONE_NUMBER = 5;
    }
}
