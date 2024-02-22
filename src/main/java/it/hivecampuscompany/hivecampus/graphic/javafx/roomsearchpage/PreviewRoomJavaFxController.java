package it.hivecampuscompany.hivecampus.graphic.javafx.roomsearchpage;

import it.hivecampuscompany.hivecampus.logic.bean.AccountBean;
import it.hivecampuscompany.hivecampus.logic.bean.RoomBean;
import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import it.hivecampuscompany.hivecampus.logic.control.RoomLeaseRequestManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PreviewRoomJavaFxController {

    private RoomLeaseRequestManager roomLeaseRequestManager;
    private SessionBean sessionBean;
    private RoomBean roomBean;
    private static final Logger logger = Logger.getLogger(PreviewRoomJavaFxController.class.getName());


    @FXML
    private Label lblTitle;

    @FXML
    private Label lblSurface;

    @FXML
    private Label lblBath;

    @FXML
    private Label lblBalcony;

    @FXML
    private Label lblConditioner;

    @FXML
    private Label lblTV;

    @FXML
    private Label lblAvailability;

    @FXML
    private Label lblUniversity;

    @FXML
    private Label lblDistance;

    public PreviewRoomJavaFxController() {
        // Default constructor
    }

    public void initialize(RoomLeaseRequestManager roomLeaseRequestManager, RoomBean roomBean, SessionBean sessionBean) {
        this.roomLeaseRequestManager = roomLeaseRequestManager;
        this.sessionBean = sessionBean;
        this.roomBean = roomBean;

        lblTitle.setText("Stanza " + roomBean.getTypeRoom() + " - " + roomBean.getAddress() + " - € " + roomBean.getPrice() + "/mese");
        lblSurface.setText(String.valueOf(roomBean.getRoomSurface()));
        lblBath.setText(String.valueOf(roomBean.getPrivateBathroom()));
        lblBalcony.setText(String.valueOf(roomBean.getBalcony()));
        lblConditioner.setText(String.valueOf(roomBean.getConditioner()));
        lblTV.setText(String.valueOf(roomBean.getTvConnection()));
        lblAvailability.setText(roomBean.getAvailability());
        lblUniversity.setText(roomBean.getUniversity());
        lblDistance.setText(String.valueOf(roomBean.getDistance()));

    }


    @FXML
    public void handlePreviewRoomClick() {
        try {
            // Carica il file FXML per la visualizzazione dei dettagli della stanza
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/roomDetails-view.fxml"));
            Parent root = loader.load();

            // Ottieni il controller RoomDetailsJavaFxController
            RoomDetailsJavaFxController roomDetailsController = loader.getController();

            // Recupero le informazioni del proprietario
            AccountBean accountBean = roomLeaseRequestManager.getOwnerDetails(roomBean.getOwnerEmail());

            // Inizializza il controller con l'oggetto RoomBean e AccountBean
            roomDetailsController.initialize(sessionBean, roomLeaseRequestManager, roomBean, accountBean);

            // Crea e visualizza la finestra modale con i dettagli della stanza
            Stage popUpStage = new Stage();
            popUpStage.initModality(Modality.APPLICATION_MODAL);
            popUpStage.setTitle("Room Details");
            Scene scene = new Scene(root, 800, 500);
            // Impostare la finestra come non ridimensionabile
            popUpStage.setResizable(false);
            popUpStage.setScene(scene);
            popUpStage.showAndWait();

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error loading FXML file", e);
        }
    }
}