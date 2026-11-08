package org.example;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class Expense {
    //Data Fields
    private int id; //Expense id
    private double amount; //Expense amount
    private String description; //Expense description
    private String category; //Expense Category
    private LocalDate createdAt; //Expense creation date
    private static int maxId; //Generate expense id

    //Constructor
    public Expense(){}

    public Expense(double amount, String description) {
        this.id = ++maxId;
        this.amount = amount;
        this.description = description;
        this.createdAt = LocalDate.now();
    }

    public Expense(double amount, String description, String category){
        this(amount, description);
        this.category = category;
    }

    //Getters
    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public String getCategory() {
        return category;
    }

    //Setters
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static void setMaxId(int newMaxId){
        maxId = newMaxId;
    }

    //Methods
    @Override
    public String toString(){
        return getId() + "  " + getCreatedAt() + "  " + getDescription() + "  " + getAmount() + "  " + (getCategory() == null ? "Nil":getCategory());
    }
}

