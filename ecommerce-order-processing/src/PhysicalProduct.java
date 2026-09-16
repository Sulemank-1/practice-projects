public class PhysicalProduct extends Product{
    //Data Fields
    private double taxRate;

    //Constructor
    public PhysicalProduct(int productID, String name, double basePrice, double weight, double taxRate) throws InvalidProductException{
        super(productID, name, basePrice, weight);
        this.taxRate = taxRate;
    }

    //Getters
    public double getTaxRate() {
        return taxRate;
    }

    @Override
    public double getFinalPrice(){
        double basePrice = super.getBasePrice();
        return basePrice + (basePrice * taxRate);
    }

}
