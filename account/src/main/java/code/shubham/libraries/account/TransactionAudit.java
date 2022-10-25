package code.shubham.libraries.account;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntityWithApprover;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="transaction_audit")
public class TransactionAudit extends BaseAbstractAuditableEntityWithApprover {

    public enum TransactionAuditStatus {
        INITIATED, FAIL, SUCCESS
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String transactionType;
    private String transactionSubType;
    private String referenceCode;
    private String accountingReferenceCode;
    private String clientCode;
    private String clientReferenceCode;
    private String externalReferenceCode;
    private String partnerReferenceCode;
    private String stan;
    private String channel;
    private String customerIdentifierType;
    private String customerIdentifierValue;
    private BigDecimal transactionAmount;
    private String initialStatus;
    private String status;
    private String partnerStatus;
    private String partnerReversalStatus;
    private String remarks;
    private Date reversalDate;
    private String reversalAccountingReferenceCode;
    private String partnerReconStatus;
    private String partnerReconDate;
    private String partnerReconFileName;
    private String accountingReconStatus;
    private Date accountingReconDate;

    //remitter  id
    private String freeField1;
    //bene  id
    private String freeField3;
    //ifsc
    private String freeField4;
    //account num
    private String freeField5;
}
