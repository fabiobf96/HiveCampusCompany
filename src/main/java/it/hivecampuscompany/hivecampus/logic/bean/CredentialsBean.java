package it.hivecampuscompany.hivecampus.logic.bean;

import it.hivecampuscompany.hivecampus.logic.exception.EmptyFieldsException;
import it.hivecampuscompany.hivecampus.logic.exception.InvalidEmailException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CredentialsBean {
    protected String email;
    protected String password;
    private static final Logger LOGGER = Logger.getLogger(CredentialsBean.class.getName());

    public CredentialsBean(){

    }

    public CredentialsBean(String email, String password) throws InvalidEmailException, EmptyFieldsException {
        if (email.isEmpty()) throw new EmptyFieldsException("Email is empty.");
        validateEmail(email);
        this.email = email;
        if (password.isEmpty()) throw new EmptyFieldsException("Password is empty.");
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    protected void validateEmail(String email) throws InvalidEmailException {
        try (InputStream input = new FileInputStream("properties/config.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            Pattern pattern = Pattern.compile(properties.getProperty("EMAIL_REGEX.regexp"));
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                throw new InvalidEmailException("Indirizzo email non valido: " + email);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load CSV properties", e);
            System.exit(1);
        }
    }

    public void setEmail(String email) throws InvalidEmailException, EmptyFieldsException {
        if(email.isEmpty()) throw new EmptyFieldsException("E-mail field is empty");
        validateEmail(email);
        this.email = email;
    }
}
