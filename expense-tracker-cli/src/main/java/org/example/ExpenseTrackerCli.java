package org.example;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
        name = "expense-tracker",
        mixinStandardHelpOptions = true,
        version = "1.0",
        description = "Tracks your daily expenses",
        subcommands = {
                ExpenseTrackerCli.AddCommand.class,
                ExpenseTrackerCli.ListCommand.class,
                ExpenseTrackerCli.SummaryCommand.class,
                ExpenseTrackerCli.DeleteCommand.class
        }
)
public class ExpenseTrackerCli implements Runnable {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new ExpenseTrackerCli()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        System.out.println("Please specify a command (add, list, summary, delete). Use --help for usage.");
    }

    @Command(name = "add", description = "Add a new expense")
    static class AddCommand implements Runnable {
        @Option(names = {"--description"}, required = true, description = "Expense description")
        private String description;

        @Option(names = {"--amount"}, required = true, description = "Expense amount")
        private double amount;

        @Override
        public void run() {
            if (amount > 0){
                ExpenseTracker expenseTracker = new ExpenseTracker();
                expenseTracker.addExpense(amount, description);
                System.out.println("Expense added successfully");
            }else
                System.out.println("Error: Amount must greater than zero");

        }
    }

    @Command(name = "list", description = "List all expenses")
    static class ListCommand implements Runnable {
        @Override
        public void run() {
            ExpenseTracker expenseTracker = new ExpenseTracker();
            expenseTracker.listExpenses();
        }
    }

    @Command(name = "summary", description = "Show expense summary")
    static class SummaryCommand implements Runnable {
        @Option(names = {"--month"}, description = "Filter by month (1-12)")
        private Integer month;

        @Override
        public void run() {
            ExpenseTracker expenseTracker = new ExpenseTracker();
            if (month == null) {
                expenseTracker.expensesSummary();
            } else if (month < 1 || month > 12) {
                System.out.println("Error: Month must be between 1 and 12.");
            } else {
                expenseTracker.summaryByMonth(month);
            }
        }
    }

    @Command(name = "delete", description = "Delete an expense by ID")
    static class DeleteCommand implements Runnable {
        @Option(names = {"--id"}, required = true, description = "ID of the expense to delete")
        private int id;

        @Override
        public void run() {
            ExpenseTracker expenseTracker = new ExpenseTracker();
            expenseTracker.deleteExpense(id);
            System.out.println("Expense deleted successfully");
        }
    }
}