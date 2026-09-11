import java.util.Date;

public class CardPayment extends PaymentMethod {
    private String cardNumber;
    private String cardHolderName;
    private String expDate;
    private String cvv;
    
    public CardPayment(double paymentAmount, String cardNumber, String cardHolderName, String expDate, String cvv) {
        this.paymentId = "CARD-" + System.currentTimeMillis();
        this.paymentDate = new Date();
        this.paymentAmount = paymentAmount;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expDate = expDate;
        this.cvv = cvv;
    }
    
    public void processPayment(double amount) {
        System.out.println("Processing card payment of RM " + amount + " with card ending in " + cardNumber.substring(cardNumber.length() - 4));
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
    
    public String getCardNumber() {
        return cardNumber;
    }
    
    public String getCardHolderName() {
        return cardHolderName;
    }
    
    public String getExpDate() {
        return expDate;
    }
    
    public String getCvv() {
        return cvv;
    }
}