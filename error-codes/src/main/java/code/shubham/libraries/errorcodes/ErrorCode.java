package code.shubham.libraries.errorcodes;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "error_codes")
public class ErrorCode extends BaseAbstractAuditableEntity {

    @Column(unique = true)
    private String errorCode;

    @Lob
    private String errorMessage;

}
