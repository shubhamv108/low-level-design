package code.shubham.swiggy.restaurant.entities.fooditems;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import code.shubham.swiggy.commons.entities.base.BaseAbstractAuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "food_items", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "day", "restaurant_outlet_id"
        })
})
public class FoodItem extends BaseAbstractAuditableEntity {

    @Column(unique = true)
    private String name;

    private String description;

    private Long image_id;

}
