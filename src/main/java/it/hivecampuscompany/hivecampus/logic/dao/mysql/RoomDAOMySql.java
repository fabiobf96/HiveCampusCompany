package it.hivecampuscompany.hivecampus.logic.dao.mysql;

import it.hivecampuscompany.hivecampus.logic.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.logic.control.ConnectionManager;
import it.hivecampuscompany.hivecampus.logic.dao.RoomDAO;
import it.hivecampuscompany.hivecampus.logic.dao.queries.SimpleQueries;
import it.hivecampuscompany.hivecampus.logic.model.Account;
import it.hivecampuscompany.hivecampus.logic.model.Room;
import it.hivecampuscompany.hivecampus.logic.utility.RoundingFunction;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoomDAOMySql implements RoomDAO {
    private final Connection conn;
    private final Properties properties;

    private static final Logger LOGGER = Logger.getLogger(RoomDAOMySql.class.getName());

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

        // Utilizzo del blocco try-with-resources per garantire che le risorse siano chiuse correttamente
        try {
            String sql = SimpleQueries.buildRoomFiltersQuery(filtersBean);

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, filtersBean.getUniversity());
                pstmt.setFloat(2, filtersBean.getDistance());
                pstmt.setInt(3, filtersBean.getMaxPrice());

                try (ResultSet res = pstmt.executeQuery()) {
                    // Se il result set è vuoto, restituisci una lista vuota
                    if (!res.first()) {
                        return Collections.emptyList();
                    }

                    // Riposizionamento del cursore al primo record
                    res.first();

                    do {
                        try (Statement stmt = conn.createStatement();
                             ResultSet res2 = SimpleQueries.selectOwnerByIdHome(stmt, res.getInt("idImmobile"))) {

                            // Se il result set è vuoto, restituisci una lista vuota
                            if (!res2.first()) {
                                return Collections.emptyList();
                            }

                            res2.first();

                            //Creo un'istanza della classe Account
                            Account owner = new Account(res2.getString("username"), null, res2.getString("nome"), res2.getString("cognome"), null, res2.getString("telefono"));

                            // Creo un'istanza della classe Room
                            Room room = fillRoom(res);

                            room.setUniversity(res.getString("universita"));
                            room.setDistance(RoundingFunction.roundingDouble(Float.parseFloat(res.getString("distanza"))));

                            room.setOwnerAccount(owner);

                            listOfRooms.add(room); // Aggiungo l'oggetto Room alla lista
                        }
                    } while (res.next());
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(LeaseRequestDAOMySql.class.getName()).log(Level.SEVERE, e.getMessage());
        }

        return listOfRooms;
    }


    @Override
    public List<Room> retrieveRoomsByOwner(Account owner) {
        List<Room> rooms = new ArrayList<>();
        String query = properties.getProperty("QUERY_ROOM_BY_OWNER");
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, owner.getEmail());
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                rooms.add(fillRoom(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to load CSV properties", e);
            System.exit(1);
        }
        return rooms;
    }

    @Override
    public void updateAvailability(Room room) {
        String query = properties.getProperty("QUERY_UPDATE_AVAILABILITY");
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBoolean(1, room.getAvailable());
            stmt.setInt(2, room.getIdRoom());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to load CSV properties", e);
            System.exit(1);
        }
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
        room.setAvailable(resultSet.getBoolean("disponibilita"));
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
