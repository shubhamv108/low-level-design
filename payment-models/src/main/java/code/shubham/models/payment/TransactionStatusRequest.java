package code.shubham.models.payment;

public class TransactionStatusRequest {
    class Header {
        private String clientRequestTimestamp;
        private String version;
        private String clientId;
        private String channelId;
        private String checksum;
    }

    public class Body {
        private String clientReferenceId;
        private String orderId;
        private String accountId;
    }
}
