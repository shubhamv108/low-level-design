package lowleveldesign.hackerrank.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.commons.entities.base.BaseAbstractAuditableEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contests")
public class Contest extends BaseAbstractAuditableEntity {

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "contest", fetch = FetchType.LAZY)
    private Test test;

}
