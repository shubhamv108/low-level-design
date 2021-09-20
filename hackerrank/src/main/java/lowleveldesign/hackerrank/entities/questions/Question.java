package lowleveldesign.hackerrank.entities.questions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.commons.entities.base.BaseAbstractAuditableEntity;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questions")
public class Question extends BaseAbstractAuditableEntity {

    @Lob
    private String statement;

}
