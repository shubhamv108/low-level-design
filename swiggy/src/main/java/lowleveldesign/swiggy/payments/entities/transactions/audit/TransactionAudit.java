package lowleveldesign.swiggy.payments.entities.transactions.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.swiggy.payments.entities.transactions.Transaction;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction_audits")
public class TransactionAudit {

    private String paymentGatewayRequest;
    private String paymentGatewayResponse;

    @ManyToOne
    @JoinColumn(name = "transactions", referencedColumnName = "id", nullable = false, updatable = false)
    private Transaction transaction;

}
