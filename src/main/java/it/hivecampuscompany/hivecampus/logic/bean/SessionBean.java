package it.hivecampuscompany.hivecampus.logic.bean;

import it.hivecampuscompany.hivecampus.logic.model.Session;

public class SessionBean {
    private final int id;
    private final String typeAccount;

    public SessionBean(){  // Default constructor
        this.id = 0;
        this.typeAccount = null;
    }
    public SessionBean(Session session){
        this.id = session.getId();
        this.typeAccount = session.getAccount().getTypeAccount();
    }

    public int getId() {
        return id;
    }

    public String getTypeAccount() {
        return typeAccount;
    }
}
