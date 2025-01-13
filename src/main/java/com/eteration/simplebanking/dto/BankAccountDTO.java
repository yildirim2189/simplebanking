package com.eteration.simplebanking.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BankAccountDTO {

    private String accountNumber;
    private String owner;
    private Double balance;
    private Date createDate;
    private List<TransactionDTO> transactions;

    public BankAccountDTO() {
        this.transactions = new ArrayList<>();
    }

    public BankAccountDTO(String accountNumber, String owner) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.transactions = new ArrayList<>();
    }

    public BankAccountDTO(String owner, String accountNumber, Double balance) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "BankAccountDTO{" +
                "accountNumber='" + accountNumber + '\'' +
                ", owner='" + owner + '\'' +
                ", balance=" + balance +
                ", transactions=" + transactions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankAccountDTO that = (BankAccountDTO) o;

        if (!accountNumber.equals(that.accountNumber)) return false;
        if (!owner.equals(that.owner)) return false;
        if (!balance.equals(that.balance)) return false;
        return transactions.equals(that.transactions);
    }

    @Override
    public int hashCode() {
        int result = accountNumber.hashCode();
        result = 31 * result + owner.hashCode();
        result = 31 * result + balance.hashCode();
        result = 31 * result + transactions.hashCode();
        return result;
    }
}
