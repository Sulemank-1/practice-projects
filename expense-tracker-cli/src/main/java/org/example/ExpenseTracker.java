package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.util.*;
import java.time.Month;

public class ExpenseTracker {
    private List<Expense> expenses;
    private static File file = new File("expenses.json");
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public ExpenseTracker(){
        expenses = new ArrayList<>();
        loadFromFile();
    }

    //Add an expense
    public boolean addExpense(double amount, String description) {
        expenses.add(new Expense(amount, description));
        saveToFile();
        return true;
    }

    //Delete an expense
    public boolean deleteExpense(int id){
        boolean removed  = expenses.removeIf(e -> e.getId() == id);
        if (removed){
            saveToFile();
            return true;
        }
        System.out.println("Id not found");
        return false;
    }

    //View all expenses
    public void listExpenses(){
        if (!expenses.isEmpty()){
            System.out.println("ID  Date  Description  Amount");
            expenses.forEach(System.out::println);
        }else
            System.out.println("No expenses found");

    }

    //View summary of all expenses
    public void expensesSummary(){
        double sum = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
        System.out.println("Total expenses: " + sum);
    }

    //View expenses summary of a month
    public void summaryByMonth(int month){
        double sum = expenses.stream()
                .filter(e -> e.getCreatedAt().getMonthValue() == month)
                .mapToDouble(Expense::getAmount)
                .sum();

        String monthName = Month.of(month).name();
        System.out.println("Total expenses for " + monthName + ": " + sum);
    }

    //Save expenses to file
    public void saveToFile(){
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, expenses);
        }catch (IOException ex){
            System.out.println("Failed to save to file " + ex.getMessage());
        }
    }

    //Load expenses from file
    public void loadFromFile(){
        if (file.exists() && file.length() > 0){
            try {
                expenses = objectMapper.readValue(file, new TypeReference<>(){});
                int maxId = expenses.stream()
                        .mapToInt(Expense::getId)
                        .max()
                        .orElse(0);
                Expense.setMaxId(maxId);
            }catch (IOException ex){
                System.out.println(ex);
            }

        }
    }
}

