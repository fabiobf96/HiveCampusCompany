package it.hivecampuscompany.hivecampus.logic.dao.queries;

import it.hivecampuscompany.hivecampus.logic.bean.CredentialsBean;
import it.hivecampuscompany.hivecampus.logic.bean.FiltersBean;

import java.sql.*;

public class SimpleQueries {

    private static final String SELECT_QUERY_PREFIX = "SELECT\n";

    private SimpleQueries(){
        //Default constructor
    }
    public static ResultSet checkAccount(Statement stmt, CredentialsBean credentialsBean) throws SQLException {
        String email = credentialsBean.getEmail();
        String password = credentialsBean.getPassword();

        String sql = SELECT_QUERY_PREFIX  +
                "    A.username,\n" +
                "    A.ruolo,\n" +
                "    A.password,\n" +
                "    COALESCE(P.nome, Af.nome) AS nome,\n" +
                "    COALESCE(P.cognome, Af.cognome) AS cognome,\n" +
                "    COALESCE(P.telefono, Af.telefono) AS telefono\n" +
                "    \n" +
                "FROM hivecampus.account A\n" +
                "LEFT JOIN hivecampus.proprietario P ON A.username = P.username AND A.ruolo = 'owner'\n" +
                "LEFT JOIN hivecampus.affittuario Af ON A.username = Af.username AND A.ruolo = 'tenant'\n" +
                "WHERE A.username = '" + email + "' AND A.password = '" + password +"';";

        return stmt.executeQuery(sql);
    }

    public static ResultSet selectAccountInformation(Statement stmt, String email) throws SQLException {
        ResultSet res;
        String sql = SELECT_QUERY_PREFIX  +
                "    A.username,\n" +
                "    A.ruolo,\n" +
                "    COALESCE(P.nome, Af.nome) AS nome,\n" +
                "    COALESCE(P.cognome, Af.cognome) AS cognome,\n" +
                "    COALESCE(P.telefono, Af.telefono) AS telefono\n" +
                "    \n" +
                "FROM hivecampus.account A\n" +
                "LEFT JOIN hivecampus.proprietario P ON A.username = P.username AND A.ruolo = 'owner'\n" +
                "LEFT JOIN hivecampus.affittuario Af ON A.username = Af.username AND A.ruolo = 'tenant'\n" +
                "WHERE A.username = '" + email + "';";
        res = stmt.executeQuery(sql);
        return res;
    }

    public static ResultSet selectOwnerByIdHome (Statement stmt, int idHome) throws SQLException {
        ResultSet res;
        String sql = SELECT_QUERY_PREFIX  +
                "    P.username,\n" +
                "    P.nome,\n" +
                "    P.cognome,\n" +
                "    P.telefono\n" +
                "FROM hivecampus.proprietario P\n" +
                "JOIN hivecampus.immobile I ON P.username = I.proprietario\n" +
                "WHERE I.idImmobile = " + idHome + ";";
        res = stmt.executeQuery(sql);
        return res;
    }

    public static ResultSet selectRoomsByFilters(Connection conn, FiltersBean filtersBean) throws SQLException {
        String sql = buildRoomFiltersQuery(filtersBean);

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int parameterIndex = 1;
            pstmt.setString(parameterIndex++, filtersBean.getUniversity());
            pstmt.setString(parameterIndex++, String.valueOf(filtersBean.getDistance()));
            pstmt.setString(parameterIndex++, String.valueOf(filtersBean.getMaxPrice()));

            // Set filter values if true
            if (Boolean.parseBoolean(String.valueOf(filtersBean.getPrivateBathroom()))) {
                pstmt.setString(parameterIndex++, "true");
            }

            if (Boolean.parseBoolean(String.valueOf(filtersBean.getBalcony()))) {
                pstmt.setString(parameterIndex++, "true");
            }

            if (Boolean.parseBoolean(String.valueOf(filtersBean.getConditioner()))) {
                pstmt.setString(parameterIndex++, "true");
            }

            if (Boolean.parseBoolean(String.valueOf(filtersBean.getTvConnection()))) {
                pstmt.setString(parameterIndex, "true");
            }

            return pstmt.executeQuery();
        }
    }


    public static String buildRoomFiltersQuery(FiltersBean filtersBean) {
        String sql = """
        SELECT S.idStanza, S.tipologia, S.superficie, S.bagnoPrivato, S.balcone, S.condizionatore, S.tv, S.descrizione, S.prezzo, S.meseDisponibilita, I.*, U.nome, (6371 * 2 * ASIN(SQRT(POWER(SIN(RADIANS(I.latitudine - U.latitudine) / 2), 2) + COS(RADIANS(U.latitudine)) * COS(RADIANS(I.latitudine)) * POWER(SIN(RADIANS(I.longitudine - U.longitudine) / 2), 2))))
        FROM hivecampus.universita U
        JOIN hivecampus.immobile I ON U.citta = I.citta
        JOIN hivecampus.stanza S ON I.idImmobile = S.idImmobile
        WHERE U.nome = ?
        AND (6371 * 2 * ASIN(SQRT(POWER(SIN(RADIANS(I.latitudine - U.latitudine) / 2), 2) + COS(RADIANS(U.latitudine)) * COS(RADIANS(I.latitudine)) * POWER(SIN(RADIANS(I.longitudine - U.longitudine) / 2), 2)))) <= ?
        AND S.prezzo <= ?
        AND S.disponibilita = true
        """;

        // Add dynamic filters if true
        if (Boolean.parseBoolean(String.valueOf(filtersBean.getPrivateBathroom()))) {
            sql += " AND S.bagnoPrivato = ?";
        }

        if (Boolean.parseBoolean(String.valueOf(filtersBean.getBalcony()))) {
            sql += " AND S.balcone = ?";
        }

        if (Boolean.parseBoolean(String.valueOf(filtersBean.getConditioner()))) {
            sql += " AND S.condizionatore = ?";
        }

        if (Boolean.parseBoolean(String.valueOf(filtersBean.getTvConnection()))) {
            sql += " AND S.tv = ?";
        }

        return sql;
    }

}
