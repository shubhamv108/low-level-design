package code.shubham.models.payment;

import java.util.Map;

public class PaymentModeDetails {
    private CardInfo cardInfo;
    private Transfer transfer;
}

class Transfer {
    private String fromAccount;
    private String toAccount;
    private String toAccountName;
    private String toAccountIfsc;

}
