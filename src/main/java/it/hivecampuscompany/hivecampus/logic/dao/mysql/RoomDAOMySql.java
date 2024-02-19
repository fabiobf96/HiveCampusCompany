package it.hivecampuscompany.hivecampus.logic.dao.mysql;

import it.hivecampuscompany.hivecampus.logic.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.logic.control.ConnectionManager;
import it.hivecampuscompany.hivecampus.logic.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.logic.model.Owner;
import it.hivecampuscompany.hivecampus.logic.model.Room;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RoomDAOMySql implements RoomDAO {
    private final Connection conn;
    private final Properties properties;

    public RoomDAOMySql() {
        conn = ConnectionManager.getConnection();
        properties = new Properties();
        try (InputStream input = new FileInputStream("properties/db.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error loading database properties.", e);
        }
    }

    @Override
    public List<Room> retrieveRoomsByFilters(FiltersBean filtersBean) {
        return null;
    }

    @Override
    public List<Room> retrieveRoomsByOwner(Owner owner) {
        List<Room> rooms = new ArrayList<>();
        String query = properties.getProperty("QUERY_ROOM_BY_OWNER");
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, owner.getEmail());
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                rooms.add(fillRoom(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rooms;
    }

    private Room fillRoom(ResultSet resultSet) throws SQLException {
        Room room = new Room();

        // Assumi che Room abbia setter per tutti questi campi
        room.setIdRoom(resultSet.getInt("idStanza"));
        room.setRoomType(resultSet.getString("tipologiaStanza"));
        room.setRoomSurface(resultSet.getInt("superficieStanza"));
        room.setPrivateBathroom(resultSet.getBoolean("bagnoPrivato"));
        room.setBalcony(resultSet.getBoolean("balcone"));
        room.setConditioner(resultSet.getBoolean("condizionatore"));
        room.setTvConnection(resultSet.getBoolean("tv"));
        room.setRoomDescription(resultSet.getString("descrizioneStanza"));
        room.setPrice(resultSet.getFloat("prezzo"));
        room.setAvailability(resultSet.getString("disponibilita"));

        // Assumi che Room abbia anche attributi per le informazioni dell'immobile
        room.setStreet(resultSet.getString("via") + ", " + resultSet.getString("civico") + ", " + resultSet.getString("citta"));
        room.setHouseLatitude(resultSet.getFloat("latitudine"));
        room.setHouseLongitude(resultSet.getFloat("longitudine"));
        room.setHouseType(resultSet.getString("tipologia"));
        room.setHouseSurface(resultSet.getInt("superficie"));
        room.setNumRooms(resultSet.getInt("locali"));
        room.setNumBathrooms(resultSet.getInt("servizi"));
        room.setFloor(resultSet.getInt("piano"));
        room.setElevator(resultSet.getBoolean("ascensore"));
        room.setHeating(resultSet.getString("riscaldamento"));
        room.setHouseDescription(resultSet.getString("descrizione"));
        room.setIdHome(resultSet.getInt("idImmobile"));

        return room;
    }
}
