package lowleveldesign.hackerrank.entities.questions.writtenquestion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.commons.entities.base.BaseAbstractAuditableEntity;
import lowleveldesign.hackerrank.entities.questions.Question;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "written_questions")
public class WrittenQuestion extends BaseAbstractAuditableEntity {

    @OneToOne
    @Column(nullable = false)
    private Question question;

    private Long maxAnswerLength;

}
