public class DigitalProduct extends Product{
    //Data Fields
    private String downloadLink;

    //Constructor
    public DigitalProduct(int productID, String name, double basePrice) throws InvalidProductException{
        super(productID, name, basePrice, 0.0);
        downloadLink = "www.downloadLink.com";
    }

    //Getter
    public String getDownloadLink() {
        return downloadLink;
    }

    //Methods
    @Override
    public double getFinalPrice(){
        return getBasePrice();
    }

    @Override
    public String toString(){
        return super.toString() + " Download Link: " + getDownloadLink();
    }
}
