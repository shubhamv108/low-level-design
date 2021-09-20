package lowleveldesign.swiggy.payments.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.swiggy.commons.entities.base.BaseAbstractAuditableEntity;
import lowleveldesign.swiggy.payments.entities.transactions.Transaction;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment_gateways")
public class PaymentGateway extends BaseAbstractAuditableEntity {

    private String name;

    @OneToMany(mappedBy = "paymentGateway", fetch = FetchType.LAZY)
    private Collection<Transaction> transactions;

}
