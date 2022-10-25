package lowleveldesign.bank.entities.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.bank.entities.Account;
import lowleveldesign.bank.entities.base.BaseAbstractAuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
        })
})
public class Transaction extends BaseAbstractAuditableEntity {

    @Column(nullable = false, unique = true, updatable = false)
    private String transactionId;

    @Enumerated
    private TransactionType type;

    @Enumerated
    private TransactionStatus status;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "account_number", referencedColumnName = "accountNumber")
    private Account account;
}
