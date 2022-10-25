package code.shubham.system.offer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "plan_offers",
        indexes = { @Index(name = "NonUK_plan_id", columnList="plan_id",  unique = false)})
public class PlanOffer {
    Integer id;
    BigDecimal offAmount;

    @Column(nullable = false)
    Integer planId;

    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;
}
