package code.shubham.models.wallet;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public class WalletBalanceTransfer {
    public class Request {
        public class Headers {
            private String clientId;
            private String checksum;
        }

        public class Body {
            String fromAccount;
            String toAccount;
            BigDecimal amount;
            Currency currency;
            UUID transactionId;
        }
    }
}
