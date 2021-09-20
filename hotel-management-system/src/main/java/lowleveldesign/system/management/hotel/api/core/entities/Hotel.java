package lowleveldesign.system.management.hotel.api.core.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.system.management.hotel.api.core.entities.base.BaseAbstractAuditableEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotels")
public class Hotel extends BaseAbstractAuditableEntity {

    private String name;

    @ManyToOne
    private Address address;

    @OneToMany
    private List<User> owners;

    @OneToMany
    private List<Room> rooms;

    @ManyToMany
    private List<HotelFacility> facilities;

    @ManyToMany
    @JoinTable(name = "hotels__coupons__mapping",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id"))
    private Set<Coupon> coupons;

}
