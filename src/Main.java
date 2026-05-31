import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        final String ROSTER_FILE = "roster.dat";
        ArrayList<Course> courses = Course.loadRoster(ROSTER_FILE);

        if (courses.isEmpty()) {
            System.out.println("No database found. Generating default course ...");
            Faculty instructor = new Faculty(7001, "Dr. John Doe", "Computer Science");
            Course defaultCourse = new Course("OOP-121", "Object Oriented Programming", instructor);
            courses.add(defaultCourse);
        }else
            System.out.println("Courses roster data loaded from file");

        Course activeCourse = courses.get(0);

        boolean running = true;
        while (running) {
            System.out.println("1. Register a New Student");
            System.out.println("2. View Roster");
            System.out.println("3. View Academic Leaderboard (Highest GPA First)");
            System.out.println("4. Drop Student by ID");
            System.out.println("5. Save Records and Exit System");
            System.out.print("Select menu option: ");

            int choice;
            try {
                choice = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please input a valid option number.");
                continue;
            }

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Enter Student ID: ");
                        int id = Integer.parseInt(input.nextLine().trim());

                        System.out.print("Enter Student Name: ");
                        String name = input.nextLine().trim();

                        System.out.print("Enter GPA: ");
                        double gpa = Double.parseDouble(input.nextLine().trim());

                        Student newStudent = new Student(id, name, gpa);
                        boolean added = activeCourse.registerStudent(newStudent);
                        if (added) {
                            System.out.println("Update: Student successfully added to enrollment roster.");
                        }
                    } catch (InvalidGPAException e) {
                        System.out.println("Registration Terminated: " + e.getMessage());
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid numeric input type typed.");
                        input.nextLine();
                    }
                    break;

                case 2:
                    System.out.println("\n--- Live Class Roster Status ---");
                    activeCourse.displayClassRoster();
                    break;

                case 3:
                    System.out.println("\n--- GPA Performance Leaderboard ---");
                    activeCourse.displayGpaLeaderboard();
                    break;

                case 4:
                    System.out.print("Enter target Student ID to drop: ");
                    int dropId = Integer.parseInt(input.nextLine().trim());
                    boolean removed = activeCourse.dropStudent(dropId);
                    if (removed) {
                        System.out.println("Update: Student successfully removed from enrollment roster.");
                    }
                    break;

                case 5:
                    Course.saveRoster(ROSTER_FILE, courses);
                    running = false;
                    break;

                default:
                    System.out.println("Please choose a value from 1 to 5.");
            }
        }
        input.close();
    }
}