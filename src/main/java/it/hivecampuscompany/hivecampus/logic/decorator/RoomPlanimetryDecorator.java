package it.hivecampuscompany.hivecampus.logic.decorator;

import it.hivecampuscompany.hivecampus.logic.bean.RoomBean;

public class RoomPlanimetryDecorator extends RoomBeanDecorator{
    private final byte[] image;

    public RoomPlanimetryDecorator(RoomBean roomBean, byte[] roomImage) {
        super(roomBean);
        this.image = roomImage;
    }

    public byte[] getRoomImage() {
        return image;
    }
}