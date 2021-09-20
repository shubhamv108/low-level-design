package lowleveldesign.hackerrank.entities.questions.multiplechiocequestions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.hackerrank.entities.questions.multiplechiocequestions.MultipleChoiceQuestion;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "multiple_choice_questions_choices")
public class MultipleChoiceQuestionChoice {

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "choices", nullable = false)
    private MultipleChoiceQuestion question;

}
