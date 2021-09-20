package lowleveldesign.hackerrank.entities.questions.multiplechiocequestions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.commons.entities.base.BaseAbstractAuditableEntity;
import lowleveldesign.hackerrank.entities.questions.writtenquestion.WrittenQuestion;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Time;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "test_multiple_choice_questions")
public class TestMultipleChoiceQuestion extends BaseAbstractAuditableEntity {

    private Long score;
    private Time time;
    private Long maxAnswerLength;

    @OneToOne
    private MultipleChoiceQuestion question;

}
