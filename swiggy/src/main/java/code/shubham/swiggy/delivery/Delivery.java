package code.shubham.swiggy.delivery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import code.shubham.swiggy.commons.entities.base.BaseAbstractAuditableEntity;
import code.shubham.swiggy.delivery_partner.entities.DeliveryExecutive;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "deliveries")
public class Delivery extends BaseAbstractAuditableEntity {

    @Column(nullable = false, updatable = false)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "delivery_status_id", referencedColumnName = "id", nullable = false)
    private DeliveryStatus status;

    @OneToOne
    @JoinColumn(name = "delivery_executive_id", referencedColumnName = "id", nullable = false, unique = true)
    private DeliveryExecutive deliveryExecutive;


}
