package it.hivecampuscompany.hivecampus.graphic.javafx.tenanthomepage;

import it.hivecampuscompany.hivecampus.graphic.javafx.leaserequestspage.LeaseRequestJavaFxController;
import it.hivecampuscompany.hivecampus.graphic.javafx.login.LoginJavaFxGUI;
import it.hivecampuscompany.hivecampus.graphic.javafx.roomsearchpage.RoomSearchJavaFxController;
import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TenantHomeJavaFxController {

    private static final Logger logger = Logger.getLogger(TenantHomeJavaFxController.class.getName());
    private SessionBean sessionBean;
    @FXML
    private TabPane tabPane;

    @FXML
    private MenuButton btnMenuAccount;

    public TenantHomeJavaFxController() {
        // Default constructor
    }

    public TenantHomeJavaFxController(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    @FXML
    private void initialize() {
        try {
            addDynamicTab("Ricerca Camera", "/it/hivecampuscompany/hivecampus/tabRoomSearch-view.fxml");
            addDynamicTab("Gestione richieste", "/it/hivecampuscompany/hivecampus/tabLeaseRequest-view.fxml");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding dynamic tab",e);
        }
    }

    @FXML
    private void handleLogout() {
        Stage stage = (Stage) btnMenuAccount.getScene().getWindow();

        LoginJavaFxGUI login = new LoginJavaFxGUI();
        try {
            login.start(stage);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error logging out.", e);
        }
    }

    private void addDynamicTab(String tabName, String contentFXML) {
        try {
            // Carica il contenuto del tab da un file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(contentFXML));

            // Carica il contenuto e imposta il contenuto del nuovo tab
            Tab newTab = new Tab(tabName);
            newTab.setContent(loader.load());

            // Ottieni il controller della GUI caricata
            Object controller = loader.getController();
            if (controller instanceof RoomSearchJavaFxController){
                new RoomSearchJavaFxController(sessionBean);
            }
            else if (controller instanceof LeaseRequestJavaFxController) {
                new LeaseRequestJavaFxController(sessionBean);
            }

            // Imposta closable a false per impedire la chiusura del tab
            newTab.setClosable(false);

            // Aggiungi il nuovo tab al tuo TabPane
            tabPane.getTabs().add(newTab);

        } catch (IOException | RuntimeException e) {
            logger.log(Level.SEVERE, "Error while loading.", e);
        }
    }
}