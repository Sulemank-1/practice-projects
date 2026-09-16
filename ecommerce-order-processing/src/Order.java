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
    public void addProduct(Product p) {
        orderItems.add(p);
        commandHistory.push(p);
    }

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

    public double calculateSubtotal() {
        double sum = 0.0;
        for (Product p : orderItems)
            sum += p.getFinalPrice();
        return sum;
    }

    public double calculateShipping() {
        double sum = 0.0;
        for (Product p : orderItems)
            sum += p.getWeight();
        return sum * SHIPPING_RATE_PER_KG;
    }

    public double calculateTotal() {
        return calculateSubtotal() + calculateShipping();
    }

    public void displayOrderSummary() {
        for (Product p : orderItems)
            System.out.println(p.toString());
        System.out.println("Subtotal: " + calculateSubtotal());
        System.out.println("Shipping Cost: " + calculateShipping());
        System.out.println("Grand Cost: " + calculateTotal());
    }

    public void saveReceipt(String filename) {
        StorageEngine.saveData(filename, orderItems);
    }

    public void loadReceipt(String filename) {
        orderItems = StorageEngine.loadData(filename);
        if (!orderItems.isEmpty()) {
            System.out.println("Receipt successfully loaded from file.");
            commandHistory.clear();
        } else {
            System.out.println("No pre-existing order records found.");
        }
    }

    public void sortItemsByPrice() {
        if (orderItems.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        ArrayList<Product> sortedList = new ArrayList<>(orderItems);
        Collections.sort(sortedList);
        for (Product p : sortedList) {
            System.out.println(p + " | Final Price: $" + p.getFinalPrice());
        }
    }
}
