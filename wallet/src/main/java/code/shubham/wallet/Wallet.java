package code.shubham.wallet;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntityWithApprover;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wallets")
public class Wallet extends BaseAbstractAuditableEntityWithApprover {
    private Integer accountNumber;
    private List<PaymentCard> paymentCards; // jsonb
    private List<IdentityCard> identityCards; // jsonb
}
