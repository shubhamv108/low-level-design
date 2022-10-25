package code.shubham.models.payment;

import java.util.List;
import java.util.Map;

public class PaymentInitiate {
    public class Request {
        class Headers {
            private String clientRequestTimestamp;
            private String version;
            private String clientId;
            private String channelId;
            private String checksum;
        }
        class Body {
            private RequestType requestType;
            private List<PaymentOrder> paymentOrders;
        }
    }

    public class Response {
        class Headers {
            private String responseTimestamp;
            private String version;
            private String clientId;
            private String channelId;
            private String checksum;
        }

        public class Body {
            private List<PaymentOrderResult> paymentOrderResults;
        }

        class PaymentOrderResult {
            private String transactionToken; // valid for 15 mins
            private String orderId;
            private ResultStatus resultStatus;
            private Map<String, String> metadata;
        }

    }
}
