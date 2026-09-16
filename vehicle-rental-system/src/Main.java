import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        FleetManager manager = new FleetManager("Arcade Vehicle Rental");
        final String FLEET_FILE = "fleet.dat";

        manager.loadFleet(FLEET_FILE);

        boolean running = true;
        while (running) {
            System.out.println("\n=== " + manager.getAgencyName() + " Management ===");
            System.out.println("1. Add New Car to fleet");
            System.out.println("2. Add New Truck to fleet");
            System.out.println("3. Display Full Fleet Inventory Status");
            System.out.println("4. Check Out/Rent vehicle via License Plate");
            System.out.println("5. Check In/Return vehicle via License Plate");
            System.out.println("6. Calculate Daily Revenue from Active Rentals");
            System.out.println("7. Save fleet logs to file");
            System.out.print("Select choice: ");

            int choice;
            try {
                choice = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Choose a valid menu index number.");
                continue;
            }

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Enter Plate: ");
                        String plate = input.nextLine().trim();

                        System.out.print("Enter Model: ");
                        String model = input.nextLine().trim();

                        System.out.print("Enter Daily Rate ($): ");
                        double rate = Double.parseDouble(input.nextLine().trim());

                        System.out.print("Include GPS Integration? (true/false): ");
                        boolean gps = Boolean.parseBoolean(input.nextLine().trim());


                        manager.addVehicle(new Car(plate, model, rate, false, gps));
                        System.out.println("Success: Car unit added to fleet.");
                    } catch (InvalidRentalException e) {
                        System.out.println("Addition Blocked: " + e.getMessage());
                    } catch (InputMismatchException e) {
                        System.out.println("Typing Format Violation. Operation aborted.");
                        input.nextLine();
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Enter Plate: ");
                        String plate = input.nextLine().trim();

                        System.out.print("Enter Model: ");
                        String model = input.nextLine().trim();

                        System.out.print("Enter Daily Rate ($): ");
                        double rate = Double.parseDouble(input.nextLine().trim());

                        System.out.print("Enter Cargo Capacity Limit (Tons): ");
                        double tons = Double.parseDouble(input.nextLine().trim());

                        manager.addVehicle(new Truck(plate, model, rate, false, tons));
                        System.out.println("Success: Truck unit added to fleet.");
                    } catch (InvalidRentalException e) {
                        System.out.println("Addition Blocked: " + e.getMessage());
                    } catch (InputMismatchException e) {
                        System.out.println("Typing Format Violation. Operation aborted.");
                        input.nextLine();
                    }
                    break;

                case 3:
                    System.out.println("\n--- Current Active Fleet Status Inventory ---");
                    manager.displayFleetStatus();
                    break;

                case 4:
                    System.out.print("Enter license plate: ");
                    String checkoutPlate = input.nextLine().trim();

                    System.out.print("Enter Customer Name: ");
                    String customerName = input.nextLine().trim();

                    manager.processRental(checkoutPlate, customerName);
                    break;

                case 5:
                    System.out.print("Enter license plate: ");
                    String returnPlate = input.nextLine().trim();

                    manager.processReturn(returnPlate);
                    break;

                case 6:
                    System.out.printf("Current live operational cash-flow trend: $%.2f / day\n",
                            manager.calculateTotalFleetValue());
                    break;

                case 7:
                    manager.saveFleet(FLEET_FILE);
                    System.out.println("Fleet log saved to file.");
                    running = false;
                    break;

                default:
                    System.out.println("Select index numbers 1 through 7.");
            }
        }
        input.close();
    }
}