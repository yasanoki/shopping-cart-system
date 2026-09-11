import java.util.Date;

interface Payment {
    void processPayment(double amount);
    String getPaymentId();
    Date getPaymentDate();
    double getPaymentAmount();
}

