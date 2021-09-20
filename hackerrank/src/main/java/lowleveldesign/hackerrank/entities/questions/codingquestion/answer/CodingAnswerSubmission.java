package lowleveldesign.hackerrank.entities.questions.codingquestion.answer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.commons.entities.base.BaseAbstractAuditableEntity;
import lowleveldesign.hackerrank.entities.Candidate;
import lowleveldesign.hackerrank.entities.questions.codingquestion.ProgrammingLanguage;
import lowleveldesign.hackerrank.entities.questions.codingquestion.TestCodingQuestion;
import lowleveldesign.hackerrank.entities.questions.codingquestion.TestCodingQuestionTestCase;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coding_answer_submissions")
public class CodingAnswerSubmission extends BaseAbstractAuditableEntity {

    @Lob
    private String code;

    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "id", nullable = false)
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "coding_question_id", referencedColumnName = "id", nullable = false)
    private TestCodingQuestion question;

    private ProgrammingLanguage language;

    @OneToMany(fetch = FetchType.LAZY)
    private Collection<TestCodingQuestionTestCase> passedTestCases;



}
