package code.shubham.models.payment;

import java.util.List;
import java.util.Map;

public class PaymentUpdate {
    public class Request {
        class Headers {
            private String clientRequestTimestamp;
            private String version;
            private String clientId;
            private String channelId;
            private String checksum;
        }
        public class Body {
            List<PaymentOrder> paymentOrders;
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
            List<PaymentOrderUpdateResponse> orderUpdateResponses;
        }

        public class PaymentOrderUpdateResponse {
            private ResultStatus resultStatus;
            private Map<String, String> metadata;
        }
    }
}
