package com.eteration.simplebanking.model.transaction;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PHONE_BILL_PAYMENT")
public class PhoneBillPaymentTransaction extends PaymentTransaction {

    private String phoneNumber;

    public PhoneBillPaymentTransaction() {
        super();
    }

    public PhoneBillPaymentTransaction(double amount, String phoneNumber, String payee) {
        super(amount, payee);
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
