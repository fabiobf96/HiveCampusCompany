package it.hivecampuscompany.hivecampus.logic.dao;

import it.hivecampuscompany.hivecampus.logic.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.logic.model.Account;
import it.hivecampuscompany.hivecampus.logic.model.Owner;
import it.hivecampuscompany.hivecampus.logic.model.Room;

import java.util.List;

public interface RoomDAO {
    List<Room> retrieveRoomsByFilters(FiltersBean filtersBean);
    List<Room> retrieveRoomsByOwner(Account owner);
    String getRoomPath(String typeRoom);
}
