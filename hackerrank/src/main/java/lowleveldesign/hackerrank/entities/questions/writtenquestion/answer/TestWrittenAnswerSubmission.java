package lowleveldesign.hackerrank.entities.questions.writtenquestion.answer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.commons.entities.base.BaseAbstractAuditableEntity;
import lowleveldesign.hackerrank.entities.Candidate;
import lowleveldesign.hackerrank.entities.questions.multiplechiocequestions.TestMultipleChoiceQuestion;
import lowleveldesign.hackerrank.entities.questions.writtenquestion.TestWrittenQuestion;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "test_written_answer_submissions")
public class TestWrittenAnswerSubmission extends BaseAbstractAuditableEntity {

    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "id", nullable = false)
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "test_written_question_id", referencedColumnName = "id", nullable = false)
    private TestWrittenQuestion testWrittenQuestion;

    @Lob
    private String answer;

    private Long evaluatedScore;
}
