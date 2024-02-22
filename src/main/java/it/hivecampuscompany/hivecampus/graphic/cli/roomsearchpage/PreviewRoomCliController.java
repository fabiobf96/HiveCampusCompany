package it.hivecampuscompany.hivecampus.graphic.cli.roomsearchpage;

import it.hivecampuscompany.hivecampus.logic.bean.RoomBean;

import java.util.ArrayList;
import java.util.List;

public class PreviewRoomCliController {
    private final PreviewRoomCliView view;
    public PreviewRoomCliController() {
        this.view = new PreviewRoomCliView();
    }

    public void showPreviewRoom(RoomBean roomBean, int index) {
        List<String> features = new ArrayList<>();
        String title = index + ") - Stanza " + roomBean.getTypeRoom() + " - " + roomBean.getAddress() + " - € " + roomBean.getPrice() + "/mese";
        String surface = "Superficie: " + roomBean.getRoomSurface() + " mq";
        String bath = "Bagno privato: " + (Boolean.TRUE.equals(roomBean.getPrivateBathroom()) ? "si" : "no");
        String balcony = "Balcone: " + (Boolean.TRUE.equals(roomBean.getBalcony()) ? "si" : "no");
        String conditioner = "Aria condizionata: " + (Boolean.TRUE.equals(roomBean.getConditioner()) ? "si" : "no");
        String tv = "Allaccio TV: " + (Boolean.TRUE.equals(roomBean.getTvConnection()) ? "si" : "no");
        String availability = "Disponibilità: " + roomBean.getAvailability();
        String distanceFromUniversity = "Distanza dall'università" + roomBean.getUniversity() + ": " + roomBean.getDistance() + " km";

        features.add(surface);
        features.add(bath);
        features.add(balcony);
        features.add(conditioner);
        features.add(tv);

        view.displayPreviewRoom(title, features, availability, distanceFromUniversity);

    }

}
