package com.eteration.simplebanking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Objects;

public class TransactionDTO {

    @JsonIgnore
    private Long id;
    private Double amount;
    private Date date;

    @JsonProperty("type")
    private String transactionType;
    private String approvalCode;

    public TransactionDTO() {
    }

    public TransactionDTO(Long id, Double amount, Date date, String transactionType, String approvalCode) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.transactionType = transactionType;
        this.approvalCode = approvalCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }


    @Override
    public String toString() {
        return "TransactionDTO{" +
                "id=" + id +
                ", amount=" + amount +
                ", date=" + date +
                ", transactionType='" + transactionType + '\'' +
                ", approvalCode='" + approvalCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDTO that = (TransactionDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(date, that.date) &&
                Objects.equals(transactionType, that.transactionType) &&
                Objects.equals(approvalCode, that.approvalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, date, transactionType, approvalCode);
    }
}
