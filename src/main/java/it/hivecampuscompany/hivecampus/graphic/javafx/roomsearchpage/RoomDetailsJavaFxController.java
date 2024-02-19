package it.hivecampuscompany.hivecampus.graphic.javafx.roomsearchpage;

import it.hivecampuscompany.hivecampus.logic.bean.AccountBean;
import it.hivecampuscompany.hivecampus.logic.bean.RoomBean;
import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class RoomDetailsJavaFxController {

    private SessionBean sessionBean;

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

    public RoomDetailsJavaFxController(){
        // Default constructor
    }
    /*
    public void initialize(RoomBean roomBean, AccountBean accountBean, SessionBean sessionBean){
        lblTitle.setText("Stanza " + roomBean.getRoomType() + " - " + roomBean.getStreet() + ", " + roomBean.getStreetNumber() + ", " + roomBean.getCity() + " - € " + roomBean.getPrice() + "/mese");
        lblUniversity.setText(roomBean.getUniversity());
        lblDistance.setText(roomBean.getDistance());
        lblHType.setText(roomBean.getHouseType());
        lblHSurface.setText(roomBean.getHouseSurface());
        lblNumRooms.setText(roomBean.getNumRooms());
        lblNumBathrooms.setText(roomBean.getNumBathrooms());
        lblFloor.setText(roomBean.getFloor());
        lblElevator.setText(roomBean.getElevator());
        lblHeating.setText(roomBean.getHeating());
        txfHDescription.setText(formatText(roomBean.getHouseDescription(), 50));
        lblEmail.setText(accountBean.getEmail());
        lblTelephone.setText(accountBean.getPhoneNumber());
        lblRSurface.setText(roomBean.getRoomSurface());
        lblBath.setText(roomBean.getPrivateBathroom());
        lblBalcony.setText(roomBean.getBalcony());
        lblConditioner.setText(roomBean.getConditioner());
        lblTV.setText(roomBean.getTvConnection());
        txfRDescription.setText(formatText(roomBean.getRoomDescription(), 50));
        lblAvailability.setText(roomBean.getAvailability());
    }

    private String formatText(String text, int charactersPerLine) {
        StringBuilder formattedText = new StringBuilder();
        StringBuilder currentLine = new StringBuilder();
        int charCount = 0;

        for (char c : text.toCharArray()) {
            currentLine.append(c);
            charCount++;

            if (charCount >= charactersPerLine && (c == ' ' || c == '.' || c == ':')) {
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampusteam/hivecampus/leaseRequestForm-view.fxml"));
            Parent root = loader.load();

            // LeaseRequestJavaFxController
            //loader getController

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
            e.printStackTrace();
        }

    }


     */


}
