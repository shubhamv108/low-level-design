package code.shubham.queue;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "queues", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name"
        })
})
public class Queue extends BaseAbstractAuditableEntity {
    @Column(length = 128, nullable = false, updatable = false)
    private String name;
}
