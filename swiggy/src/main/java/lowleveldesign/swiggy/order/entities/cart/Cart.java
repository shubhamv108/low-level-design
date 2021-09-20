package lowleveldesign.swiggy.order.entities.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.swiggy.commons.entities.Currency;
import lowleveldesign.swiggy.commons.entities.base.BaseAbstractAuditableEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carts")
public class Cart extends BaseAbstractAuditableEntity {
        @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        private Collection<CartItem> cartItems;

        private BigDecimal amount;
        @Enumerated(EnumType.STRING)
        private Currency currency;

        private Long restaurantOutletId;

        @Column(nullable = false, updatable = false)
        private Long userId;

        @Temporal(TemporalType.TIMESTAMP)
        private Date lastClearedAt;

        public void clear() {
                this.cartItems.clear();
                this.amount = null;
                this.currency = null;
                this.lastClearedAt = new Date(System.currentTimeMillis());
        }

}
