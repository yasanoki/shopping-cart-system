public class OrderItem {
    private String productName;
    private String brand;
    private String model;
    private int quantity;
    private double price;
    
    public OrderItem(Product product){
        this.productName = product.getProductName();
        this.brand = product.getBrand();
        this.model = product.getModel();
        this.quantity = product.getQuantity();
        this.price = product.getPrice();
    }
    
    public OrderItem(Product product, int quantity){
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.quantity = quantity;
    }

    public OrderItem(String product, int quantity,double price){
        this.productName = product;
        this.price = price;
        this.quantity = quantity;
    }

    
    public String getProductName(){
        return productName;
    }
    
    public String getBrand(){
        return brand;
    }
    
    public String getModel(){
        return model;
    }
    
    public int getQuantity(){
        return quantity;
    }
    
    public double getPrice(){
        return price;
    }
    
    public double getTotal(){
        return price * quantity;
    }
}
