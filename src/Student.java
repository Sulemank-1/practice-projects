import java.util.Objects;

public class Student extends Person implements Comparable<Student>{
    //Data Fields
    private double gpa;

    //Constructor
    public Student(int studentID, String name, double gpa) throws InvalidGPAException{
        super(studentID, name);
        if (!validGPA(gpa))
            throw new InvalidGPAException("GPA must be between 0.0 and 4.0");
        this.gpa = gpa;
    }

    //Getters
    public double getGpa() {
        return gpa;
    }

    //Setters
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    //Methods
    @Override
    public String toString(){
        return "ID: " + getId() + " | Name: " + getName() + " | GPA: " + getGpa();
    }

    @Override
    public String getRole(){
        return "Student";
    }

    @Override
    public int compareTo(Student o){
        return Double.compare(o.getGpa(), getGpa());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Student student = (Student) o;
        return getId() == student.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId()); // Generates a unique hash index based entirely on the ID
    }

    public boolean validGPA(double gpa){
        return (gpa >= 0.0 && gpa <= 4.0);
    }
}
