package it.hivecampuscompany.hivecampus.graphic.javafx.roomsearchpage;

import it.hivecampuscompany.hivecampus.logic.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.logic.bean.RoomBean;
import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import it.hivecampuscompany.hivecampus.logic.control.RoomLeaseRequestManager;
import it.hivecampuscompany.hivecampus.logic.exception.InvalidSessionException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.List;


public class RoomSearchJavaFxController {

    private RoomLeaseRequestManager roomLeaseRequestManager;
    private SessionBean sessionBean;

    @FXML
    private TextField searchField;

    @FXML
    private CheckBox ckbPrivateBath;

    @FXML
    private CheckBox ckbBalcony;

    @FXML
    private CheckBox ckbConditioner;

    @FXML
    private CheckBox ckbTvConnection;

    @FXML
    private TextField txfMaxPrice;

    @FXML
    private TextField txfDistance;

    @FXML
    private ListView<VBox> lvRooms;

    public RoomSearchJavaFxController() throws InvalidSessionException {
        // Default constructor required by FXML
        this.roomLeaseRequestManager = new RoomLeaseRequestManager(sessionBean);
    }

    public RoomSearchJavaFxController(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    @FXML
    public void handleSearchButtonClick() {

        // Manage the search button click event
        String university = searchField.getText().trim();
        Float maxDistance = validateNumericInput(txfDistance.getText(), 15F); // Check distance
        Integer maxPrice = (validateNumericInput(txfMaxPrice.getText(), 1000)).intValue(); // Check price
        Boolean privateBath = ckbPrivateBath.isSelected();
        Boolean balcony = ckbBalcony.isSelected();
        Boolean conditioner = ckbConditioner.isSelected();
        Boolean tvConnection = ckbTvConnection.isSelected();

        if (university.isEmpty()) {
            showAlert("Enter the name of the university to start the search.");
            return;
        }

        FiltersBean filtersBean;

        try{
            filtersBean = new FiltersBean(university,maxDistance,maxPrice,privateBath,balcony,conditioner,tvConnection);

            List<RoomBean> roomBeans = roomLeaseRequestManager.searchRoomsByFilters(sessionBean, filtersBean);

            if (roomBeans == null || roomBeans.isEmpty()) {
                showAlert("Nessun risultato trovato");
                return;
            }

            for (RoomBean roomBean : roomBeans) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/hivecampusteam/hivecampus/previewRoom-view.fxml"));
                VBox previewRoomVBox = loader.load();

                PreviewRoomJavaFxController previewRoomController = loader.getController();
                previewRoomController.initialize(roomLeaseRequestManager, roomBean, sessionBean);

                lvRooms.getItems().add(previewRoomVBox);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Float validateNumericInput(String input, float defaultValue) {
        // Verify if the input is empty
        if (input.isEmpty()){
            return defaultValue;
        }
        else {
            // Verifica se il valore supera il limite o non rappresenta un numero
            try {
                float floatValue = Float.parseFloat(input);
                if (floatValue > defaultValue) {
                    return defaultValue;
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid filter value specified.");
            }
        }
        return Float.parseFloat(input);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Attention");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}