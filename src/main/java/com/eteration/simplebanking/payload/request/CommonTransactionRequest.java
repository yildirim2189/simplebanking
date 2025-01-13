package com.eteration.simplebanking.payload.request;

public class CommonTransactionRequest {
    private double amount;

    public CommonTransactionRequest() {
    }

    public CommonTransactionRequest(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}