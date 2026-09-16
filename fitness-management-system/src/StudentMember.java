public class StudentMember extends Member{
    //Data Fields
    private String schoolName;

    //Constructor
    public StudentMember(int memberID, String name, double height, String schoolName) throws InvalidMemberDataException {
        super(memberID, name, height);
        this.schoolName = schoolName;
    }

    //Getter
    public String getSchoolName() {
        return schoolName;
    }

    //Setter
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    //Methods
    @Override
    public double calculateMonthlyDues(){
        return 30.0 - (30.0 * 0.2);
    }

    @Override
    public String toString(){
        return super.toString() + " Membership Type: Student Member";
    }
}
