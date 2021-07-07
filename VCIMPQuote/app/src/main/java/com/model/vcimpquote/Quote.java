package com.model.vcimpquote;


public class Quote {

    private int id;
    private String date;
    private int vendor;
    private int customer;
    private int car;
    private double remainingAmount;
    private int paymentTerms;
    private double interestRate;

    public Quote() {
    }

    public Quote(int id, String date, int vendor, int customer, int car, double amount, int paymentTerms, double remainingAmount) {
        this.id = id;
        this.date = date;
        this.vendor = vendor;
        this.customer = customer;
        this.car = car;
        this.remainingAmount = remainingAmount;
        this.paymentTerms = paymentTerms;
        this.interestRate = interestRate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getVendor() {
        return vendor;
    }

    public void setVendor(int vendor) {
        this.vendor = vendor;
    }

    public int getCustomer() {
        return customer;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public int getCar() {
        return car;
    }

    public void setCar(int car) {
        this.car = car;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public int getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(int paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public String getDataQuery() {
        return id + ", \"" + date + "\"," + vendor + "," + customer + "," + car + "," + remainingAmount + "," + paymentTerms + "," + interestRate;
    }
}
