package lowleveldesign.swiggy.restaurant.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.swiggy.commons.entities.base.BaseAbstractAuditableEntity;
import lowleveldesign.swiggy.commons.entities.base.address.Address;
import lowleveldesign.swiggy.restaurant.entities.fooditems.RestaurantOutletFoodItem;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Collection;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant_outlets")
public class RestaurantOutlet extends BaseAbstractAuditableEntity {

    private String outletAddress;

    @Column(length = 10)
    private String contactNumber;

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address;

    @OneToMany(mappedBy = "restaurantOutlet", fetch = FetchType.LAZY)
    private Collection<RestaurantOutletWeekDayTime> weekDayTimes;

    @ManyToOne
    @JoinColumn(name = "restaurant_outlet_status_id", referencedColumnName = "id")
    private RestaurantOutletStatus restaurantOutletStatus;

    @OneToMany(mappedBy = "restaurantOutlet", fetch = FetchType.LAZY)
    private Collection<RestaurantOutletFoodItem> foodItems;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id", nullable = false)
    private Restaurant restaurant;

    @OneToMany(mappedBy = "restaurantOutlet", fetch = FetchType.LAZY)
    private Collection<CustomerRestaurantOutletRating> customerRestaurantOutletRatings;

    @OneToOne(mappedBy = "restaurantOutlet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "average_cost_id", referencedColumnName = "id", nullable = false)
    private AverageCost averageCost;

}
