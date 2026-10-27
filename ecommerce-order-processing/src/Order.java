import java.io.*;
import java.util.*;

public class Order implements Serializable {
    //Data Fields
    private int orderID;
    private ArrayList<Product> orderItems;
    private final double SHIPPING_RATE_PER_KG = 2.5;
    private Stack<Product> commandHistory;

    //Constructor
    public Order(int orderID) {
        this.orderID = orderID;
        orderItems = new ArrayList<>();
        commandHistory = new Stack<>();
    }

    //Getters
    public int getOrderID() {
        return orderID;
    }

    //Methods

    //Add a product to order
    public void addProduct(Product p) {
        orderItems.add(p);
        commandHistory.push(p);
    }

    //Remove last product from order
    public boolean undoLastAction() {
        if (commandHistory.isEmpty()) {
            System.out.println("Notice: Nothing to undo. Action history is empty.");
            return false;
        }

        Product lastAddedProduct = commandHistory.pop();

        boolean removed = orderItems.remove(lastAddedProduct);

        if (removed) {
            System.out.println("Undo Success: Removed [" + lastAddedProduct.getName() + "] from your cart.");
            return true;
        }

        return false;
    }

    //Calculate subtotal of order
    public double calculateSubtotal() {
        return orderItems.stream()
                .mapToDouble(Product::getFinalPrice)
                .sum();
    }

    //Calculate shipping price of order
    public double calculateShipping() {
        double sum = orderItems.stream()
                .mapToDouble(Product::getWeight)
                .sum();
        return sum * SHIPPING_RATE_PER_KG;
    }

    //Calculate total price of order
    public double calculateTotal() {
        return calculateSubtotal() + calculateShipping();
    }

    //Display order summary
    public void displayOrderSummary() {
        orderItems.forEach(System.out::println);
        System.out.println("Subtotal: " + calculateSubtotal());
        System.out.println("Shipping Cost: " + calculateShipping());
        System.out.println("Grand Cost: " + calculateTotal());
    }

    //Display order items sorted by price
    public void sortItemsByPrice() {
        if (orderItems.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        orderItems.stream()
                .sorted()
                .forEach(p -> System.out.println(p + " | Final Price: $" + p.getFinalPrice()));
    }

    //Save order receipt to file
    public void saveReceipt(String filename) {
        StorageEngine.saveData(filename, orderItems);
    }

    //Load order receipt from file
    public void loadReceipt(String filename) {
        orderItems = StorageEngine.loadData(filename);
        if (!orderItems.isEmpty()) {
            System.out.println("Receipt successfully loaded from file.");
            commandHistory.clear();
        } else {
            System.out.println("No pre-existing order records found.");
        }
    }
}
