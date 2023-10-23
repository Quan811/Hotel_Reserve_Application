package com.example.hotelbooking.model;

public class Client {
    String fullName;
    Account account;
    String phoneNumber;

    public Client(String fullName, Account account, String phoneNumber) {
        this.fullName = fullName;
        this.account = account;
        this.phoneNumber = phoneNumber;
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
