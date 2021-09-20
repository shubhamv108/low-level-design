package lowleveldesign.hackerrank.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.commons.entities.base.BaseAbstractAuditableEntity;
import lowleveldesign.hackerrank.entities.questions.TestQuestion;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Time;
import java.util.Collection;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tests")
public class Test extends BaseAbstractAuditableEntity {

    private Time time;

    private Date startTime;
    private Date endTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "test", cascade = CascadeType.ALL)
    private Collection<TestQuestion> testQuestions;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "test")
    @Column(updatable = false, unique = true)
    private Contest contest;

}
