package it.hivecampuscompany.hivecampus.logic.bean;

import it.hivecampuscompany.hivecampus.logic.model.Room;

import java.util.List;

public class PreviewRoomBean {

    private Integer idRoom;
    private String address;
    private String typeRoom;
    private Integer price;
    private Boolean available;
    List<LeaseRequestBean> leaseRequestBeans;

    public PreviewRoomBean() {
    }

    public PreviewRoomBean(Room room){
        idRoom = room.getIdRoom();
        address = room.getStreet() + " " + room.getStreetNumber() + " " + room.getCity();
        typeRoom = room.getRoomType();
        price = room.getPrice();
        available = room.getAvailable();
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
        String f50  = "%-50s";
        String f20  = "%-20s";
        String f10  = "%-10d";

        StringBuilder sb = new StringBuilder();

        if (address != null) {
            sb.append(" ");
            sb.append(String.format(f50, address));
        }

        if (typeRoom != null) {
            sb.append(" | ");
            sb.append(String.format(f20, typeRoom));
        }

        if (price != null) {
            sb.append(" | ");
            sb.append("€");
            sb.append(String.format(f10, price));
        }

        sb.append(" |");
        return sb.toString();
    }

    public void setLeaseRequestBean(List<LeaseRequestBean> leaseRequestBeans) {
        this.leaseRequestBeans = leaseRequestBeans;
    }

    public List<LeaseRequestBean> getLeaseRequestBeans(){
        return leaseRequestBeans;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
