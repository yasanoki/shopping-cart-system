import java.util.Date;

public class CashPayment extends PaymentMethod {
    private double chargeAmount;
    
    public CashPayment(double paymentAmount, double chargeAmount) {
        this.paymentId = "CASH-" + System.currentTimeMillis();
        this.paymentDate = new Date();
        this.paymentAmount = paymentAmount;
        this.chargeAmount = chargeAmount;
    }
    
    public void processPayment(double amount) {
        System.out.println("Processing cash payment of RM " + amount);
    }

    public String getPaymentId() {
        return paymentId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }
    
    public double getChargeAmount() {
        return chargeAmount;
    }
    
    public double getChange() {
        return chargeAmount - paymentAmount;
    }
}