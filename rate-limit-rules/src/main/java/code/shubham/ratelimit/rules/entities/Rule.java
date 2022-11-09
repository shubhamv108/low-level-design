package code.shubham.ratelimit.rules.entities;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntity;
import code.shubham.models.ratelimit.rules.IRule;
import code.shubham.models.ratelimit.rules.Plan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.concurrent.TimeUnit;

@Builder
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rules", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "plan", "apiName", "role", "userId" })
})
public class Rule extends BaseAbstractAuditableEntity implements IRule {

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private Plan plan;

    @Column(nullable = true)
    private String apiName;

    @Column(nullable = true)
    private String role;

    @Column(nullable = true)
    private Integer userId;

    @Column(nullable = false)
    private Integer allowed;

    @Column(nullable = false)
    private Long duration;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TimeUnit timeUnit;
}
