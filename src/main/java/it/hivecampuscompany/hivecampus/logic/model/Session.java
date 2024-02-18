package it.hivecampuscompany.hivecampus.logic.model;

public class Session {
    private final int id;
    private Account account;
    private final String type;
    public Session(int id, Account account){
        this.id = id;
        this.account = account;
        this.type = account.getTypeAccount();
    }
    public int getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }
    public String getType() {
        return type;
    }
    public void setAccount(Account account) {
        this.account = account;
    }


}
