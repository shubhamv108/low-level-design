package code.shubham.wallet;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transaction_details")
public class TransactionDetail {

    private static final long serialVersionUID = 3313320436552457810L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long transactionId;
    private Integer originatingOfficeId;
    private Integer officeId;
    private String accountNumber;

    @Temporal(TemporalType.TIMESTAMP)
    private Date valueDate;
    private String currency;
    private BigDecimal netAmount;

    @Enumerated(EnumType.STRING)
    private CrDrIndicator crDrIndicator;
    private BigDecimal balanceAfterTransaction;
    private String narration;
}
