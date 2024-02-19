package it.hivecampuscompany.hivecampus.graphic.cli.roomsearchpage;


import it.hivecampuscompany.hivecampus.logic.bean.RoomBean;

import java.util.ArrayList;
import java.util.List;

public class PreviewRoomCliController {
    private final PreviewRoomCliView view;
    public PreviewRoomCliController() {
        this.view = new PreviewRoomCliView();
    }

    public void showPreviewRoom(RoomBean roomBean) {
        List<String> features = new ArrayList<>();
        String idRoom = "ID: " + roomBean.getIdRoom();
        String title = "Stanza " + roomBean.getTypeRoom() + " - " + roomBean.getAddress() + " - € " + roomBean.getPrice() + "/mese";
        String surface = "Superficie: " + roomBean.getRoomSurface() + " mq";
        String bath = "Bagno privato: " + roomBean.getPrivateBathroom();
        String balcony = "Balcone: " + roomBean.getBalcony();
        String conditioner = "Aria condizionata: " + roomBean.getConditioner();
        String tv = "Allaccio TV: " + roomBean.getTvConnection();
        String availability = "Disponibilità: " + roomBean.getAvailability();
        String distanceFromUniversity = "Distanza dall'università" + roomBean.getUniversity() + ": " + roomBean.getDistance() + " km";

        features.add(surface);
        features.add(bath);
        features.add(balcony);
        features.add(conditioner);
        features.add(tv);

        view.displayPreviewRoom(idRoom, title, features, availability, distanceFromUniversity);
    }

}
