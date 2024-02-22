package it.hivecampuscompany.hivecampus.graphic.cli.roomsearchpage;

import it.hivecampuscompany.hivecampus.graphic.cli.leaserequestspage.LeaseRequestCliController;
import it.hivecampuscompany.hivecampus.logic.bean.AccountBean;
import it.hivecampuscompany.hivecampus.logic.bean.RoomBean;
import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import it.hivecampuscompany.hivecampus.logic.control.RoomLeaseRequestManager;
import it.hivecampuscompany.hivecampus.logic.exception.InvalidSessionException;

import java.util.ArrayList;
import java.util.List;

public class RoomDetailsCliController {

    private final RoomLeaseRequestManager manager;

    private final SessionBean sessionBean;
    private final RoomDetailsCliView view;

    public RoomDetailsCliController(SessionBean sessionBean, RoomLeaseRequestManager manager) {
        this.sessionBean = sessionBean;
        this.manager = manager;
        this.view = new RoomDetailsCliView();
    }


    public void handleRoomDetails() throws InvalidSessionException {  // la vecchia versione sta su sublime
        int index = getValidIntegerInput("Inserisci l'indice della stanza: ");
        RoomBean roomBean = manager.getRoomDetails(index);

        if (roomBean != null) {

            AccountBean ownerAccountBean = manager.getOwnerDetails(roomBean.getOwnerEmail());
            showRoomDetails(roomBean, ownerAccountBean);

            // Verifica se l'utente ha già una richiesta attiva per questa stanza
            boolean hasActiveRequest = manager.hasActiveLeaseRequest(sessionBean, roomBean.getIdRoom()); /////////////

            if (hasActiveRequest) {
                view.displayMessage("Hai già una richiesta attiva per questa stanza.");
                // torna indietro
            }

            else {
                String choice = view.getUserInput("Vuoi effettuare una richiesta di affitto per questa stanza? (si/no): ");
                if (choice.equals("si")) {
                    // Call the RoomLeaseRequestCliController
                    LeaseRequestCliController leaseRequestCliController = new LeaseRequestCliController(sessionBean, manager);
                    leaseRequestCliController.handleRoomLeaseRequest(roomBean);
                }
            }

        } else {
            view.displayMessage("Stanza non trovata.");
        }
    }


    private void showRoomDetails(RoomBean roomBean, AccountBean accountBean) {

        List<String> houseFeatures = new ArrayList<>();
        List<String> roomFeatures = new ArrayList<>();
        List<String> ownerDetails = new ArrayList<>();

        String title = "Stanza " + roomBean.getTypeRoom() + " - " + roomBean.getAddress() + " - € " + roomBean.getPrice() + "/mese";
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

    public Integer getValidIntegerInput(String message) {
        Integer result = null;
        boolean isValid = false;

        while (!isValid) {
            String userInput = view.getUserInput(message);
            if (userInput.isEmpty()) {
                System.out.println("Devi inserire un valore.");
                continue;
            }
            try {
                result = Integer.parseInt(userInput);
                isValid = true; // Imposta isValid a true se l'input è un intero valido
            } catch (NumberFormatException e) {
                System.out.println("Inserire un valore numerico valido.");
            }
        }
        return result;
    }

}
