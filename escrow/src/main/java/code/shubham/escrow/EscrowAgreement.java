package code.shubham.escrow;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntity;
import code.shubham.models.escrow.ReceiverDetails;
import code.shubham.models.escrow.SenderDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "escrow_agreements")
public class EscrowAgreement extends BaseAbstractAuditableEntity {
    private String agreementUrl;
    private BigDecimal amount;

    private ReceiverDetails receiverDetails;
    private SenderDetails senderDetails;

    private boolean isSignedByReceiver;

    private String signatureToken; // ttl for 15 mins
    private Date signatureValidTill;
    private String paymentOrderId;
    private boolean isFullFilled;
    private boolean isFundSettled;
    private boolean isAgreementClosed;
}
