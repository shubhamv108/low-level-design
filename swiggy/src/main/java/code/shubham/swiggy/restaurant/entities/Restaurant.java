package code.shubham.swiggy.restaurant.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import code.shubham.swiggy.commons.entities.base.BaseAbstractAuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurants")
public class Restaurant extends BaseAbstractAuditableEntity {

    private String name;
    private String description;

    @Column(length = 10)
    private String contactNumber;

    @ManyToOne
    @JoinColumn(name = "restaurant_status_id", referencedColumnName = "id")
    private RestaurantStatus restaurantStatus;

    @OneToMany(mappedBy = "restaurantOutlet")
    private Collection<RestaurantOutlet> restaurantOutlets;

}
