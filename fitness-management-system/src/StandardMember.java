public class StandardMember extends Member {

    //Constructor
    public StandardMember(int memberID, String name, double height) throws InvalidMemberDataException{
        super(memberID, name, height);
    }

    //Methods
    @Override
    public double calculateMonthlyDues() {
        return 30.0;
    }

    @Override
    public String toString(){
        return super.toString() + " Membership Type: Standard Member";
    }
}
