import java.io.*;
import java.util.*;

public class Course implements Serializable {
    //Data Fields
    private String courseCode;
    private String title;
    private Faculty instructor;
    private Set<Student> enrolledStudents;
    private final int MAX_SEATS = 3;

    //Constructor
    public Course(String courseCode, String title, Faculty instructor){
        this.courseCode = courseCode;
        this.title = title;
        this.instructor = instructor;
        enrolledStudents = new HashSet<>();
    }

    //Getters
    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public Faculty getInstructor() {
        return instructor;
    }

    public Set<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    //Setters
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInstructor(Faculty instructor) {
        this.instructor = instructor;
    }

    //Methods
    public boolean registerStudent(Student s){
        if (enrolledStudents.size() >= MAX_SEATS){
            System.out.println("Course seats full");
            return false;
        }
        boolean isAdded = enrolledStudents.add(s);
        if (!isAdded){
            System.out.println("Student ID already exists");
            return false;
        }
        return true;
    }

    public boolean dropStudent(int studentID){
        for (Student student: enrolledStudents)
            if (student.getId() == studentID) {
                enrolledStudents.remove(student);
                return true;
            }
        System.out.println("Student ID not found");
        return false;
    }

    public void displayClassRoster(){
        System.out.println("Instructor Name: " + instructor.getName());
        System.out.println("Number of students in course: " + enrolledStudents.size());
        for (Student student: enrolledStudents)
            System.out.println(student);
    }

    public void displayGpaLeaderboard(){
        ArrayList<Student> sortedList = new ArrayList<>(enrolledStudents);
        Collections.sort(sortedList);
        for (Student student: sortedList)
            System.out.println(student);
    }

    public static void saveRoster(String filename, ArrayList<Course> activeCourses) {
        StorageEngine.saveData(filename, activeCourses);
    }

    public static ArrayList<Course> loadRoster(String filename) {
       return StorageEngine.loadData(filename);
    }
}
