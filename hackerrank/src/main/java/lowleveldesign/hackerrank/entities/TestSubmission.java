package lowleveldesign.hackerrank.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.commons.entities.base.BaseAbstractAuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "test_submissions", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "test_id", "candidate_id"
        })
})
public class TestSubmission extends BaseAbstractAuditableEntity {

    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "id", nullable = false)
    private Candidate candidate;

    @OneToOne
    @Column(name = "test_id", nullable = false)
    private Test test;

    private Long score;

}
