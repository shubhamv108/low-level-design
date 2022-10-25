package code.shubham.models.payment;

import java.util.Map;

public class PaymentOrder {
    private String orderId;
    private String accountId;
    private TransactionAmount transactionAmount;

    private String transactionToken;

    private String callBackUrl;
    private Map<String, Object> metadata;
}