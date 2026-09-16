public class PremiumMember extends Member{
    //Data Fields
    private final double personalTrainerFee = 100;

    //Constructor
    public PremiumMember(int memberID, String name, double height) throws InvalidMemberDataException {
        super(memberID, name, height);
    }

    //Methods
    @Override
    public double calculateMonthlyDues(){
        return 30.0 + personalTrainerFee;
    }

    @Override
    public String toString(){
        return super.toString() + " Membership Type: Premium Member";
    }
}
