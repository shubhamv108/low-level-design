package code.shubham.tinyurl;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "short_urls")
public class ShortURL extends BaseAbstractAuditableEntity {

    @Column(unique = true, length = 8, nullable = false, updatable = false)
    private String shortUrl;

    @Lob
    private String url;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryAt;

    private Integer userId;
}
