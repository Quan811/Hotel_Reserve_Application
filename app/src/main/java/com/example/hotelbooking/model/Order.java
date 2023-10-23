package com.example.hotelbooking.model;

import java.util.List;

public class Order {

    Client client;
    List<Accommodation> accommodationOrdered;
    String paymentMethod;
    String timeOrder;
    String totalPayment;

    public Order(Client client, List<Accommodation> accommodationOrdered, String paymentMethod, String timeOrder, String totalPayment) {
        this.client = client;
        this.accommodationOrdered = accommodationOrdered;
        this.paymentMethod = paymentMethod;
        this.timeOrder = timeOrder;
        this.totalPayment = totalPayment;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(String totalPayment) {
        this.totalPayment = totalPayment;
    }

    public List<Accommodation> getHotelOrdered() {
        return accommodationOrdered;
    }

    public void setHotelOrdered(List<Accommodation> accommodationOrdered) {
        this.accommodationOrdered = accommodationOrdered;
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
}
