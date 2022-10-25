package code.shubham.models.payment;

import java.util.Date;

public class TransactionStatusResponse {
    class Header {
        private String clientRequestTimestamp;
        private String version;
        private String clientId;
        private String channelId;
        private String checksum;
    }

    class Body {
        private ResultInfo resultInfo;
        private String clientReferenceId;
        private String orderId;
        private TransactionAmount amount;
        private String gatewayName;
        private String bankName;
        private PaymentMode paymentMode;
        private Date transactionTimestamp;

    }
}
