package com.example.hotelbooking.model;


import java.io.Serializable;

public class Order implements Serializable {
    String orderID;
    Room room;
    Accommodation accommodation;
    String clientName;
    String clientPhoneNumber;
    String paymentMethod;
    String timeOrder;
    String orderStatus;
    String total;
    String numberOfRoom;

    public Order(String orderID, Room room, Accommodation accommodation, String clientName, String clientPhoneNumber, String paymentMethod, String timeOrder, String orderStatus, String totalPayment, String numberOfRoom) {
        this.orderID = orderID;
        this.room = room;
        this.accommodation = accommodation;
        this.clientName = clientName;
        this.clientPhoneNumber = clientPhoneNumber;
        this.paymentMethod = paymentMethod;
        this.timeOrder = timeOrder;
        this.orderStatus = orderStatus;
        this.total = totalPayment;
        this.numberOfRoom = numberOfRoom;
    }

    public Order() {
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getNumberOfRoom() {
        return numberOfRoom;
    }

    public void setNumberOfRoom(String numberOfRoom) {
        this.numberOfRoom = numberOfRoom;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(String timeOrder) {
        this.timeOrder = timeOrder;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }
}
