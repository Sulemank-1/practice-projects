public class Faculty extends Person{
    //Data Fields
    private String department;

    //Constructor
    public Faculty(int employeeID, String name, String department){
        super(employeeID, name);
        this.department = department;
    }

    //Getters
    public String getDepartment() {
        return department;
    }

    //Setters
    public void setDepartment(String department) {
        this.department = department;
    }

    //Methods
    @Override
    public String toString(){
        return "ID: " + getId() + " | Name: " + getName() + " | Department: " + getDepartment();
    }

    @Override
    public String getRole(){
        return "Faculty";
    }
}
