import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Order activeOrder = new Order(786);
        final String RECEIPT_FILE = "receipt.dat";

        activeOrder.loadReceipt(RECEIPT_FILE);

        boolean running = true;
        while (running) {
            System.out.println("\n=== E-Commerce Order Processing ===");
            System.out.println("1. Add a Physical Product (Taxable)");
            System.out.println("2. Add a Digital Product");
            System.out.println("3. View Current Cart");
            System.out.println("4. View Cart Sorted by Price (Cheapest First)");
            System.out.println("5. Undo Last Item Addition");
            System.out.println("6. Save Invoice and Close");
            System.out.print("Select choice: ");

            int choice;
            try {
                choice = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Input Error: Please select an integer from the menu.");
                continue;
            }

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Enter Product ID: ");
                        int id = Integer.parseInt(input.nextLine().trim());

                        System.out.print("Enter Item Name: ");
                        String name = input.nextLine().trim();

                        System.out.print("Enter Base Price ($): ");
                        double price = Double.parseDouble(input.nextLine().trim());

                        System.out.print("Enter Shipping Weight (kg): ");
                        double weight = Double.parseDouble(input.nextLine().trim());

                        System.out.print("Enter Standard Tax Rate (e.g., 0.15 for 15%): ");
                        double tax = Double.parseDouble(input.nextLine().trim());

                        activeOrder.addProduct(new PhysicalProduct(id, name, price, weight, tax));
                        System.out.println("Success: Physical item appended to checkout array.");
                    } catch (InvalidProductException e) {
                        System.out.println("Validation Failed: " + e.getMessage());
                    } catch (NumberFormatException e) {
                        System.out.println("Typing Error: Invalid format numeric stream provided.");
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Enter Product ID: ");
                        int id = Integer.parseInt(input.nextLine().trim());
                        System.out.print("Enter Item Name: ");
                        String name = input.nextLine().trim();
                        System.out.print("Enter Price ($): ");
                        double price = Double.parseDouble(input.nextLine().trim());

                        activeOrder.addProduct(new DigitalProduct(id, name, price));
                        System.out.println("Success: Digital file added.");
                    } catch (InvalidProductException e) {
                        System.out.println("Validation Failed: " + e.getMessage());
                    } catch (NumberFormatException e) {
                        System.out.println("Typing Error: Invalid format numeric stream provided.");
                    }
                    break;

                case 3:
                    System.out.println("\n--- Current Active Cart Contents ---");
                    activeOrder.displayOrderSummary();
                    break;

                case 4:
                    System.out.println("\n--- Sorted Presentation (Cheapest First) ---");
                    activeOrder.sortItemsByPrice();
                    break;

                case 5:
                    System.out.println("\n--- Reverting Last Action ---");
                    if (activeOrder.undoLastAction())
                        System.out.println("Removed last item");
                    else
                        System.out.println("Error: Action couldn't be completed");
                    break;
                case 6:
                    activeOrder.saveReceipt(RECEIPT_FILE);
                    System.out.println("Receipt saved.");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid execution target choice. Please choose 1-5.");
            }
        }
        input.close();
    }
}