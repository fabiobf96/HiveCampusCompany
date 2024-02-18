package it.hivecampuscompany.hivecampus.logic.dao.queries;



import it.hivecampuscompany.hivecampus.logic.bean.CredentialsBean;
import it.hivecampuscompany.hivecampus.logic.bean.FiltersBean;

import java.sql.*;

public class SimpleQueries {
    public static ResultSet checkAccount (Statement stmt, CredentialsBean credentialsBean) throws SQLException {
        String email = credentialsBean.getEmail();
        String password = credentialsBean.getPassword();
        ResultSet res;

        String sql = "SELECT\n" +
                "    A.username,\n" +
                "    A.password,\n" +
                "    A.ruolo,\n" +
                "    COALESCE(P.nome, Af.nome) AS nome,\n" +
                "    COALESCE(P.cognome, Af.cognome) AS cognome,\n" +
                "    COALESCE(P.telefono, Af.telefono) AS telefono\n" +
                "    \n" +
                "FROM hivecampus.account A\n" +
                "LEFT JOIN hivecampus.proprietario P ON A.username = P.username AND A.ruolo = 'proprietario'\n" +
                "LEFT JOIN hivecampus.affittuario Af ON A.username = Af.username AND A.ruolo = 'affittuario'\n" +
                "WHERE A.username = '" + email + "'  AND A.password = '" + password + "';";
        res = stmt.executeQuery(sql);
        return res;
    }

    public static ResultSet selectAccountInformation(Statement stmt, String email) throws SQLException {
        ResultSet res;
        String sql = "SELECT\n" +
                "    A.username,\n" +
                "    A.ruolo,\n" +
                "    COALESCE(P.nome, Af.nome) AS nome,\n" +
                "    COALESCE(P.cognome, Af.cognome) AS cognome,\n" +
                "    COALESCE(P.telefono, Af.telefono) AS telefono\n" +
                "    \n" +
                "FROM hivecampus.account A\n" +
                "LEFT JOIN hivecampus.proprietario P ON A.username = P.username AND A.ruolo = 'proprietario'\n" +
                "LEFT JOIN hivecampus.affittuario Af ON A.username = Af.username AND A.ruolo = 'affittuario'\n" +
                "WHERE A.username = '" + email + "';";
        res = stmt.executeQuery(sql);
        return res;
    }

    public static ResultSet selectOwnerByIdHome (Statement stmt, int idHome) throws SQLException {
        ResultSet res;
        String sql = "SELECT\n" +
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
        ResultSet res;

        String sql = """
            SELECT S.idStanza, S.tipologia, S.superficie, S.bagnoPrivato, S.balcone, S.condizionatore, S.tv, S.descrizione, S.prezzo, S.meseDisponibilita, I.*, U.nome, (6371 * 2 * ASIN(SQRT(POWER(SIN(RADIANS(I.latitudine - U.latitudine) / 2), 2) + COS(RADIANS(U.latitudine)) * COS(RADIANS(I.latitudine)) * POWER(SIN(RADIANS(I.longitudine - U.longitudine) / 2), 2))))
            FROM hivecampus.universita U
            JOIN hivecampus.immobile I ON U.citta = I.citta
            JOIN hivecampus.stanza S ON I.idImmobile = S.idImmobile
            WHERE U.nome = ?
            AND (6371 * 2 * ASIN(SQRT(POWER(SIN(RADIANS(I.latitudine - U.latitudine) / 2), 2) + COS(RADIANS(U.latitudine)) * COS(RADIANS(I.latitudine)) * POWER(SIN(RADIANS(I.longitudine - U.longitudine) / 2), 2)))) <= ?
            AND S.prezzo <= ?
            AND S.disponibilita = true;
            """;

        // Add dynamic filters if true
        if (Boolean.parseBoolean(filtersBean.getPrivateBathroom())) {
            sql += "AND S.bagnoPrivato = 'true'\n";
        }

        if (Boolean.parseBoolean(filtersBean.getBalcony())) {
            sql += "AND S.balcone = 'true'\n";
        }

        if (Boolean.parseBoolean(filtersBean.getConditioner())) {
            sql += "AND S.condizionatore = 'true'\n";
        }

        if (Boolean.parseBoolean(filtersBean.getTvConnection())) {
            sql += "AND S.tv = 'true'\n";
        }

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, filtersBean.getUniversity());
        pstmt.setString(2, filtersBean.getDistance());
        pstmt.setString(3, filtersBean.getMaxPrice());

        res = pstmt.executeQuery();

        return res;
    }

}
