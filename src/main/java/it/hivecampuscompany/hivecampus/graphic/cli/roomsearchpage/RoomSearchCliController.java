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

    private final SessionBean sessionBean;
    private final RoomSearchCliView view;
    private final RoomLeaseRequestManager manager;

    public RoomSearchCliController(SessionBean sessionBean) throws InvalidSessionException {
        //validateSessionBean(sessionBean);
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
            Boolean privateBathroom = (view.getUserInput("Bagno privato? (si/no): ")).equalsIgnoreCase("si");
            Boolean balcony = (view.getUserInput("Balcone? (si/no): ")).equalsIgnoreCase("si");
            Boolean conditioner = (view.getUserInput("Aria condizionata? (si/no): ")).equalsIgnoreCase("si");
            Boolean tvConnection = (view.getUserInput("Aria condizionata? (si/no): ")).equalsIgnoreCase("si");

            FiltersBean filtersBean = new FiltersBean(university, maxDistance, maxPrice, privateBathroom, balcony, conditioner, tvConnection);

            //filtersBean print to check if the values are correct
            System.out.println(filtersBean);


            try {
                List<RoomBean> roomBeans = manager.searchRoomsByFilters(sessionBean, filtersBean);
                if (roomBeans == null || roomBeans.isEmpty()) {
                    view.displayMessage("Nessuna stanza trovata con i filtri specificati.");
                } else {

                    resultRoomSearch(roomBeans);
                    choice = view.getUserInput("Vuoi visualizzare i dettagli di una stanza? (si/no): ");

                    if (choice.equals("si")) {
                        // Call the RoomDetailsCliController
                        RoomDetailsCliController roomDetailsCliController = new RoomDetailsCliController(sessionBean, manager);
                        roomDetailsCliController.handleRoomDetails();
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

    public void resultRoomSearch(List<RoomBean> roomBeans) {
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
