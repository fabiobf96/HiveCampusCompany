package it.hivecampuscompany.hivecampus.logic.bean;

import it.hivecampuscompany.hivecampus.logic.decorator.RoomBeanDecorator;
import it.hivecampuscompany.hivecampus.logic.model.Room;

import java.util.List;

public class PreviewRoomBean {

    private Integer idRoom;
    private String address;
    private String typeRoom;
    private Integer price;
    List<LeaseRequestBean> leaseRequestBeans;

    public PreviewRoomBean() {
    }

    public PreviewRoomBean(Room room){
        idRoom = room.getIdRoom();
        address = room.getStreet() + " " + room.getStreetNumber() + " " + room.getCity();
        typeRoom = room.getRoomType();
        price = room.getPrice();
    }

    public Integer getIdRoom() {
        return idRoom;
    }

    public String getAddress() {
        return address;
    }

    public String getTypeRoom() {
        return typeRoom;
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public String toString() {
        String format = "| %-10s | %-50s | %-20s | %-10.2f |";
        return String.format(format, idRoom, address, typeRoom, price);
    }

    public void setLeaseRequestBean(List<LeaseRequestBean> leaseRequestBeans) {
        this.leaseRequestBeans = leaseRequestBeans;
    }

    public List<LeaseRequestBean> getLeaseRequestBeans(){
        return leaseRequestBeans;
    }
}
