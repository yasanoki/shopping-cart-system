
public class Product {
    private String productName;
    private String brand;
    private String model;
    private int quantity;
    private double price;
    
    public Product(String productName, String brand, String model, int quantity, double price){
        this.productName = productName;
        this.brand = brand;
        this.model = model;
        this.quantity = quantity;
        this.price = price;
    }
    
    public String getProductName(){
        return productName;
    }
    
    public void setProductName(String productName){
        this.productName = productName;
    }
    
    public String getBrand(){
        return brand;
    }
    
    public void setBrand(String brand){
        this.brand = brand;
    }
    
    public String getModel(){
        return model;
    }
    
    public void setModel(String model){
        this.model = model;
    }
    
    public int getQuantity(){
        return quantity;
    }
    
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    
    public double getPrice(){
        return price;
    }
    
    public void setPrice(double price){
        this.price = price;
    }
    
    public String toString(){
        return "Product" + '\n' +
                "Product Name: " + productName + '\n' +
                "Brand: " + brand + '\n' +
                "Model: " + model + '\n' +
                "Quantity: " + quantity + '\n' +
                "Price: " + price + '\n' + 
                '\n';
    }
}
