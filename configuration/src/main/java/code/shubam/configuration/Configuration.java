package code.shubam.configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.commons.entities.base.BaseAbstractAuditableEntity;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "configurations", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name",
                "service"
        })
})
public class Configuration extends BaseAbstractAuditableEntity {
    @Column(nullable = false, length = 24)
    private String serviceName = "*";

    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false, length = 256)
    private String value;
}
