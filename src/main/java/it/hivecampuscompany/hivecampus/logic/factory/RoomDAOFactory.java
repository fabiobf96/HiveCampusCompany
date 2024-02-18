package it.hivecampuscompany.hivecampus.logic.factory;

import it.hivecampuscompany.hivecampus.logic.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.logic.dao.mysql.RoomDAOMySql;

public class RoomDAOFactory implements Factory{
    @Override
    public RoomDAO getDAO(String typePersistence) throws IllegalArgumentException{
        return switch (typePersistence){
            case "csv" -> createRoomDAOCSV();
            case "mysql" -> createRoomDAOMySql();
            default -> throw new IllegalArgumentException("Unsupported persistence type: " + typePersistence);
        };
    }

    private RoomDAO createRoomDAOCSV() {
        return new RoomDAOMySql();
    }

    public RoomDAO createRoomDAOMySql(){
        return new RoomDAOMySql();
    }
}
