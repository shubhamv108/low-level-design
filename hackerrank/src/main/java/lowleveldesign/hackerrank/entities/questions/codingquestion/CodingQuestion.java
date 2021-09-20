package lowleveldesign.hackerrank.entities.questions.codingquestion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.hackerrank.entities.questions.DifficultyLevel;
import lowleveldesign.hackerrank.entities.questions.Question;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Collection;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coding_questions")
public class CodingQuestion {

    @OneToOne
    @Column(nullable = false)
    private Question question;

    private String sampleInput;
    private String sampleOutput;

    @Enumerated
    private DifficultyLevel level;

    private Long points;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "question")
    @JoinTable(name = "coding_question__allowed_language__mapping",
            joinColumns = @JoinColumn(name = "coding_question_id"),
            inverseJoinColumns = @JoinColumn(name = "allowed_language_id"))
    private Collection<ProgrammingLanguage> programmingLanguages;
}