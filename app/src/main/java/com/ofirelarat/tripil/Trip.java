package com.ofirelarat.tripil;

import java.util.List;

public class Trip {
    String id;
    String username;
    String arrivalDate;
    String returnDate;
    String area;
    String[] hotels;
    String[] attractions;
    String travelGuide;
    int stars;
    String description;

    public Trip() {
        this.stars = 0;
    }

    public Trip(String id, String username, String arrivalDate, String returnDate) {
        this();
        this.id = id;
        this.username = username;
        this.arrivalDate = arrivalDate;
        this.returnDate = returnDate;
    }

    public Trip(String id, String username, String arrivalDate, String returnDate, String area) {
        this(id, username, arrivalDate, returnDate);
        this.area = area;
    }

    public Trip(String id, String username, String arrivalDate, String returnDate, String area, String hotels) {
        this(id, username, arrivalDate, returnDate, area);
        this.hotels = hotels.split(",");
    }

    public Trip(String id, String username, String arrivalDate, String returnDate, String area, String hotels, String attractions) {
        this(id, username, arrivalDate, returnDate,area, hotels);
        this.attractions = attractions.split(",");
    }

    public Trip(String id, String username, String arrivalDate, String returnDate, String area, String hotels, String attractions, String travelGuide) {
        this(id, username, arrivalDate, returnDate, area, hotels, attractions);
        this.travelGuide = travelGuide;
    }

    public Trip(String id, String username, String arrivalDate, String returnDate, String area, String hotels, String attractions, String travelGuide, String description) {
        this(id, username, arrivalDate, returnDate, area, hotels, attractions, travelGuide);
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String[] getHotels() {
        return hotels;
    }

    public void setHotels(String[] hotels) {
        this.hotels = hotels;
    }

    public String[] getAttractions() {
        return attractions;
    }

    public void setAttractions(String[] attractions) {
        this.attractions = attractions;
    }

    public String getTravelGuide() {
        return travelGuide;
    }

    public void setTravelGuide(String travelGuide) {
        this.travelGuide = travelGuide;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
