import java.io.Serializable;

public abstract class Member implements Serializable, Cloneable, Comparable<Member>{
    //Data Fields
    private int memberID;
    private String name;
    private double height;


    //Constructor
    protected Member(int memberID, String name, double height) throws InvalidMemberDataException {
        if (height <= 0)
            throw new InvalidMemberDataException("Height must be greater than 0");

        if (name == null || name.trim().isEmpty())
            throw new InvalidMemberDataException("Name cannot be empty");
        this.memberID = memberID;
        this.name = name;
        this.height = height;
    }

    //Getters
    public int getMemberID() {
        return memberID;
    }

    public String getName() {
        return name;
    }

    public double getHeight() {
        return height;
    }


    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    //Methods
    public abstract double calculateMonthlyDues();

    @Override
    public String toString() {
        return "ID: " + memberID + " | Name: " + name;
    }

    @Override
    public int compareTo(Member o){
        return getName().compareTo(o.getName());
    }

    @Override
    public Object clone(){
        try {
            return super.clone();
        }catch (CloneNotSupportedException ex) {
            return null;
        }
    }
}
