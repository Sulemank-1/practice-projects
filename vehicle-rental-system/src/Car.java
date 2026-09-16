public class Car extends Vehicle{
    //Data Fields
    private boolean hasGPS;

    //Constructor
    public Car(String licensePlate, String model, double dailyRate, boolean isRented, boolean hasGPS) throws InvalidRentalException{
        super(licensePlate, model, dailyRate, isRented);
        this.hasGPS = hasGPS;
    }

    //Getter
    public boolean getHasGPS() {
        return hasGPS;
    }

    //Setter
    public void setHasGPS(boolean hasGPS) {
        this.hasGPS = hasGPS;
    }

    //Methods
    @Override
    public double calculateRentalCost(int days){
        if (getHasGPS())
            return (getDailyRate() + 5.0) * days;
        else
            return getDailyRate() * days;
    }

    @Override
    public String toString(){
        return super.toString() + "| GPS status: " + (getHasGPS()? "Available" : "Not available");
    }
}
