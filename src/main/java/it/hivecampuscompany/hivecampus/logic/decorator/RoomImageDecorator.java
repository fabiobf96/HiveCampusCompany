package it.hivecampuscompany.hivecampus.logic.decorator;

import it.hivecampuscompany.hivecampus.logic.bean.RoomBean;

public class RoomImageDecorator extends RoomBeanDecorator {
    // Implementazione del decoratore per le immagini
    private final byte[] image;

    public RoomImageDecorator(RoomBean roomBean, byte[] roomImage) {
        super(roomBean);
        this.image = roomImage;
    }

    public byte[] getRoomImage() {
        return image;
    }
}