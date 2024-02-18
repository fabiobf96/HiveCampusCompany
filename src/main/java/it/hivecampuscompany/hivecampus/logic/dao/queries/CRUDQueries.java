package it.hivecampuscompany.hivecampus.logic.dao.queries;



import it.hivecampuscompany.hivecampus.logic.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CRUDQueries {

    public static void insertAccount(Connection conn, Account account) {
        String sql = "INSERT INTO hivecampus.account (username, password, ruolo) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, account.getEmail());
            pstmt.setString(2, account.getPassword());
            pstmt.setString(3, account.getTypeAccount());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Account insertion failed.");
        }
    }

    public static void  insertOwner(Connection conn, Account account) {
        String sql = "INSERT INTO hivecampus.proprietario (username, nome, cognome, telefono) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, account.getEmail());
            pstmt.setString(2, account.getName());
            pstmt.setString(3, account.getSurname());
            pstmt.setString(4, account.getPhoneNumber());

            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException("Tenant insertion failed.");
        }
    }

    public static void  insertTenant(Connection conn, Account account) {
        String sql = "INSERT INTO hivecampus.affittuario (username, nome, cognome, telefono) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, account.getEmail());
            pstmt.setString(2, account.getName());
            pstmt.setString(3, account.getSurname());
            pstmt.setString(4, account.getPhoneNumber());

            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException("Owner insertion failed.");
        }
    }
}
