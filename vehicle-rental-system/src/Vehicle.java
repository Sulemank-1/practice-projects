import java.io.Serializable;
import java.util.*;

public abstract class Vehicle implements Serializable, Comparable<Vehicle>{
    //Data Fields
    private String licensePlate;
    private String model;
    private double dailyRate;
    private boolean isRented;
    private Queue<String> waitlist;

    //Constructor
    public Vehicle(String licensePlate, String model, double dailyRate, boolean isRented) throws InvalidRentalException{
        if (dailyRate <= 0)
            throw new InvalidRentalException("Invalid daily rate");
        this.licensePlate = licensePlate;
        this.model = model;
        this.dailyRate = dailyRate;
        this.isRented = isRented;
        this.waitlist = new LinkedList<>();
    }

    //Getters
    public String getLicensePlate() {
        return licensePlate;
    }

    public String getModel() {
        return model;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public boolean isRented() {
        return isRented;
    }

    public Queue<String> getWaitlist() {
        return waitlist;
    }

    //Setters
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    //Methods
    public void addToWaitlist(String customerName) {
        waitlist.offer(customerName);
    }

    public String popNextFromWaitlist() {
        return waitlist.poll();
    }

    public void rentVehicle(){
        isRented = true;
    }

    public void returnVehicle(){
        isRented = false;
    }

    public abstract double calculateRentalCost(int days);

    @Override
    public String toString(){
        return "Licence Plate: " + getLicensePlate() + "| Model: " + getModel() + "| Daily Rate: " + getDailyRate() + "| Rented Status " + (isRented() ? "Rented" : "Not Rented");
    }

    @Override
    public int compareTo(Vehicle o) {
        return Double.compare(o.getDailyRate(), getDailyRate());
    }
}
