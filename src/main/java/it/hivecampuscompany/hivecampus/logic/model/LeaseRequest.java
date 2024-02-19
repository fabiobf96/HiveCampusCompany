package it.hivecampuscompany.hivecampus.logic.model;

import it.hivecampuscompany.hivecampus.logic.bean.LeaseRequestBean;

public class LeaseRequest {
    private Account account;
    private Room room;
    private int id;
    private String date;
    private String typePermanence;

    public LeaseRequest() {
    }

    public LeaseRequest(int requestId, Account account, String startDate, String duration) {
        this.id = requestId;
        this.account = account;
        this.date = startDate;
        this.typePermanence = duration;
    }

    public LeaseRequest(LeaseRequestBean leaseRequestBean) {
        date = leaseRequestBean.getStartPermanence();
        typePermanence = leaseRequestBean.getTypePermanence();
    }

    public Account getAccount() {
        return account;
    }

    public Room getRoom() {
        return room;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTypePermanence() {
        return typePermanence;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTypePermanence(String typePermanence) {
        this.typePermanence = typePermanence;
    }
}