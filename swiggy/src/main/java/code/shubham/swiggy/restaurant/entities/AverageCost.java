package code.shubham.swiggy.restaurant.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import code.shubham.swiggy.commons.entities.base.BaseAbstractAuditableEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer_ratings")
public class AverageCost extends BaseAbstractAuditableEntity {

    private Long total = 0L;
    private Long count = 0L;

    @OneToOne
    @JoinColumn(name = "restaurant_outlet_id", referencedColumnName = "id", nullable = false, unique = true)
    private RestaurantOutlet restaurantOutlet;

}
