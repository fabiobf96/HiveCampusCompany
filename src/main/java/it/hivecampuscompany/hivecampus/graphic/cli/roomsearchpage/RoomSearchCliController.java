package it.hivecampuscompany.hivecampus.graphic.cli.roomsearchpage;

import it.hivecampuscompany.hivecampus.graphic.cli.tenanthomepage.TenantHomeCliController;
import it.hivecampuscompany.hivecampus.logic.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.logic.bean.RoomBean;
import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import it.hivecampuscompany.hivecampus.logic.control.RoomLeaseRequestManager;
import it.hivecampuscompany.hivecampus.logic.exception.EmptyListException;
import it.hivecampuscompany.hivecampus.logic.exception.InvalidSessionException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static    org.apache.commons.lang3.ObjectUtils.isEmpty;


public class RoomSearchCliController {

    private final SessionBean sessionBean;
    private final RoomSearchCliView view;
    private final RoomLeaseRequestManager manager;


    public RoomSearchCliController(SessionBean sessionBean) throws InvalidSessionException {
        this.sessionBean = sessionBean;
        this.view = new RoomSearchCliView();
        this.manager = new RoomLeaseRequestManager(sessionBean);
        view.displayRoomSearchMessage();
    }

    public void searchRooms() {
        do {
            String university = getNonEmptyInput();
            Float maxDistance = getValidatedFloatInput("Inserisci distanza massima: ", 15F);
            Integer maxPrice = getValidatedFloatInput("Inserisci prezzo massimo: ", 1000F).intValue();
            Boolean privateBathroom = getBooleanInput("Bagno privato? (si/no): ");
            Boolean balcony = getBooleanInput("Balcone? (si/no): ");
            Boolean conditioner = getBooleanInput("Aria condizionata? (si/no): ");
            Boolean tvConnection = getBooleanInput("Connessione TV? (si/no): "); // Correzione per unicità dell'input

            FiltersBean filtersBean = new FiltersBean(university, maxDistance, maxPrice, privateBathroom, balcony, conditioner, tvConnection);
            processSearch(filtersBean);
        } while (Boolean.TRUE.equals(confirm("Vuoi effettuare una nuova ricerca? (si/no): ")));
        try {
            TenantHomeCliController tenantHomeCliController = new TenantHomeCliController(sessionBean);
            tenantHomeCliController.handleUserChoice();
        } catch (InvalidSessionException e) {
            Logger.getLogger(RoomSearchCliController.class.getName()).log(Level.SEVERE, e.getMessage());
            System.exit(2);
        }
    }


    private String getNonEmptyInput() {
        String input;
        do {
            input = view.getUserInput("Inserisci università: ");
            if (input.isEmpty()) displayErrorMessage("Il campo non può essere vuoto.");
        } while (input.isEmpty());
        return input;
    }

    private Boolean getBooleanInput(String message) {
        return confirm(message);
    }

    private Float getValidatedFloatInput(String message, Float defaultValue) {
        Float input;
        do {
            input = validateNumericInput(view.getUserInput(message), defaultValue);
            if (input == null) displayErrorMessage("Input non valido, riprova.");
        } while (input == null);
        return input;
    }

    private Boolean confirm(String message) {
        return view.getUserInput(message).equalsIgnoreCase("si");
    }


    private void processSearch(FiltersBean filtersBean) {
        try {
            List<RoomBean> roomBeans = manager.searchRoomsByFilters(sessionBean, filtersBean);
            if (isEmpty(roomBeans)) {
                displayInfoMessage();
                return;
            }
            displaySearchResults(roomBeans);
            if (Boolean.TRUE.equals(confirm("Vuoi visualizzare i dettagli di una stanza? (si/no): "))) {
                showRoomDetails();
            }
        } catch (InvalidSessionException | EmptyListException e) {
            logError(e.getMessage());
        }
    }
    private void showRoomDetails() {
        RoomDetailsCliController roomDetailsCliController = new RoomDetailsCliController(sessionBean, manager);
        try {
            roomDetailsCliController.handleRoomDetails();
        } catch (InvalidSessionException e) {
            Logger.getLogger(RoomSearchCliController.class.getName()).log(Level.SEVERE, e.getMessage());
            System.exit(2);
        }
    }
    private void displayErrorMessage(String message) {
        view.displayMessage("Errore: " + message);
    }

    private void displayInfoMessage() {
        view.displayMessage("Nessuna stanza trovata con i filtri specificati.");
    }

    private void logError(String message) {
        Logger.getLogger(RoomSearchCliController.class.getName()).log(Level.SEVERE, message);
    }
    public void displaySearchResults(List<RoomBean> roomBeans) {
        if (roomBeans == null || roomBeans.isEmpty()) {
            view.displayMessage("Nessun risultato trovato");
            return;
        }
        for (int i = 0; i < roomBeans.size(); i++) {
            // Call the PreviewRoomCliController
            PreviewRoomCliController previewRoomCliController = new PreviewRoomCliController();
            previewRoomCliController.showPreviewRoom(roomBeans.get(i), i);
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
                view.displayMessage("Invalid filter value specified.");
                return defaultValue;
            }
        }
        return Float.parseFloat(input);
    }
}
