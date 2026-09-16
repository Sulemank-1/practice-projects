import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        GymManager manager = new GymManager("Titan Fitness");
        final String FILE_NAME = "members.dat";

        manager.loadMembersFromFile(FILE_NAME);

        boolean running = true;
        while (running) {
            System.out.println("\n=== " + manager.getGymName() +" Management System ===");
            System.out.println("1. Add a New Member");
            System.out.println("2. View All Members");
            System.out.println("3. Search Member by ID");
            System.out.println("4. Remove Member by ID");
            System.out.println("5. Calculate Total Monthly Revenue");
            System.out.println("6. Save and Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid menu number.");
                input.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Enter Member ID: ");
                        int id = Integer.parseInt(input.nextLine().trim());
                        System.out.print("Enter Name: ");
                        String name = input.nextLine().trim();
                        System.out.print("Enter Height (cm): ");
                        double height = Double.parseDouble(input.nextLine().trim());

                        System.out.println("Select Membership Type:");
                        System.out.println("1. Standard ($30)");
                        System.out.println("2. Student ($24 - 20% Off)");
                        System.out.println("3. Premium ($130 - Includes Trainer)");
                        System.out.print("Choice: ");
                        int typeChoice = Integer.parseInt(input.nextLine().trim());

                        switch (typeChoice) {
                            case 1:
                                manager.addMember(new StandardMember(id, name, height));
                                break;
                            case 2:
                                System.out.print("Enter School Name: ");
                                String school = input.nextLine().trim();
                                manager.addMember(new StudentMember(id, name, height, school));
                                break;
                            case 3:
                                manager.addMember(new PremiumMember(id, name, height));
                                break;
                            default:
                                System.out.println("Invalid type selected. Member registration canceled.");
                        }
                    } catch (InvalidMemberDataException e) {
                        System.out.println("Registration Failed: " + e.getMessage());
                    } catch (NumberFormatException e) {
                        System.out.println("Registration Failed: Invalid data type entered.");
                        input.nextLine();
                    }
                    break;

                case 2:
                    System.out.println("\n--- Current Gym Members ---");
                    manager.displayAllMembers();
                    break;

                case 3:
                    System.out.print("Enter Member ID to search: ");
                    int searchId = input.nextInt();
                    Member found = manager.findMember(searchId);
                    if (found != null) {
                        System.out.println("\n[Record Found]\n" + found);
                    } else {
                        System.out.println("No member found with ID: " + searchId);
                    }
                    break;

                case 4:
                    System.out.print("Enter Member ID to remove: ");
                    int removeId = Integer.parseInt(input.nextLine().trim());
                    boolean removed = manager.removeMember(removeId);
                    if (removed) {
                        System.out.println("Member removed");
                    } else {
                        System.out.println("No member found with ID: " + removeId);
                    }
                    break;

                case 5:
                    System.out.printf("Total Expected Monthly Revenue: $%.2f\n", manager.calculateTotalRevenue());
                    break;

                case 6:
                    manager.saveMembersToFile(FILE_NAME);
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please choose between 1 and 5.");
            }
        }
        input.close();
    }
}