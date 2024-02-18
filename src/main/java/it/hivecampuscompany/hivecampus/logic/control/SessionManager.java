package it.hivecampuscompany.hivecampus.logic.control;

import it.hivecampuscompany.hivecampus.logic.model.Account;
import it.hivecampuscompany.hivecampus.logic.model.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionManager {
    private static SessionManager instance = null;

    // Attributi della sessione
    private String username;
    private Map<String, Object> sessionData = new HashMap<>();

    private SessionManager() {
        // Costruttore privato per il pattern Singleton
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void login(String username) {
        this.username = username;
        // Qui puoi inizializzare altri dati necessari per la sessione
    }

    public void logout() {
        this.username = null;
        this.sessionData.clear();
        // Qui puoi gestire altre operazioni necessarie alla disconnessione
    }

    public boolean isLoggedIn() {
        return this.username != null;
    }

    public void setAttribute(String key, Object value) {
        sessionData.put(key, value);
    }

    public Object getAttribute(String key) {
        return sessionData.get(key);
    }

    public String getUsername() {
        return username;
    }
}
