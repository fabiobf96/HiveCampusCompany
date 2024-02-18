package it.hivecampuscompany.hivecampus.logic.bean;


import it.hivecampuscompany.hivecampus.logic.exception.EmptyFieldsException;

public class FiltersBean {
    private String university;
    private String distance;
    private String maxPrice;
    private String privateBathroom;
    private String balcony;
    private String conditioner;
    private String tvConnection;

    public FiltersBean(){
        // Default constructor
    }

    public FiltersBean(String university, String distance, String maxPrice, String privateBathroom, String balcony, String conditioner, String tvConnection) {
        this.university = university;
        this.distance = distance;
        this.maxPrice = maxPrice;
        this.privateBathroom = privateBathroom;
        this.balcony = balcony;
        this.conditioner = conditioner;
        this.tvConnection = tvConnection;
    }

    public String getUniversity() { return university; }

    public String getDistance() { return distance; }

    public String getMaxPrice() { return maxPrice; }

    public String getPrivateBathroom() { return privateBathroom; }

    public String getBalcony() { return balcony; }

    public String getConditioner() { return conditioner; }

    public String getTvConnection() { return tvConnection; }

    public String toString() {
        return "University: " + university + "\n" +
                "Distance: " + distance + "\n" +
                "Max Price: " + maxPrice + "\n" +
                "Private Bathroom: " + privateBathroom + "\n" +
                "Balcony: " + balcony + "\n" +
                "Conditioner: " + conditioner + "\n" +
                "TV Connection: " + tvConnection;
    }

    public void setUniversity(String university) throws EmptyFieldsException {
        if (university.isEmpty()) {
            throw new EmptyFieldsException("University field is empty. Please insert a valid university name.");
        }
        this.university =  university;
    }

    public void setMaxDistance(String distance) {
        this.distance = distance;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setFeatures(String privateBathroom, String balcony, String conditioner, String tvConnection) {
        this.privateBathroom = privateBathroom;
        this.balcony = balcony;
        this.conditioner = conditioner;
        this.tvConnection = tvConnection;
    }
}
