package it.hivecampuscompany.hivecampus.logic.bean;

import it.hivecampuscompany.hivecampus.logic.model.Room;

public class RoomBean extends PreviewRoomBean  {
    private Integer roomSurface;
    private Boolean privateBathroom;
    private Boolean balcony;
    private Boolean conditioner;
    private Boolean tvConnection;
    private String roomDescription;
    private String availability;
    private String houseType;
    private Integer houseSurface;
    private Integer numRooms;
    private Integer numBathrooms;
    private Integer floor;
    private Boolean elevator;
    private String heating;
    private String houseDescription;

    private String ownerEmail;
    private String university;
    private Float distance;

    public RoomBean(){
        //Default constructor
    }

    public RoomBean(Room room) {
        super(room);
        this.roomSurface = room.getRoomSurface();
        this.privateBathroom = room.getPrivateBathroom();
        this.balcony = room.getBalcony();
        this.conditioner = room.getConditioner();
        this.tvConnection = room.getTvConnection();
        this.roomDescription = room.getRoomDescription();
        this.availability = room.getAvailability();
        this.houseType = room.getHouseType();
        this.houseSurface = room.getHouseSurface();
        this.numRooms = room.getNumRooms();
        this.numBathrooms = room.getNumBathrooms();
        this.floor = room.getFloor();
        this.elevator = room.getElevator();
        this.heating = room.getHeating();
        this.houseDescription = room.getHouseDescription();
        this.ownerEmail = room.getOwnerAccount().getEmail();
        this.university = room.getUniversity();
        this.distance = room.getDistance();
    }

    public Integer getRoomSurface() {
        return roomSurface;
    }

    public Boolean getPrivateBathroom() {
        return privateBathroom;
    }

    public Boolean getBalcony() {
        return balcony;
    }

    public Boolean getConditioner() {
        return conditioner;
    }

    public Boolean getTvConnection() {
        return tvConnection;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public String getAvailability() {
        return availability;
    }

    public String getHouseType() {
        return houseType;
    }

    public Integer getHouseSurface() {
        return houseSurface;
    }

    public Integer getNumRooms() {
        return numRooms;
    }

    public Integer getNumBathrooms() {
        return numBathrooms;
    }

    public Integer getFloor() {
        return floor;
    }

    public Boolean getElevator() {
        return elevator;
    }

    public String getHeating() {
        return heating;
    }

    public String getHouseDescription() {
        return houseDescription;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public String getUniversity() {
        return university;
    }

    public Float getDistance() {
        return distance;
    }

}
