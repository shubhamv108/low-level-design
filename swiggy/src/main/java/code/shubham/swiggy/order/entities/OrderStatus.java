package code.shubham.swiggy.order.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_statuses")
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * PENDING_PAYMENT
     * RECEIVED_PAYMENT
     * AWAITING_RESTAURANT_TO_ACCEPT_ORDER
     * RESTAURANT_ACCEPTED_ORDER
     * DELIVERY_CREATED
     * CANCELLED
     */
    @NaturalId
    @Column(unique = true, nullable = false, length = 12)
    private String name;

}
