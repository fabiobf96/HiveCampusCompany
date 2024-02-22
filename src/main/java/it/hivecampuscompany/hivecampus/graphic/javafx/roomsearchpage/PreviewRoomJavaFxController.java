package it.hivecampuscompany.hivecampus.graphic.javafx.roomsearchpage;

import it.hivecampuscompany.hivecampus.logic.bean.AccountBean;
import it.hivecampuscompany.hivecampus.logic.bean.RoomBean;
import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import it.hivecampuscompany.hivecampus.logic.control.RoomLeaseRequestManager;
import it.hivecampuscompany.hivecampus.logic.decorator.RoomBeanDecorator;
import it.hivecampuscompany.hivecampus.logic.decorator.RoomImageDecorator;
import it.hivecampuscompany.hivecampus.logic.model.Room;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
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

    @FXML
    private ImageView imgRoom;

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
        //setRoomImage(((RoomImageDecorator) roomBean).getRoomImage());

    }

    private RoomBean decoratedRoom(RoomBean roomBean){

        String path = roomLeaseRequestManager.getRoomPath(roomBean.getTypeRoom());
        byte[] image = loadImage(path);

        if (image.length == 0) {
            return new RoomImageDecorator(roomBean, image);
        }
        return roomBean;
    }

    private byte[] loadImage(String imagePath) {
        try {
            // Carica l'immagine dal percorso specificato e converte in array di byte
            Path path = Paths.get(Objects.requireNonNull(getClass().getResource(imagePath)).toURI());
            return Files.readAllBytes(path);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error loading image", e);
            return new byte[0];
        }
    }

    private void setRoomImage(byte[] imageBytes) {
        try {
            // Converti l'array di byte dell'immagine in un oggetto Image
            javafx.scene.image.Image image = new javafx.scene.image.Image(new java.io.ByteArrayInputStream(imageBytes));

            // Imposta l'immagine nella ImageView
            imgRoom.setImage(image);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error setting room image", e);
        }
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