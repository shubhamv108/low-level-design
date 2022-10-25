package code.shubham.commons.entities.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseAbstractAuditableEntityWithApprover implements Serializable {

    private static final long serialVersionUID = -132464313958782577L;

    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedOn;
    private String approvedBy;
}