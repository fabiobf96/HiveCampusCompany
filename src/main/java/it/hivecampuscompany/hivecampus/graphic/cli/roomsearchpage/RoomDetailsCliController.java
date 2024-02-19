package it.hivecampuscompany.hivecampus.graphic.cli.roomsearchpage;

import it.hivecampuscompany.hivecampus.graphic.cli.leaserequestspage.LeaseRequestCliController;
import it.hivecampuscompany.hivecampus.logic.bean.AccountBean;
import it.hivecampuscompany.hivecampus.logic.bean.RoomBean;
import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import it.hivecampuscompany.hivecampus.logic.control.RoomLeaseRequestManager;

import java.util.ArrayList;
import java.util.List;

public class RoomDetailsCliController {

    private SessionBean sessionBean;
    private final RoomDetailsCliView view;

    public RoomDetailsCliController(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
        this.view = new RoomDetailsCliView();
    }

    public void handleRoomDetails(RoomLeaseRequestManager roomLeaseRequestManager) {
        String idRoom = view.getUserInput("Inserisci l'id della stanza: ");
        RoomBean roomBean = roomLeaseRequestManager.getRoomDetails(idRoom);

        if (roomBean != null) {
            AccountBean ownerAccountBean = roomLeaseRequestManager.getOwnerDetails(roomBean.getOwnerEmail());
            showRoomDetails(roomBean, ownerAccountBean);
            String choice = view.getUserInput("Vuoi effettuare una richiesta di affitto per questa stanza? (si/no): ");
            if (choice.equals("si")) {
                // Call the RoomLeaseRequestCliController
                LeaseRequestCliController leaseRequestCliController = new LeaseRequestCliController(sessionBean);
                leaseRequestCliController.handleRoomLeaseRequest(roomLeaseRequestManager, roomBean, ownerAccountBean);

            }
        }
        else {
            view.displayMessage("Stanza non trovata.");
        }
    }

    private void showRoomDetails(RoomBean roomBean, AccountBean accountBean) {

        List<String> houseFeatures = new ArrayList<>();
        List<String> roomFeatures = new ArrayList<>();
        List<String> ownerDetails = new ArrayList<>();

        String title = "Stanza " + roomBean.getRoomType() + " - " + roomBean.getStreet() + ", " + roomBean.getStreetNumber() + ", " + roomBean.getCity() + " - € " + roomBean.getPrice() + "/mese";
        String distanceFromUniversity = "Distanza dall'università" + roomBean.getUniversity() + ": " + roomBean.getDistance() + " km";
        String availability = "Disponibilità: " + roomBean.getAvailability();

        String houseType = "Tipo casa: " + roomBean.getHouseType();
        String houseSurface = "Superficie casa: " + roomBean.getHouseSurface() + " mq";
        String numRooms = "Numero stanze: " + roomBean.getNumRooms();
        String numBathrooms = "Numero bagni: " + roomBean.getNumBathrooms();
        String floor = "Piano: " + roomBean.getFloor();
        String elevator = "Ascensore: " + roomBean.getElevator();
        String heating = "Riscaldamento: " + roomBean.getHeating();
        String houseDescription = "Descrizione casa: " + roomBean.getHouseDescription();

        String roomSurface = "Superficie: " + roomBean.getRoomSurface() + " mq";
        String privateBath = "Bagno privato: " + roomBean.getPrivateBathroom();
        String balcony = "Balcone: " + roomBean.getBalcony();
        String conditioner = "Aria condizionata: " + roomBean.getConditioner();
        String tv = "Allaccio TV: " + roomBean.getTvConnection();
        String roomDescription = "Descrizione stanza: " + roomBean.getRoomDescription();

        String ownerEmail = "Email: " + accountBean.getEmail();
        String ownerTelephone = "Telefono: " + accountBean.getPhoneNumber();

        houseFeatures.add(houseType);
        houseFeatures.add(houseSurface);
        houseFeatures.add(numRooms);
        houseFeatures.add(numBathrooms);
        houseFeatures.add(floor);
        houseFeatures.add(elevator);
        houseFeatures.add(heating);
        houseFeatures.add(houseDescription);

        roomFeatures.add(roomSurface);
        roomFeatures.add(privateBath);
        roomFeatures.add(balcony);
        roomFeatures.add(conditioner);
        roomFeatures.add(tv);
        roomFeatures.add(roomDescription);

        ownerDetails.add(ownerEmail);
        ownerDetails.add(ownerTelephone);

        view.displayRoomDetails(title, houseFeatures, roomFeatures, ownerDetails, availability, distanceFromUniversity);

    }
}
