package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.Month;

public class ExpenseTracker {
    static void main(String[] args) {
        getArgs(args);
    }

    private List<Expense> expenses;
    private static File file = new File("expenses.json");
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public ExpenseTracker(){
        expenses = new ArrayList<>();
    }

    public static void getArgs(String[] args){
        if (args.length == 0){
            System.out.println("No commands provided");
            return;
        }

        ExpenseTracker expenseTracker = new ExpenseTracker();
        expenseTracker.loadFromFile();
        String description = "";
        double amount = 0.0;
        try {
            switch (args[0].toLowerCase()){
                case "add":
                    if (args.length < 2){
                        System.out.println("Incomplete command");
                        return;
                    }
                    if (args[1].toLowerCase().equals("--description")){
                        description = args[2];
                        if (args[3].toLowerCase().equals("--amount")){
                            amount = Double.parseDouble(args[4]);
                            if (expenseTracker.addExpense(amount, description))
                                System.out.println("Expense added");
                        }
                        else
                            System.out.println("Invalid command");
                    }else
                        System.out.println("Invalid command");
                    break;
                case "list":
                    expenseTracker.listExpenses();
                    break;
                case "summary":
                    if (args.length == 1)
                        expenseTracker.expensesSummary();
                    else if (args[1].equals("--month")){
                        int month = Integer.parseInt(args[2]);
                        expenseTracker.summaryByMonth(month);
                    }else
                        System.out.println("Invalid command");
                    break;
                case "delete":
                    int id = 0;
                    if (args.length < 2){
                        System.out.println("Incomplete command");
                        return;
                    }
                    if (args[1].equals("--equals")){
                        id  = Integer.parseInt(args[2]);
                        if (expenseTracker.deleteExpense(id))
                            System.out.println("Expense deleted");
                    }else
                        System.out.println("Invalid command");
                    break;
            }
        }catch (IllegalArgumentException ex){
            System.out.println(ex);
        }


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

