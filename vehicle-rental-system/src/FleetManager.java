import java.io.*;
import java.util.*;

public class FleetManager {
    //Data Fields
    private String agencyName; //Agency Name
    private Map<String, Vehicle> fleet; //Vehicle fleet

    //Constructor
    public FleetManager(String agencyName){
        this.agencyName = agencyName;
        fleet = new HashMap<>();
    }

    //Getters
    public String getAgencyName() {
        return agencyName;
    }

    //Setters
    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    //Methods

    //Add a vehicle to fleet
    public void addVehicle(Vehicle v){
        String plate = v.getLicensePlate().toLowerCase().trim();

        if (fleet.containsKey(plate)){
            System.out.println("Duplicate Licence Plate");
            return;
        }
        fleet.put(v.getLicensePlate(), v);
    }

    //Process vehicle rental
    public boolean processRental(String plate, String customerName) {
        String standardizedPlate = plate.toLowerCase().trim();
        if (fleet.containsKey(standardizedPlate)) {
            Vehicle vehicle = fleet.get(standardizedPlate);
            if (!vehicle.isRented()) {
                vehicle.rentVehicle();
                System.out.println("Success: Vehicle " + plate.toUpperCase() + " has been checked out.");
                return true;
            } else {
                vehicle.addToWaitlist(customerName);
                System.out.println("Notice: " + plate.toUpperCase() + " is currently occupied.");
                return false;
            }
        }
        System.out.println("License plate not found in fleet records.");
        return false;
    }

    //Process vehicle return
    public boolean processReturn(String plate) {
        String standardizedPlate = plate.toLowerCase().trim();
        if (fleet.containsKey(standardizedPlate)) {
            Vehicle vehicle = fleet.get(standardizedPlate);
            if (vehicle.isRented()) {
                if (!vehicle.getWaitlist().isEmpty()) {
                    String nextCustomer = vehicle.popNextFromWaitlist();
                    System.out.println("Vehicle returned");
                    System.out.println("WAITLIST ALERT: Automatically assigning vehicle to next customer in line: " + nextCustomer);
                    System.out.println("Remaining customers in queue: " + vehicle.getWaitlist().size());
                } else {
                    vehicle.returnVehicle();
                    System.out.println("Vehicle returned");
                    System.out.println("Vehicle status updated to Available.");
                }
                return true;
            } else {
                System.out.println("Vehicle is not currently checked out.");
                return false;
            }
        }
        System.out.println("License plate not found in fleet records.");
        return false;
    }

    //Calculate total fleet value of rented vehicles
    public double calculateTotalFleetValue(){
        return fleet.values().stream()
                .filter(Vehicle::isRented)
                .mapToDouble(Vehicle::getDailyRate)
                .sum();
    }

    //Display each vehicle of fleet
    public void displayFleetStatus(){
        fleet.values().forEach(System.out::println);
    }

    //Save fleet data to file
    public void saveFleet(String filename){
        StorageEngine.saveData(filename, fleet);
    }

    //Load fleet data from file
    public void loadFleet(String filename) {
        fleet = StorageEngine.loadData(filename);
        if (!fleet.isEmpty()) {
            System.out.println("Fleet records successfully loaded into application.");
        } else {
            System.out.println("No fleet logs found");
        }
    }
}
