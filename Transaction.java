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

}
