package com.utopia.designmyexperience_api.dto;

public class ValidateBookingRequest {

    private String transactionHash;
    private int offeringId;
    private int clientId;
    private int attendeeCount;
    private double price;
    private String bookingDateTime;

    public ValidateBookingRequest() {
        // Constructeur vide pour la désérialisation JSON
    }

    public ValidateBookingRequest(String transactionHash, int offeringId, int clientId, int attendeeCount, double price, String bookingDateTime) {
        this.transactionHash = transactionHash;
        this.offeringId = offeringId;
        this.clientId = clientId;
        this.attendeeCount = attendeeCount;
        this.price = price;
        this.bookingDateTime = bookingDateTime;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public int getOfferingId() {
        return offeringId;
    }

    public void setOfferingId(int offeringId) {
        this.offeringId = offeringId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getAttendeeCount() {
        return attendeeCount;
    }

    public void setAttendeeCount(int attendeeCount) {
        this.attendeeCount = attendeeCount;
    }

    public String getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(String bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public double getPrice(){
        return this.price;
    }
}