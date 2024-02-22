package it.hivecampuscompany.hivecampus.logic.control;



import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import it.hivecampuscompany.hivecampus.logic.model.Account;
import it.hivecampuscompany.hivecampus.logic.model.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SessionManager {
    private static SessionManager instance;
    private final List<Session> sessions;

    private SessionManager(){
        sessions = new ArrayList<>();
    }

    public static SessionManager getInstance(){
        if (instance == null){
            instance = new SessionManager();
        }
        return instance;
    }

    public Session createSession(Account account){
        for (Session session: sessions) {
            if(session.getAccount().equals(account)){
                return session;
            }
        }
        Session session = new Session(account.hashCode(), account);
        sessions.add(session);
        return session;
    }

    public boolean isValid(SessionBean sessionBean){
        for (Session session: sessions){
            if(session.getId() == sessionBean.getId()) {
                return true;
            }
        }
        return false;
    }

    public void closeSession(SessionBean sessionBean){
        sessions.removeIf(session -> session.getId() == sessionBean.getId());
        try {
            ConnectionManager.closeConnection();
        } catch (SQLException e) {
            System.exit(2);
        }
    }

    public Account getAccount(SessionBean sessionBean) throws IllegalArgumentException{
        for (Session session: sessions){
            if(session.getId() == sessionBean.getId()) {
                return  session.getAccount();
            }
        }
        throw new IllegalArgumentException("Invalid Session");
    }

}
