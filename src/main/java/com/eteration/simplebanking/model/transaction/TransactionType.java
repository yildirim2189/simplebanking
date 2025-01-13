package com.eteration.simplebanking.model.transaction;

public enum TransactionType {
    DEPOSIT("DepositTransaction"),
    WITHDRAW("WithdrawalTransaction"),
    PHONE_BILL_PAYMENT("PhoneBillPaymentTransaction");

    private String name;

    TransactionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static TransactionType fromString(String value) {
        for (TransactionType type : TransactionType.values()) {
            if (type.name.equalsIgnoreCase(value)) {
                return type;
            }
        }
        return null;
    }
}
