package code.shubham.swiggy.delivery_partner.entities;

import code.shubham.swiggy.restaurant.entities.RestaurantOutlet;
import code.shubham.swiggy.restaurant.entities.RestaurantOutletRatingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import code.shubham.swiggy.commons.entities.base.BaseAbstractAuditableEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "delivery_executive_aggregated_ratings", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "rating_type_id",
                "restaurant_outlet_id"
        })
})
public class DeliveryExecutiveRating extends BaseAbstractAuditableEntity {

    private Long total = 0L;
    private Long count = 0L;

    @OneToOne
    @JoinColumn(name = "rating_type_id", referencedColumnName = "id", nullable = false)
    private RestaurantOutletRatingType restaurantOutletRatingType;

    @ManyToOne
    @JoinColumn(name = "restaurant_outlet_id", referencedColumnName = "id", nullable = false)
    private RestaurantOutlet restaurantOutlet;

}
