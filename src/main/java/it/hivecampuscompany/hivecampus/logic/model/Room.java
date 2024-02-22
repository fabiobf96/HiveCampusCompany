package it.hivecampuscompany.hivecampus.logic.model;

import it.hivecampuscompany.hivecampus.logic.bean.RoomBean;

import java.io.Serializable;
import java.util.List;

public class Room implements Serializable {

    private Integer idRoom;
    private String roomType;
    private Integer roomSurface;
    private Boolean privateBathroom;
    private Boolean balcony;
    private Boolean conditioner;
    private Boolean tvConnection;
    private String roomDescription;
    private Integer price;
    private String availability;
    private Boolean isAvailable;

    private Integer idHome;
    private String street;
    private Integer streetNumber;
    private String city;
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

    private String university;
    private Float distance;

    private Account ownerAccount;

    private List<LeaseRequest> leaseRequests;

    public Room(){
        //Costruttore di default
    }


    public Integer getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Integer idRoom) {
        this.idRoom = idRoom;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public Boolean getAvailable() { return isAvailable; }

    public void setAvailable(Boolean available) { isAvailable = available; }

    public Integer getIdHome() {
        return idHome;
    }

    public void setIdHome(Integer idHome) {
        this.idHome = idHome;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public Account getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(Account ownerAccount) {
        this.ownerAccount = ownerAccount;
    }

    public List<LeaseRequest> getLeaseRequests() {
        return leaseRequests;
    }

    public void setLeaseRequests(List<LeaseRequest> leaseRequests) {
        this.leaseRequests = leaseRequests;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}