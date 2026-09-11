import java.util.Date;

public class BankTransfer extends PaymentMethod {
    private String bankName;
    private int bankAccountNumber;

    public BankTransfer(double paymentAmount,String bankName,int bankAccountNumber){
        this.paymentId = "BANKTRANSFER-" + System.currentTimeMillis();
        this.paymentDate = new Date();
        this.paymentAmount = paymentAmount;
        this.bankAccountNumber = bankAccountNumber;
        this.bankName = bankName;
    }

    public void processPayment(double amount) {
        System.out.println("Processing bank transfer of RM " + amount + " with bank in " + bankName);
        System.out.println("Bank account number:"+bankAccountNumber);
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

    public int getBankAccountNumber() {
        return bankAccountNumber;
    }
    
    public String bankName() {
        return bankName;
    }
}
