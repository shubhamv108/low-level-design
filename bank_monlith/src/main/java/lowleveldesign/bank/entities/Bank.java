package lowleveldesign.bank.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.bank.entities.base.BaseAbstractAuditableEntity;

import javax.persistence.UniqueConstraint;
import javax.persistence.Entity;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "banks", uniqueConstraints = {
        @UniqueConstraint(columnNames = {

        })
})
public class Bank extends BaseAbstractAuditableEntity {

    private String name;

}
