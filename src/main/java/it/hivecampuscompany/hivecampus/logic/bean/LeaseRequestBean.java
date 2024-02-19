package it.hivecampuscompany.hivecampus.logic.bean;

import it.hivecampuscompany.hivecampus.logic.model.LeaseRequest;

public class LeaseRequestBean {
    private int id;
    private String typePermanence;
    private String startPermanence;
    private String getStartPermanence;
    private RoomBean roomBean;
    private AccountBean tenant;
    public LeaseRequestBean(LeaseRequest leaseRequest) {
        id = leaseRequest.getId();
        typePermanence = leaseRequest.getTypePermanence();
        startPermanence = leaseRequest.getDate().toString();
        tenant = new AccountBean(leaseRequest.getAccount());
    }
}
