package lowleveldesign.swiggy.payments.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.swiggy.payments.entities.transactions.Transaction;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(name = "payments")
public class Payment {

    @Column(nullable = false, updatable = false)
    private Long userId;

    @Column(nullable = false, updatable = false)
    private Long orderId;

    @Column(nullable = false)
    private PaymentStatus status;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Collection<Transaction> transactions;

}
