package it.hivecampuscompany.hivecampus.logic.bean;

import it.hivecampuscompany.hivecampus.logic.model.LeaseRequest;

public class LeaseRequestBean {
    private String statusRequest;
    private int id;
    private String typePermanence;
    private String startPermanence;
    private RoomBean roomBean;
    private AccountBean tenant;

    public LeaseRequestBean () {
        //Default constructor
    }
    public LeaseRequestBean(LeaseRequest leaseRequest) {
        id = leaseRequest.getId();
        typePermanence = leaseRequest.getTypePermanence();
        startPermanence = leaseRequest.getStartPermanence();
        //roomBean = new RoomBean(leaseRequest.getRoom());
        tenant = new AccountBean(leaseRequest.getAccount());
        statusRequest = leaseRequest.getStatusRequest();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypePermanence() {
        return typePermanence;
    }

    public void setTypePermanence(String typePermanence) {
        this.typePermanence = typePermanence;
    }

    public String getStartPermanence() {
        return startPermanence;
    }

    public void setStartPermanence(String startPermanence) {
        this.startPermanence = startPermanence;
    }

    public RoomBean getRoomBean() {
        return roomBean;
    }

    public void setRoomBean(RoomBean roomBean) {
        this.roomBean = roomBean;
    }

    public AccountBean getTenant() {
        return tenant;
    }

    public void setTenant(AccountBean tenant) {
        this.tenant = tenant;
    }

    public String getStatusRequest() {
        return statusRequest;
    }
    public void setStatusRequest(String statusRequest) {
        this.statusRequest = statusRequest;
    }

}