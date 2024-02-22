package it.hivecampuscompany.hivecampus.graphic.cli.roomsearchpage;

import it.hivecampuscompany.hivecampus.graphic.cli.leaserequestspage.LeaseRequestCliController;
import it.hivecampuscompany.hivecampus.logic.bean.AccountBean;
import it.hivecampuscompany.hivecampus.logic.bean.RoomBean;
import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import it.hivecampuscompany.hivecampus.logic.control.RoomLeaseRequestManager;
import it.hivecampuscompany.hivecampus.logic.exception.InvalidSessionException;

import java.util.Arrays;
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
        List<String> houseFeatures = buildHouseFeaturesList(roomBean);
        List<String> roomFeatures = buildRoomFeaturesList(roomBean);
        List<String> ownerDetails = buildOwnerDetailsList(accountBean);

        String title = buildTitle(roomBean);
        String availability = "Disponibilità: " + roomBean.getAvailability();
        String distanceFromUniversity = buildDistanceFromUniversity(roomBean);

        view.displayRoomDetails(title, houseFeatures, roomFeatures, ownerDetails, availability, distanceFromUniversity);
    }

    private List<String> buildHouseFeaturesList(RoomBean roomBean) {
        return Arrays.asList(
                "Tipo casa: " + roomBean.getHouseType(),
                "Superficie casa: " + roomBean.getHouseSurface() + " mq",
                "Numero stanze: " + roomBean.getNumRooms(),
                "Numero bagni: " + roomBean.getNumBathrooms(),
                "Piano: " + roomBean.getFloor(),
                "Ascensore: " + roomBean.getElevator(),
                "Riscaldamento: " + roomBean.getHeating(),
                "Descrizione casa: " + roomBean.getHouseDescription()
        );
    }

    private List<String> buildRoomFeaturesList(RoomBean roomBean) {
        return Arrays.asList(
                "Superficie: " + roomBean.getRoomSurface() + " mq",
                "Bagno privato: " + roomBean.getPrivateBathroom(),
                "Balcone: " + roomBean.getBalcony(),
                "Aria condizionata: " + roomBean.getConditioner(),
                "Allaccio TV: " + roomBean.getTvConnection(),
                "Descrizione stanza: " + roomBean.getRoomDescription()
        );
    }

    private List<String> buildOwnerDetailsList(AccountBean accountBean) {
        return Arrays.asList(
                "Email: " + accountBean.getEmail(),
                "Telefono: " + accountBean.getPhoneNumber()
        );
    }

    private String buildTitle(RoomBean roomBean) {
        return "Stanza " + roomBean.getTypeRoom() + " - " + roomBean.getAddress() + " - € " + roomBean.getPrice() + "/mese";
    }

    private String buildDistanceFromUniversity(RoomBean roomBean) {
        return "Distanza dall'università " + roomBean.getUniversity() + ": " + roomBean.getDistance() + " km";
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
