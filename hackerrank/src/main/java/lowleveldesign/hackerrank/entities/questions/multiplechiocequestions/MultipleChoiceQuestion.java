package lowleveldesign.hackerrank.entities.questions.multiplechiocequestions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.hackerrank.entities.questions.Question;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Collection;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "multiple_choice_questions")
public class MultipleChoiceQuestion {

    @OneToOne
    @Column(nullable = false)
    private Question question;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "question")
    private Collection<MultipleChoiceQuestionChoice> choices;

    private MultipleChoiceQuestionChoice answer;

}
