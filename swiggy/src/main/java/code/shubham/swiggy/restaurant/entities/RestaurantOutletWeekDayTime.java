package code.shubham.swiggy.restaurant.entities;

import code.shubham.swiggy.restaurant.constants.enums.Day;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import code.shubham.swiggy.commons.entities.base.BaseAbstractAuditableEntity;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.sql.Time;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant_outlet_week_day_time", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "day", "restaurant_outlet_id"
        })
})
public class RestaurantOutletWeekDayTime extends BaseAbstractAuditableEntity {

    private Time start;
    private Time end;

    @Enumerated
    private Day day;

    @ManyToOne
    @JoinColumn(name = "restaurant_outlet_id", referencedColumnName = "id", nullable = false)
    private RestaurantOutlet restaurantOutlet;

}
