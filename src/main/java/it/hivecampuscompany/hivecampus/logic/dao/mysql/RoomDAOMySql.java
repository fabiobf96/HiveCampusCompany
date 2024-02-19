package it.hivecampuscompany.hivecampus.logic.dao.mysql;

import it.hivecampuscompany.hivecampus.logic.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.logic.control.ConnectionManager;
import it.hivecampuscompany.hivecampus.logic.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.logic.dao.queries.SimpleQueries;
import it.hivecampuscompany.hivecampus.logic.model.Account;
import it.hivecampuscompany.hivecampus.logic.model.Owner;
import it.hivecampuscompany.hivecampus.logic.model.Room;
import it.hivecampuscompany.hivecampus.logic.utility.RoundingFunction;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

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

        List<Room> listOfRooms = new ArrayList<>();

        try {
            String sql = SimpleQueries.buildRoomFiltersQuery(filtersBean);

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, filtersBean.getUniversity());
            pstmt.setFloat(2, filtersBean.getDistance());
            pstmt.setInt(3, filtersBean.getMaxPrice());

            ResultSet res = pstmt.executeQuery();

            ////////////////
            if (!res.first()) { // se res è vuoto, non esistono stanze in affitto nella citta specificata
                return Collections.emptyList();
            }
            /////////////

            // riposizionamento del cursore
            res.first();

            do {
                Statement stmt = conn.createStatement();
                ResultSet res2 = SimpleQueries.selectOwnerByIdHome(stmt, res.getInt("idImmobile"));

                if (!res2.first()) { // se res è vuoto, non esiste un account con l'email specificata
                    return Collections.emptyList();
                }

                res2.first();

                //Creo un'istanza della classe Account
                Account owner = new Account(res2.getString("username"), null,res2.getString("nome"), res2.getString("cognome"), null, res2.getString("telefono"));

                // Creo un'istanza della classe Room
                Room room = fillRoom(res);

                room.setAvailable(res.getBoolean("disponibilita"));
                room.setUniversity(res.getString("universita"));
                room.setDistance(RoundingFunction.roundingDouble(Float.parseFloat(res.getString("distanza"))));

                room.setOwnerAccount(owner);

                listOfRooms.add(room); // Aggiungo l'oggetto Room alla lista

            }while (res.next());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listOfRooms;
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
        room.setPrice(resultSet.getInt("prezzo"));
        room.setAvailability(resultSet.getString("disponibilita"));
        room.setAvailability(resultSet.getString("meseDisponibilita"));

        // Assumi che Room abbia anche attributi per le informazioni dell'immobile
        room.setStreet(resultSet.getString("via"));
        room.setStreetNumber(resultSet.getInt("civico"));
        room.setCity(resultSet.getString("citta"));
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
