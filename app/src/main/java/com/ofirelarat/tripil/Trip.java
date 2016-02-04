package com.ofirelarat.tripil;

import java.util.List;

public class Trip {
    String id;
    String username;
    String arrivalDate;
    String returnDate;
    int days;
    String area;
    String hotels;
    String attractions;
    String travelGuide;
    int stars;
    String Description;

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

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getHotels() {
        return hotels;
    }

    public void setHotels(String hotels) {
        this.hotels = hotels;
    }

    public String getAttractions() {
        return attractions;
    }

    public void setAttractions(String attractions) {
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
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
