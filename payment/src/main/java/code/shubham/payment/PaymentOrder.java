package code.shubham.payment;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment_orders")
public class PaymentOrder extends BaseAbstractAuditableEntity {

    @Column(unique = true)
    private String transactionToken;

    private TransactionType type;

    @Column(unique = true)
    private String clientReferenceId;

    private String orderId;
    private String accountId;

    private BigDecimal amount;
    private Currency currency;

    private String paymentServiceProvider;
    private String paymentServiceProviderTransactionReferenceId;
    private String paymentGatewayTransactionStatus;
    private BigDecimal receivedAmount;
    private Status status;

    private String callBackUrl;

    private boolean ledgerUpdated;
    private boolean walletUpdated;

//    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> metadata;
}