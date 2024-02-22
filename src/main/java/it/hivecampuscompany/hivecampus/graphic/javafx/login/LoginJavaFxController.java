package it.hivecampuscompany.hivecampus.graphic.javafx.login;

import it.hivecampuscompany.hivecampus.graphic.javafx.signup.SignUpJavaFxGUI;
import it.hivecampuscompany.hivecampus.graphic.javafx.tenanthomepage.TenantHomeJavaFxController;
import it.hivecampuscompany.hivecampus.graphic.javafx.tenanthomepage.TenantHomeJavaFxGUI;
import it.hivecampuscompany.hivecampus.logic.bean.CredentialsBean;
import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import it.hivecampuscompany.hivecampus.logic.control.LoginManager;
import it.hivecampuscompany.hivecampus.logic.exception.AuthenticateException;
import it.hivecampuscompany.hivecampus.logic.exception.EmptyFieldsException;
import it.hivecampuscompany.hivecampus.logic.exception.InvalidEmailException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.security.auth.login.FailedLoginException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;


public class LoginJavaFxController {

    private final LoginManager manager;

    @FXML
    private TextField txfEmail;

    @FXML
    private PasswordField txfPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSignUp;


    public LoginJavaFxController(){
        this.manager = new LoginManager();
    }

    @FXML
    private void handleLoginButtonClick() {
        String email = txfEmail.getText();
        String password = txfPassword.getText();
        CredentialsBean credentialsBean;

        try {
            credentialsBean = new CredentialsBean(email, password);
            SessionBean sessionBean = manager.searchAccountByCredentials(credentialsBean);
            // Login successfully and empty the fields
            clearFields();
            // Shows the correct homepage based on the account type
            showHomePage(sessionBean);
            System.out.println("Login successful.");
        }
        catch (EmptyFieldsException | InvalidEmailException | AuthenticateException | FailedLoginException |
               SQLException | NoSuchAlgorithmException e) {
            showErrorAlert(e.getMessage());
        }
    }

    public void handleSignUpButtonClick() {
        Stage stage = (Stage) btnSignUp.getScene().getWindow();

        SignUpJavaFxGUI signUp = new SignUpJavaFxGUI();
        try {
            signUp.start(stage);
        } catch (Exception e) {
            showErrorAlert("Error loading signup window.");
        }
    }

    public void handleGoogleButtonClick() {
        String msg = "Sorry, this function is not available yet";
        showErrorAlert(msg);
    }

    private void showHomePage(SessionBean sessionBean) {
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        try {
            switch (sessionBean.getTypeAccount()){
                case ("owner"):
                    // Create a new instance of OwnerHomeJavaFxController and pass the sessionBean
                    break;

                case ("tenant"):
                    TenantHomeJavaFxController tenantController = new TenantHomeJavaFxController();
                    new TenantHomeJavaFxGUI().startWithController(stage, tenantController);
                    tenantController.initialize(sessionBean);
                    break;

                default : System.exit(3);
            }
        } catch (Exception e) {
            showErrorAlert("Error loading homepage window.");
            System.exit(1);
        }
    }


    private void clearFields() {
        txfEmail.clear();
        txfPassword.clear();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
