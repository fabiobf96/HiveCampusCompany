package it.hivecampuscompany.hivecampus.graphic.cli.roomsearchpage;

import java.util.List;

public class PreviewRoomCliView {
    public void displayPreviewRoom(String title, List<String> features, String availability, String distanceFromUniversity) {
        System.out.println("_____________________________________________________________");
        System.out.println(title);
        for (String feature : features) {
            System.out.println(feature);
        }
        System.out.println(availability);
        System.out.println(distanceFromUniversity);
        System.out.println("_____________________________________________________________");
    }
}
