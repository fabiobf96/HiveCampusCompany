package it.hivecampuscompany.hivecampus.logic.model;

import java.util.List;

public class Owner extends Account{
    private List<Room> rooms;

    public Owner(Account account) {
        super(account.getEmail(), account.getPassword(), account.getName(), account.getSurname(), account.getTypeAccount(), account.getPhoneNumber());
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
