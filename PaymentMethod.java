import java.util.Date;

abstract public class PaymentMethod implements Payment {
    protected String paymentId;
    protected Date paymentDate;
    protected double paymentAmount;

    abstract public void processPayment(double amount);
    abstract public String getPaymentId();
    abstract public Date getPaymentDate();
    abstract public double getPaymentAmount();
    
}
