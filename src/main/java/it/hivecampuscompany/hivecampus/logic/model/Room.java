package it.hivecampuscompany.hivecampus.logic.model;

import java.io.Serializable;
import java.util.List;

public class Room implements Serializable {

    private int idRoom;
    private String roomType;
    private int roomSurface;
    private Boolean privateBathroom;
    private Boolean balcony;
    private Boolean conditioner;
    private Boolean tvConnection;
    private String roomDescription;
    private float price;
    private String availability;

    private int idHome;
    private String street;
    private int streetNumber;
    private String city;
    private float houseLatitude;
    private float houseLongitude;
    private String houseType;
    private int houseSurface;
    private int numRooms;
    private int numBathrooms;
    private int floor;
    private boolean elevator;
    private String heating;
    private String houseDescription;

    private String ownerEmail;
    private String university;
    private float distance;

    private Account ownerAccount;

    private List<LeaseRequest> leaseRequests;

    public Room(){
        //Costruttore di default
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getRoomSurface() {
        return roomSurface;
    }

    public void setRoomSurface(int roomSurface) {
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

    public void setBalcony(boolean balcony) {
        this.balcony = balcony;
    }

    public Boolean getConditioner() {
        return conditioner;
    }

    public void setConditioner(boolean conditioner) {
        this.conditioner = conditioner;
    }

    public Boolean getTvConnection() {
        return tvConnection;
    }

    public void setTvConnection(boolean tvConnection) {
        this.tvConnection = tvConnection;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public int getIdHome() {
        return idHome;
    }

    public void setIdHome(int idHome) {
        this.idHome = idHome;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public float getHouseLatitude() {
        return houseLatitude;
    }

    public void setHouseLatitude(float houseLatitude) {
        this.houseLatitude = houseLatitude;
    }

    public float getHouseLongitude() {
        return houseLongitude;
    }

    public void setHouseLongitude(float houseLongitude) {
        this.houseLongitude = houseLongitude;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public int getHouseSurface() {
        return houseSurface;
    }

    public void setHouseSurface(int houseSurface) {
        this.houseSurface = houseSurface;
    }

    public int getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(int numRooms) {
        this.numRooms = numRooms;
    }

    public int getNumBathrooms() {
        return numBathrooms;
    }

    public void setNumBathrooms(int numBathrooms) {
        this.numBathrooms = numBathrooms;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public boolean getElevator() {
        return elevator;
    }

    public void setElevator(boolean elevator) {
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

    public String getUniversity() { return university; }

    public void setUniversity(String university) { this.university = university; }

    public float getDistance() { return distance; }

    public void setDistance(float distance) { this.distance = distance; }

    public Account getOwnerAccount() { return ownerAccount; }

    public void setOwnerAccount(Account ownerAccount) { this.ownerAccount = ownerAccount; }

    public List<LeaseRequest> getLeaseRequests() {
        return leaseRequests;
    }

    public void setLeaseRequests(List<LeaseRequest> leaseRequests) {
        this.leaseRequests = leaseRequests;
    }
}