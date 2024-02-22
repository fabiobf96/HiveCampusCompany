package it.hivecampuscompany.hivecampus.logic.decorator;

import it.hivecampuscompany.hivecampus.logic.bean.RoomBean;

public abstract class RoomBeanDecorator extends RoomBean {
    protected RoomBean decoratedRoomBean;

    protected RoomBeanDecorator(RoomBean decoratedRoomBean) {
        this.decoratedRoomBean = decoratedRoomBean;
    }
}
