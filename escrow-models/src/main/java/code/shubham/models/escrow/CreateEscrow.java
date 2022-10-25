package code.shubham.models.escrow;

import java.math.BigDecimal;

public class CreateEscrow {

    public class Request {
        public class Headers {
            private String userId;
            private String checksum;
        }
        public class Body {
            private String agreementText;
            private BigDecimal amount;
            private ReceiverDetails receiverDetails;
            private SenderDetails senderDetails;
        }
    }

    public class Response {
        public class Headers {
            private String userId;
            private String checksum;
        }
        public class Body {

        }
    }

}
