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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomDetailsJavaFxController {

    private RoomLeaseRequestManager manager;

    private SessionBean sessionBean;

    private RoomBean roomBean;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblUniversity;

    @FXML
    private Label lblDistance;

    @FXML
    private Label lblHType;

    @FXML
    private Label lblHSurface;

    @FXML
    private Label lblNumRooms;

    @FXML
    private Label lblNumBathrooms;

    @FXML
    private Label lblFloor;

    @FXML
    private Label lblElevator;

    @FXML
    private Label lblHeating;

    @FXML
    private Text txfHDescription;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblTelephone;

    @FXML
    private Label lblRSurface;

    @FXML
    private Label lblBath;

    @FXML
    private Label lblBalcony;

    @FXML
    private Label lblConditioner;

    @FXML
    private Label lblTV;

    @FXML
    private Text txfRDescription;

    @FXML
    private Label lblAvailability;
    private static final Logger LOGGER = Logger.getLogger(RoomDetailsJavaFxController.class.getName());

    public RoomDetailsJavaFxController(){
        // Default constructor
    }

    public void initialize(SessionBean sessionBean, RoomLeaseRequestManager manager, RoomBean roomBean, AccountBean accountBean){
        this.sessionBean = sessionBean;
        this.manager = manager;
        this.roomBean = roomBean;

        lblTitle.setText("Stanza " + roomBean.getTypeRoom() + " - " + roomBean.getAddress() + " - € " + roomBean.getPrice() + "/mese");
        lblUniversity.setText(roomBean.getUniversity());
        lblDistance.setText(String.valueOf(roomBean.getDistance()));
        lblHType.setText(roomBean.getHouseType());
        lblHSurface.setText(String.valueOf(roomBean.getHouseSurface()));
        lblNumRooms.setText(String.valueOf(roomBean.getNumRooms()));
        lblNumBathrooms.setText(String.valueOf(roomBean.getNumBathrooms()));
        lblFloor.setText(String.valueOf(roomBean.getFloor()));
        lblElevator.setText(String.valueOf(roomBean.getElevator()));
        lblHeating.setText(roomBean.getHeating());
        txfHDescription.setText(formatText(roomBean.getHouseDescription()));
        lblEmail.setText(accountBean.getEmail());
        lblTelephone.setText(accountBean.getPhoneNumber());
        lblRSurface.setText(String.valueOf(roomBean.getRoomSurface()));
        lblBath.setText(String.valueOf(roomBean.getPrivateBathroom()));
        lblBalcony.setText(String.valueOf(roomBean.getBalcony()));
        lblConditioner.setText(String.valueOf(roomBean.getConditioner()));
        lblTV.setText(String.valueOf(roomBean.getTvConnection()));
        txfRDescription.setText(formatText(roomBean.getRoomDescription()));
        lblAvailability.setText(roomBean.getAvailability());
    }

    private String formatText(String text) {
        StringBuilder formattedText = new StringBuilder();
        StringBuilder currentLine = new StringBuilder();
        int charCount = 0;

        for (char c : text.toCharArray()) {
            currentLine.append(c);
            charCount++;

            if (charCount >= 50 && (c == ' ' || c == '.' || c == ':')) {
                formattedText.append(currentLine).append("\n");
                currentLine.setLength(0);
                charCount = 0;
            }
        }

        formattedText.append(currentLine); // Aggiungi l'ultima riga rimanente

        return formattedText.toString();
    }


    @FXML
    public void handleLeaseRequestClick()  {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampuscompany/hivecampus/leaseRequestForm-view.fxml"));
            Parent root = loader.load();

            Stage popUpStage = new Stage();
            popUpStage.initModality(Modality.APPLICATION_MODAL);
            popUpStage.setTitle("Lease Request Form");
            Scene scene = new Scene(root, 500, 500);
            // Impostare la finestra come non ridimensionabile
            popUpStage.setResizable(false);
            popUpStage.setScene(scene);
            popUpStage.showAndWait();
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            System.exit(2);
        }
    }
}
