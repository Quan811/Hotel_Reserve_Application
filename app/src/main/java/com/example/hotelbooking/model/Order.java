package com.example.hotelbooking.model;


import java.io.Serializable;

public class Order implements Serializable {

    Room room;
    Accommodation accommodation;
    String clientName;
    String clientPhoneNumber;
    String paymentMethod;
    String timeOrder;
    String orderStatus;
    String totalPayment;
    String numberOfRoom;

    public Order(Room room, Accommodation accommodation, String clientName, String clientPhoneNumber, String paymentMethod, String timeOrder, String orderStatus, String totalPayment, String numberOfRoom) {
        this.room = room;
        this.accommodation = accommodation;
        this.clientName = clientName;
        this.clientPhoneNumber = clientPhoneNumber;
        this.paymentMethod = paymentMethod;
        this.timeOrder = timeOrder;
        this.orderStatus = orderStatus;
        this.totalPayment = totalPayment;
        this.numberOfRoom = numberOfRoom;
    }

    public Order() {
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

    public String getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(String totalPayment) {
        this.totalPayment = totalPayment;
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
