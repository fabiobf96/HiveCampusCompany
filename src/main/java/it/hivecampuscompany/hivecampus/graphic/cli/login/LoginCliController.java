package it.hivecampuscompany.hivecampus.graphic.cli.login;



import it.hivecampuscompany.hivecampus.graphic.cli.ownerhomepage.OwnerHomeCliController;
import it.hivecampuscompany.hivecampus.logic.bean.AccountBean;
import it.hivecampuscompany.hivecampus.logic.bean.CredentialsBean;
import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import it.hivecampuscompany.hivecampus.logic.control.LoginManager;
import it.hivecampuscompany.hivecampus.logic.exception.*;

import javax.security.auth.login.FailedLoginException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginCliController {
    private final LoginCliView view;
    private final LoginManager manager;
    private static final Logger LOGGER = Logger.getLogger(LoginCliController.class.getName());

    public LoginCliController() {
        this.view = new LoginCliView();
        this.manager = new LoginManager();
        view.displayWelcomeMessage();
        handleUserChoice();
    }

    public void handleUserChoice() {
        int choice = view.getUserChoice();
        switch (choice) {
            case 1:
                logon();
                break;
            case 2:
                singUp();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                view.displayMessage("\nScelta non valida\n");
                handleUserChoice();
        }
    }

    private void logon() {
        String email = view.getUserInput("Inserisci email: ");
        String password = view.getUserInput("Inserisci password: ");
        CredentialsBean credentialsBean;
        try {
            credentialsBean = new CredentialsBean(email, password);
            SessionBean sessionBean= manager.searchAccountByCredentials(credentialsBean);
            view.displayMessage("Effettuato logon con successo per l'utente: " + credentialsBean.getEmail());
            switch (sessionBean.getTypeAccount()){
                case ("owner")-> new OwnerHomeCliController(sessionBean);
//                case ("tenant")-> new TenantHomeCliController(sessionBean);
                default -> System.exit(3);
            }
        } catch (EmptyFieldsException | InvalidEmailException | AuthenticateException e) {
            view.displayMessage(e.getMessage());
            handleUserChoice();
        } catch (SQLException | FailedLoginException | NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            view.getUserChoice();
        }
    }

    private void singUp() {
        AccountBean accountBean = new AccountBean();
        boolean correct = false;
        while (!correct) {
            try {
                accountBean.setEmail(view.getUserInput("Inserisci email: "));
            } catch (EmptyFieldsException | InvalidEmailException e) {
                view.displayMessage(e.getMessage());
            }
            correct = true;
        }
        correct = false;
        while (!correct) {
            String password = view.getUserInput("Inserisci password: ");
            String confirmPassword = view.getUserInput("Conferma password: ");
            try {
                accountBean.setPassword(password, confirmPassword);
                correct = true;
            } catch (PasswordMismatchException | EmptyFieldsException e) {
                view.displayMessage(e.getMessage());
            }
        }
        String accountType = null;
        switch (view.getAccountType()){
            case 1:
                accountType = "owner";
                break;
            case 2:
                accountType = "tenant";
                break;
            default:
                view.getAccountType();
        }

        accountBean.setTypeAccount(accountType);

        correct = false;
        while (!correct) {
            try {
                accountBean.setName(view.getUserInput("Nome: "));
                accountBean.setSurname(view.getUserInput("Cognome: "));
                accountBean.setPhoneNumber(view.getUserInput("Numero telefono: "));
                correct = true;
            } catch (EmptyFieldsException e) {
                view.displayMessage(e.getMessage());
            }
        }

        try {
            manager.createAccount(accountBean);
        } catch (DuplicateRowException e) {
            view.displayMessage(e.getMessage());
            handleUserChoice();
        } catch (SQLException | NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            System.exit(1);
        }

        view.displayMessage("Account creato con successo per l'utente: " + accountBean.getEmail());
    }
}
