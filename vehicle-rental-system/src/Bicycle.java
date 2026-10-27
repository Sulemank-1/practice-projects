public class Bicycle extends Vehicle{
    //Data Fields
    private boolean isElectric;

    //Constructor
    public Bicycle(String licensePlate, String model, double dailyRate, boolean isRented, boolean isElectric)throws InvalidRentalException{
        super(licensePlate, model, dailyRate, isRented);
        this.isElectric = isElectric;
    }

    //Getter
    public boolean isElectric(){
        return isElectric;
    }

    //Methods
    @Override
    public double calculateRentalCost(int days) {
        if(isElectric)
            return (getDailyRate() + 2) * days;
        else
            return getDailyRate() * days;
    }
}
