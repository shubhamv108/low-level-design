package code.shubham.system.offer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "user__offer_plan__mapping")
public class UserOfferPlan {

    Integer userId;
    Integer offerPlanId;
    Date createdAt;

    @Column(nullable = true)
    Date expiryDate;

}
