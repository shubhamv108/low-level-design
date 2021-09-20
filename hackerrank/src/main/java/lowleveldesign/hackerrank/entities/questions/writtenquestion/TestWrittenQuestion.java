package lowleveldesign.hackerrank.entities.questions.writtenquestion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.commons.entities.base.BaseAbstractAuditableEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Time;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "test_written_questions")
public class TestWrittenQuestion extends BaseAbstractAuditableEntity {

    private Long score;
    private Time time;
    private Long maxAnswerLength;

    @OneToOne
    private WrittenQuestion question;

}
