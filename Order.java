import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Order {
    static final String ORDER_HISTORY_FILE = "order_history.txt";
    private String orderId;
    private Customer customer;
    private List<OrderItem> items;
    private PaymentMethod paymentMethod;
    private Date orderDate;
    private double totalAmount;
    
    
    public Order(String orderId, Customer customer, ShoppingCart cart, PaymentMethod paymentMethod) {
        this.orderId = orderId;
        this.customer = customer;
        this.paymentMethod = paymentMethod;
        this.orderDate = new Date();
        this.items = cartToOrderItem(cart);
        this.totalAmount = calculateTotal();
    }
    
    public Order(String orderId, Customer customer, Date orderDate, List<OrderItem> items, PaymentMethod payment, double totalAmount){
        this.orderId = orderId;
        this.customer = customer;
        this.orderDate = orderDate;
        this.items = items;
        this.paymentMethod = payment;
        this.totalAmount = totalAmount;
    }
    
    private List<OrderItem> cartToOrderItem(ShoppingCart cart){
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            orderItems.add(new OrderItem(cartItem.getProduct(), cartItem.getQuantity()));
        }
        return orderItems;
    }
    
    public void printOrderDetails(){
        System.out.println("\n==== Order Details ====");
        System.out.println("Order ID: " + orderId);
        System.out.println("Order Date: " + orderDate);
        System.out.println("Customer: " + customer.getUsername());
        System.out.println("Payment Method: " + paymentMethod.getClass().getSimpleName());
        
        System.out.println("\nItem List:");
        System.out.println("--------------------------------------------------------------");
        System.out.printf("%-20s %-10s %-10s %-10s\n", 
                        "Item", "Quantity", "Unit Price", "Total Price");
        System.out.println("--------------------------------------------------------------");
        
        for(OrderItem item : items){
            System.out.printf("%-20s %-10d RM%-9.2f RM%-9.2f\n", 
                             item.getProductName(), 
                             item.getQuantity(), 
                             item.getPrice(), 
                             item.getTotal());
        }
        
        System.out.println("--------------------------------------------------------------");
        System.out.printf("%42s RM%.2f\n", "Total Amount: ", totalAmount);
    }
    
    private double calculateTotal(){
        double total = 0.0;
        for(OrderItem item : items){
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
    
    public double getTotalAmount(){
        return totalAmount;
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public List<OrderItem> getItems(){
        return items;
    }
    
    public PaymentMethod getPaymentMethod(){
        return paymentMethod;
    }
    
    public Date getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(Date orderDate){
        this.orderDate = orderDate;
    }
    
    public void setTotalAmount(double totalAmount){
        this.totalAmount = totalAmount;
    }
    
    public void saveToFile(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(ORDER_HISTORY_FILE, true))){
            writer.write(toFileString());
            writer.newLine();
        }catch (IOException e){
            System.err.println("Failed to save order: " + e.getMessage());
        }
    }
    
    private String toFileString(){
        StringBuilder sb = new StringBuilder();
        sb.append(orderId).append(",")
          .append(customer.getUserId()).append(",")
          .append(orderDate.getTime()).append(",")
          .append(paymentMethod.getClass().getSimpleName()).append(",")
          .append(totalAmount);
        
        for (OrderItem item : items) {
            sb.append(",").append(item.getProductName())
              .append(":").append(item.getQuantity())
              .append(":").append(item.getPrice());
        }
        
        return sb.toString();
    }
}
