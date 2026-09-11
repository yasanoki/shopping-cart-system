
import java.util.Date;

public class CheckoutService {

    public Order processCheckout(Customer customer, ShoppingCart cart, PaymentMethod payment) {
        double totalAmount = cart.getTotal();
        
        System.out.println("Processing checkout for order...");
        System.out.println("Customer: " + customer.getUsername());
        System.out.println("Total amount: RM " + totalAmount);
        
        payment.processPayment(totalAmount);
        
        String orderId = "ORD-" + System.currentTimeMillis();
        Order order = new Order(orderId, customer, cart, payment);
        
        System.out.println("\n==== Receipt =====");
        System.out.println("Order ID: " + orderId);
        System.out.println("Order date: " + new Date());
        if (payment instanceof CashPayment) {
            CashPayment cashPayment = (CashPayment) payment;
            System.out.printf("Cash Paid: RM %.2f \n",cashPayment.getChargeAmount());
            System.out.printf("Change: RM %.2f \n", cashPayment.getChange());
        }

        System.out.println("Payment processed successfully with ID: " + payment.getPaymentId());
        System.out.println("Payment date: " + payment.getPaymentDate());
        System.out.println("Order completed. Thank you!");
        return order;
    }
}