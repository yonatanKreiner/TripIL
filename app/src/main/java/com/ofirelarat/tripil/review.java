package com.ofirelarat.tripil;

/**
 * Created by NofarE on 2/8/2016.
 */
public class review {
    private int tripID;
    private String username;
    private  String message;

    public review(int tripID, String username, String message) {
        this.tripID = tripID;
        this.username = username;
        this.message = message;
    }
    public int getTripID() {return tripID;}

    public void setTripID(int tripID) {this.tripID = tripID;}

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getMessage() {return message;}

    public void setMessage(String message) {this.message = message;}


}
