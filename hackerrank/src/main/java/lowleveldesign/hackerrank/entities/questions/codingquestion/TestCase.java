package lowleveldesign.hackerrank.entities.questions.codingquestion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.commons.entities.base.BaseAbstractAuditableEntity;

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
@Table(name = "coding_question_test_cases")
public class TestCase extends BaseAbstractAuditableEntity {

    @Lob
    private String input;

    @Lob
    private String output;

    @ManyToOne
    @JoinColumn(name = "coding_question_id", referencedColumnName = "id", nullable = false)
    private CodingQuestion question;


}
