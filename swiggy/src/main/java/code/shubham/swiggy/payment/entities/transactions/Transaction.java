package code.shubham.swiggy.payment.entities.transactions;

import code.shubham.swiggy.payment.entities.Payment;
import code.shubham.swiggy.payment.entities.transactions.audit.TransactionAudit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import code.shubham.swiggy.commons.entities.Currency;
import code.shubham.swiggy.commons.entities.base.BaseAbstractAuditableEntity;
import code.shubham.swiggy.payment.entities.PaymentGateway;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Collection;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction extends BaseAbstractAuditableEntity {

    private BigDecimal amount;
    @Enumerated(value = EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private String paymentGatewayReferenceId;

    @ManyToOne
    @JoinColumn(name = "payment_gateway_id", referencedColumnName = "id", nullable = false, updatable = false)
    private PaymentGateway paymentGateway;

    @ManyToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Payment payment;

    @OneToMany(mappedBy = "transaction", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<TransactionAudit> audits;

}
