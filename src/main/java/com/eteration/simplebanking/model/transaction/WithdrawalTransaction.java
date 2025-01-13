package com.eteration.simplebanking.model.transaction;

import com.eteration.simplebanking.model.BankAccount;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("WITHDRAW")
public class WithdrawalTransaction extends Transaction {

        public WithdrawalTransaction() {
        }

        public WithdrawalTransaction(double amount) {
            super(amount);
        }

        @Override
        public void apply(BankAccount account) {
            account.withdraw(this.getAmount());
            account.getTransactions().add(this);
        }
}


