package code.shubham.wallet;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntityWithApprover;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Date;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account extends BaseAbstractAuditableEntityWithApprover {
    public enum AccountType {
        SAVINGS, LOANS
    }

    public enum AccountStatus {
        ACTIVE,INACTIVE
    }

    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    private String currency;

    private String generalLedgerCode;

    private Integer productSchemeId;

    private Integer officeId;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    private Date openingDate;

    private Date closingDate;

    private Boolean blocked;
}
