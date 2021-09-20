package lowleveldesign.hackerrank.entities.questions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.commons.entities.base.BaseAbstractAuditableEntity;
import lowleveldesign.hackerrank.entities.Test;
import lowleveldesign.hackerrank.entities.questions.codingquestion.TestCodingQuestion;
import lowleveldesign.hackerrank.entities.questions.multiplechiocequestions.TestMultipleChoiceQuestion;
import lowleveldesign.hackerrank.entities.questions.writtenquestion.TestWrittenQuestion;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Time;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "test_questions")
public class TestQuestion extends BaseAbstractAuditableEntity {

    private Long score;
    private Time time;
    private Long questionNumberForTest;

    @OneToOne
    private TestMultipleChoiceQuestion multipleChoiceQuestion;

    @OneToOne
    private TestWrittenQuestion writtenQuestion;

    @OneToOne
    private TestCodingQuestion codingQuestions;

    @OneToOne
    @JoinColumn(name = "test_id", referencedColumnName = "id")
    private Test test;

}
