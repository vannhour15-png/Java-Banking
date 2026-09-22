import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private static int counter = 1;
    private String transactionId;
    private String transactionType;
    private double amount;
    private String date;
    private String time;
    private String accId;
    private String receiptNo;

    public String getAccId() {
        return accId;
    }
    
    public Transaction(String transactionType, double amount, String accId) {
        this.transactionId = "T" + String.format("%03d", counter);
        this.receiptNo = "R" + String.format("%03d", counter);
        counter++;

        this.transactionType = transactionType;
        this.amount = amount;
        this.accId = accId;

        LocalDateTime now = LocalDateTime.now();
        this.date = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.time = now.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public void viewTransaction() {
        System.out.println("Transaction ID: " + transactionId);
        System.out.println("Type: " + transactionType);
        System.out.println("Amount: $" + String.format("%.2f", amount));
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
        System.out.println("Receipt No: " + receiptNo);
        System.out.println("---------------------------");
    }
}
