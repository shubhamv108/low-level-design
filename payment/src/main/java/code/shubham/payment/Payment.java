package code.shubham.payment;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntity;
import code.shubham.models.payment.CardInfo;
import code.shubham.models.payment.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment extends BaseAbstractAuditableEntity {
    private String channel;

    private String userId; // loggedInUser
    private String clientId;

    private PaymentMode paymentMode;

    //    @Convert(converter = HashMapConverter.class)
    private CardInfo cardInfo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastClientRequestTimestamp;
}
