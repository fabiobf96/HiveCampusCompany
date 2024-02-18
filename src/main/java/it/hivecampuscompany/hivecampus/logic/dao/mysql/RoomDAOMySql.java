package it.hivecampuscompany.hivecampus.logic.dao.mysql;

import it.hivecampuscompany.hivecampus.logic.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.logic.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.logic.model.Owner;
import it.hivecampuscompany.hivecampus.logic.model.Room;

import java.util.List;

public class RoomDAOMySql implements RoomDAO {
    @Override
    public List<Room> retrieveRoomsByFilters(FiltersBean filtersBean) {
        return null;
    }

    @Override
    public List<Room> retrieveRoomsByOwner(Owner owner) {
        return null;
    }
}
