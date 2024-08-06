package com.example.desafioCaju.model;

import jakarta.persistence.*;


@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accountId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String mcc;

    @Column(nullable = false)
    private String merchant;

    // Construtor, getters e setters

    public Transaction() {}

    public Transaction(String accountId, Double amount, String mcc, String merchant) {
        this.accountId = accountId;
        this.amount = amount;
        this.mcc = mcc;
        this.merchant = merchant;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }
}
