package com.eteration.simplebanking.model.transaction;

public class PaymentTransaction extends WithdrawalTransaction {
    private String payee;

    public PaymentTransaction() {
    }


    public PaymentTransaction(double amount, String payee) {
        super(amount);
        this.payee = payee;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }
}