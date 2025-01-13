package com.eteration.simplebanking.model;

import com.eteration.simplebanking.constants.MessageConstants;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.transaction.Transaction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String owner;
    private String accountNumber;
    private double balance;
    private Date createDate;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();


    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transaction.setAccount(this);
    }

    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction);
        transaction.setAccount(null);
    }

    public BankAccount() {
        this.createDate = new Date();
    }

    public BankAccount(String owner, String accountNumber) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
        this.createDate = new Date();
    }

    public BankAccount(String owner, String accountNumber, double balance) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactions = new ArrayList<>();
        this.createDate = new Date();
    }

    public BankAccount(String owner, String accountNumber, double balance, List<Transaction> transactions) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactions = transactions;
        this.createDate = new Date();
    }

    public BankAccount (String owner, String accountNumber, double balance, List<Transaction> transactions, Date createDate) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactions = transactions;
        this.createDate = createDate;
    }

    // Builder pattern
    public static class Builder {
        private String owner;
        private String accountNumber;
        private double balance;
        private List<Transaction> transactions;

        public Builder owner(String owner) {
            this.owner = owner;
            return this;
        }

        public Builder accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public Builder balance(double balance) {
            this.balance = balance;
            return this;
        }

        public Builder transactions(List<Transaction> transactions) {
            this.transactions = transactions;
            return this;
        }

        public BankAccount build() {
            return new BankAccount(owner, accountNumber, balance, transactions);
        }
    }


    public void deposit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero");
        }
        balance += amount;
    }

    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount < 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero");
        }
        if (balance < amount) {
            throw new InsufficientBalanceException(MessageConstants.INSUFFICIENT_BALANCE);
        }
        balance -= amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(id, that.id) && Objects.equals(accountNumber, that.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber);
    }
}
