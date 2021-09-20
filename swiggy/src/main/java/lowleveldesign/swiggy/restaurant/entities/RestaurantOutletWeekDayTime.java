package lowleveldesign.swiggy.restaurant.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.swiggy.commons.entities.base.BaseAbstractAuditableEntity;
import lowleveldesign.swiggy.restaurant.constants.enums.Day;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.sql.Time;
import java.time.Period;

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
