package com.example.hotelbooking.model;

import java.io.Serializable;

public class Client implements Serializable {
    Account account;
    String fullName;
    String phoneNumber;
    Order order;

    public Client() {
    }

    public Client(Account account, String fullName, String phoneNumber, Order order) {
        this.account = account;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.order = order;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
