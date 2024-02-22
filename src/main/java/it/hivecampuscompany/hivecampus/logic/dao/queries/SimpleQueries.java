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

    public static String buildRoomFiltersQuery(FiltersBean filtersBean) {
        String sql = """
        SELECT S.idStanza, S.tipologia AS tipologiaStanza, S.superficie AS superficieStanza, S.bagnoPrivato, S.balcone, S.condizionatore, S.tv, S.descrizione AS descrizioneStanza, S.disponibilita, S.meseDisponibilita, S.prezzo,
        I.*, U.nome AS universita, (6371 * 2 * ASIN(SQRT(POWER(SIN(RADIANS(I.latitudine - U.latitudine) / 2), 2) + COS(RADIANS(U.latitudine)) * COS(RADIANS(I.latitudine)) * POWER(SIN(RADIANS(I.longitudine - U.longitudine) / 2), 2)))) AS distanza
        FROM hivecampus.universita U
        JOIN hivecampus.immobile I ON U.citta = I.citta
        JOIN hivecampus.stanza S ON I.idImmobile = S.immobile
        WHERE U.nome = ?
        AND (6371 * 2 * ASIN(SQRT(POWER(SIN(RADIANS(I.latitudine - U.latitudine) / 2), 2) + COS(RADIANS(U.latitudine)) * COS(RADIANS(I.latitudine)) * POWER(SIN(RADIANS(I.longitudine - U.longitudine) / 2), 2)))) <= ?
        AND S.prezzo <= ?
        AND S.disponibilita = true
        """;

        // Add dynamic filters if true
        if (Boolean.parseBoolean(String.valueOf(filtersBean.getPrivateBathroom()))) {
            sql += " AND S.bagnoPrivato = true";
        }

        if (Boolean.parseBoolean(String.valueOf(filtersBean.getBalcony()))) {
            sql += " AND S.balcone = true";
        }

        if (Boolean.parseBoolean(String.valueOf(filtersBean.getConditioner()))) {
            sql += " AND S.condizionatore = true";
        }

        if (Boolean.parseBoolean(String.valueOf(filtersBean.getTvConnection()))) {
            sql += " AND S.tv = true";
        }

        return sql;
    }

}
