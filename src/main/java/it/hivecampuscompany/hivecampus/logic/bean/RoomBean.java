package it.hivecampuscompany.hivecampus.logic.bean;

import it.hivecampuscompany.hivecampus.logic.model.Room;

public class RoomBean extends PreviewRoomBean {
    private Integer roomSurface;
    private Boolean privateBathroom;
    private Boolean balcony;
    private Boolean conditioner;
    private Boolean tvConnection;
    private String roomDescription;
    private String availability;

    private Integer idHome;
    private Float houseLatitude;
    private Float houseLongitude;
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
        this.idHome = room.getIdHome();
        this.houseLatitude = room.getHouseLatitude();
        this.houseLongitude = room.getHouseLongitude();
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

    public void setRoomSurface(Integer roomSurface) {
        this.roomSurface = roomSurface;
    }

    public Boolean getPrivateBathroom() {
        return privateBathroom;
    }

    public void setPrivateBathroom(Boolean privateBathroom) {
        this.privateBathroom = privateBathroom;
    }

    public Boolean getBalcony() {
        return balcony;
    }

    public void setBalcony(Boolean balcony) {
        this.balcony = balcony;
    }

    public Boolean getConditioner() {
        return conditioner;
    }

    public void setConditioner(Boolean conditioner) {
        this.conditioner = conditioner;
    }

    public Boolean getTvConnection() {
        return tvConnection;
    }

    public void setTvConnection(Boolean tvConnection) {
        this.tvConnection = tvConnection;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }


    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public Integer getIdHome() {
        return idHome;
    }

    public void setIdHome(Integer idHome) {
        this.idHome = idHome;
    }


    public Float getHouseLatitude() {
        return houseLatitude;
    }

    public void setHouseLatitude(Float houseLatitude) {
        this.houseLatitude = houseLatitude;
    }

    public Float getHouseLongitude() {
        return houseLongitude;
    }

    public void setHouseLongitude(Float houseLongitude) {
        this.houseLongitude = houseLongitude;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public Integer getHouseSurface() {
        return houseSurface;
    }

    public void setHouseSurface(Integer houseSurface) {
        this.houseSurface = houseSurface;
    }

    public Integer getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(Integer numRooms) {
        this.numRooms = numRooms;
    }

    public Integer getNumBathrooms() {
        return numBathrooms;
    }

    public void setNumBathrooms(Integer numBathrooms) {
        this.numBathrooms = numBathrooms;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Boolean getElevator() {
        return elevator;
    }

    public void setElevator(Boolean elevator) {
        this.elevator = elevator;
    }

    public String getHeating() {
        return heating;
    }

    public void setHeating(String heating) {
        this.heating = heating;
    }

    public String getHouseDescription() {
        return houseDescription;
    }

    public void setHouseDescription(String houseDescription) {
        this.houseDescription = houseDescription;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }
    /*
    @Override
    public String toString() {
        return "RoomBean{" +
                "idRoom='" + idRoom + '\'' +
                ", idHome='" + idHome + '\'' +
                ", roomType='" + roomType + '\'' +
                ", roomSurface='" + roomSurface + '\'' +
                ", privateBathroom='" + privateBathroom + '\'' +
                ", balcony='" + balcony + '\'' +
                ", conditioner='" + conditioner + '\'' +
                ", tvConnection='" + tvConnection + '\'' +
                ", price='" + price + '\'' +
                ", availability='" + availability + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", city='" + city + '\'' +
                ", university='" + university + '\'' +
                ", distance='" + distance + '\'' +
                ", roomDescription='" + roomDescription + '\'' +
                ", houseLatitude='" + houseLatitude + '\'' +
                ", houseLongitude='" + houseLongitude + '\'' +
                ", houseType='" + houseType + '\'' +
                ", houseSurface='" + houseSurface + '\'' +
                ", numRooms='" + numRooms + '\'' +
                ", numBathrooms='" + numBathrooms + '\'' +
                ", floor='" + floor + '\'' +
                ", elevator='" + elevator + '\'' +
                ", heating='" + heating + '\'' +
                ", houseDescription='" + houseDescription + '\'' +
                ", ownerEmail='" + ownerEmail + '\'' +
                '}';
    }

     */

}
