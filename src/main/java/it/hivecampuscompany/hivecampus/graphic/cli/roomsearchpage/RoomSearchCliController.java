package it.hivecampuscompany.hivecampus.graphic.cli.roomsearchpage;


import it.hivecampuscompany.hivecampus.graphic.cli.tenanthomepage.TenantHomeCliController;
import it.hivecampuscompany.hivecampus.logic.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.logic.bean.RoomBean;
import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import it.hivecampuscompany.hivecampus.logic.control.RoomLeaseRequestManager;
import it.hivecampuscompany.hivecampus.logic.exception.EmptyListException;
import it.hivecampuscompany.hivecampus.logic.exception.InvalidSessionException;

import java.util.List;

public class RoomSearchCliController {

    private SessionBean sessionBean;
    private final RoomSearchCliView view;
    private final RoomLeaseRequestManager manager;

    public RoomSearchCliController(SessionBean sessionBean) throws InvalidSessionException {
        this.sessionBean = sessionBean;
        this.view = new RoomSearchCliView();
        this.manager = new RoomLeaseRequestManager(sessionBean);
        view.displayRoomSearchMessage();
    }

    public void searchRooms() {
        String choice = "si";

        while (choice.equals("si")) {
            String university = view.getUserInput("Inserisci università: ");
            if (university.isEmpty()) {
                view.displayMessage("Inserire il nome dell'università per avviare la ricerca.");
                continue;
            }

            Float maxDistance = validateNumericInput(view.getUserInput("Inserisci distanza massima: "), 15F);
            Integer maxPrice = (validateNumericInput(view.getUserInput("Inserisci prezzo massimo: "), 1000F)).intValue();
            Boolean privateBathroom = Boolean.parseBoolean(view.getUserInput("Bagno privato? (si/no): "));
            Boolean balcony = Boolean.parseBoolean(view.getUserInput("Balcone? (si/no): "));
            Boolean conditioner = Boolean.parseBoolean(view.getUserInput("Aria condizionata? (si/no): "));
            Boolean tvConnection = Boolean.parseBoolean(view.getUserInput("Aria condizionata? (si/no): "));

            FiltersBean filtersBean = new FiltersBean(university, maxDistance, maxPrice, privateBathroom, balcony, conditioner, tvConnection);

            //filtersBean print to check if the values are correct
            System.out.println(filtersBean);


            try {
                List<RoomBean> roomBeans = manager.searchRoomsByFilters(sessionBean, filtersBean);
                if (roomBeans == null || roomBeans.isEmpty()) {
                    view.displayMessage("Nessuna stanza trovata con i filtri specificati.");
                } else {
                    for (RoomBean roomBean : roomBeans) {
                        // Call the PreviewRoomCliController
                        PreviewRoomCliController previewRoomCliController = new PreviewRoomCliController();
                        previewRoomCliController.showPreviewRoom(roomBean);
                    }
                    choice = view.getUserInput("Vuoi visualizzare i dettagli di una stanza? (si/no): ");
                    if (choice.equals("si")) {
                        // Call the RoomDetailsCliController
                        RoomDetailsCliController roomDetailsCliController = new RoomDetailsCliController(sessionBean);
                        roomDetailsCliController.handleRoomDetails(manager);
                    }
                }
            } catch (InvalidSessionException | EmptyListException e) {
                throw new RuntimeException(e);
            }

            choice = view.getUserInput("Vuoi effettuare una nuova ricerca? (si/no): ");
        }

        if (choice.equals("no")) {
            // Tornare al menu principale
            try {
                TenantHomeCliController tenantController = new TenantHomeCliController(sessionBean);
                tenantController.handleUserChoice();
            } catch (InvalidSessionException e) {
                throw new RuntimeException(e);
            }
        } else {
            view.displayMessage("Scelta non valida");
            searchRooms();
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
                view.displayRoomSearchMessage();
            }
        }
        return Float.parseFloat(input);
    }
}
