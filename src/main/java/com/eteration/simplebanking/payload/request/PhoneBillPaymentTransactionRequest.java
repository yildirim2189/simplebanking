package com.eteration.simplebanking.payload.request;

public class PhoneBillPaymentTransactionRequest extends CommonTransactionRequest {
    private String phoneNumber;
    private String payee;

    public PhoneBillPaymentTransactionRequest() {
    }

    public PhoneBillPaymentTransactionRequest(double amount, String phoneNumber, String payee) {
        super(amount);
        this.phoneNumber = phoneNumber;
        this.payee = payee;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }
}
