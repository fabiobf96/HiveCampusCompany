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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PreviewRoomJavaFxController {

    private RoomLeaseRequestManager roomLeaseRequestManager;
    private SessionBean sessionBean;

    private RoomBean roomBean;

    @FXML
    private VBox vbPreview;

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
                // Recupera i dettagli dell'account utilizzando l'email
                AccountBean accountBean = roomLeaseRequestManager.getOwnerDetails(roomBean.getOwnerEmail());

                System.out.println(accountBean.toString());

                // Carica il file FXML per la visualizzazione dei dettagli della stanza
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampusteam/hivecampus/roomDetails-view.fxml"));
                Parent root = loader.load();

                // Ottieni il controller RoomDetailsJavaFxController
                RoomDetailsJavaFxController roomDetailsController = loader.getController();

                // Inizializza il controller con l'oggetto RoomBean e AccountBean
                roomDetailsController.initialize(roomBean, accountBean, sessionBean);

                // Crea e visualizza la finestra modale con i dettagli della stanza
                Stage popUpStage = new Stage();
                popUpStage.initModality(Modality.APPLICATION_MODAL);
                popUpStage.setTitle("Room Details");
                Scene scene = new Scene(root, 800, 500);
                // Impostare la finestra come non ridimensionabile
                popUpStage.setResizable(false);
                popUpStage.setScene(scene);
                popUpStage.showAndWait();
            } else {
                // Nessun dettaglio della stanza trovato
                System.out.println("Nessun dettaglio della stanza trovato");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}