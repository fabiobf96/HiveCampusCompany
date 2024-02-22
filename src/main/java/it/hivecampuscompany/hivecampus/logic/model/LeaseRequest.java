package it.hivecampuscompany.hivecampus.logic.model;

import it.hivecampuscompany.hivecampus.logic.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.logic.facade.DAOFactoryFacade;

public class LeaseRequest implements Observer{ // implements Observer
    private Account account;
    private Room room;
    private int id;
    private String startPermanence;
    private String typePermanence;

    private String statusRequest; //

    public LeaseRequest() {
    }

    public LeaseRequest(int requestId, Account account, String startPermanence, String duration, String leaseStatus) {
        this.id = requestId;
        this.account = account;
        this.startPermanence = startPermanence;
        this.typePermanence = duration;
        this.statusRequest = leaseStatus;
    }

    public LeaseRequest(LeaseRequestBean leaseRequestBean) {
        startPermanence = leaseRequestBean.getStartPermanence();
        typePermanence = leaseRequestBean.getTypePermanence();
        statusRequest = leaseRequestBean.getStatusRequest();
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

    public String getStartPermanence() {
        return startPermanence;
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

    public void setStartPermanence(String startPermanence) {
        this.startPermanence = startPermanence;
    }

    public void setTypePermanence(String typePermanence) {
        this.typePermanence = typePermanence;
    }

    public String getStatusRequest() {
        return statusRequest;
    }

    public void setStatusRequest(String statusRequest) {
        this.statusRequest = statusRequest;
    }

    @Override
    public void update() {
        statusRequest = "affittata";
        DAOFactoryFacade daoFactoryFacade = DAOFactoryFacade.getInstance();
        daoFactoryFacade.getLeaseRequestDAO().updateLeaseRequest(this);
    }
}