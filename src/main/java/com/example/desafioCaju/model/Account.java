package com.example.desafioCaju.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountId;
    private double foodBalance;
    private double mealBalance;
    private double cashBalance;

    public double getFoodBalance() {
        return 0;
    }

    public void setFoodBalance(double v) {
    }

    public double getMealBalance() {
        return 0;
    }

    public void setMealBalance(double v) {
    }

    public double getCashBalance() {
        return 0;
    }

    public void setCashBalance(double v) {
    }

    // Getters and setters
}
