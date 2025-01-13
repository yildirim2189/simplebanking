package com.eteration.simplebanking.model.transaction;

import com.eteration.simplebanking.model.BankAccount;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DEPOSIT")
public class DepositTransaction extends Transaction {

    public DepositTransaction() {
    }

    public DepositTransaction(double amount) {
        super(amount);
    }

    @Override
    public void apply(BankAccount account) {
        account.deposit(this.getAmount());
        account.getTransactions().add(this);
    }
}
