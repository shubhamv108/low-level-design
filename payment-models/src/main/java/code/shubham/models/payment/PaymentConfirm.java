package code.shubham.models.payment;

public class PaymentConfirm {
    public class Request {
        public class Headers {
            private String clientRequestTimestamp;
            private String version;
            private String clientId;
            private String channelId;
            private String checksum;
        }

        public class Body {
            private String orderId;
            private String accountId;

            private PaymentMode paymentMode;
            private PaymentModeDetails paymentModeDetails;

            private String channelCode; // netbanking bank
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

        class Body {
            private ResultInfo resultInfo;
            private String callbackUrl; // same as Initiate
        }
    }
}
