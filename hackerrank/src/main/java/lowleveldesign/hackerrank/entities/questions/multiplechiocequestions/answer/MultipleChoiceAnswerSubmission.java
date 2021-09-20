package lowleveldesign.hackerrank.entities.questions.multiplechiocequestions.answer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.commons.entities.base.BaseAbstractAuditableEntity;
import lowleveldesign.hackerrank.entities.Candidate;
import lowleveldesign.hackerrank.entities.questions.multiplechiocequestions.MultipleChoiceQuestionChoice;
import lowleveldesign.hackerrank.entities.questions.multiplechiocequestions.TestMultipleChoiceQuestion;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "multiple_choice_answer_submissions")
public class MultipleChoiceAnswerSubmission extends BaseAbstractAuditableEntity {

    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "id", nullable = false)
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "test_multiple_choice_question_id", referencedColumnName = "id", nullable = false)
    private TestMultipleChoiceQuestion multipleChoiceQuestion;

    private MultipleChoiceQuestionChoice answer;
}
