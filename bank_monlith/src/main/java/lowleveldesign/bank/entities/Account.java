package lowleveldesign.bank.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.bank.entities.base.BaseAbstractAuditableEntity;
import lowleveldesign.bank.entities.transaction.Transaction;
import lowleveldesign.bank.entities.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
        })
})
public class Account extends BaseAbstractAuditableEntity {

    @Column(nullable = false, unique = true, updatable = false)
    private String accountNumber;

    @Column(nullable = false)
    private User holder;

    @Enumerated
    @Column(nullable = false)
    private AccountStatus status;

    @Lob
    @Column(length=100000)
    private String statusReason;

    @ManyToOne
    @JoinColumn(name = "bank_id", referencedColumnName = "id")
    private Bank bank;

    @OneToMany(mappedBy = "account")
    List<Transaction> transactions;
}
