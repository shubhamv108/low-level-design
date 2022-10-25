package code.shubham.pastebin;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "pastes")
public class Paste extends BaseAbstractAuditableEntity {

    @Column(length = 7, unique = true, nullable = false, updatable = false)
    String key; // 7 bytes
    Integer blobId; // 32 bytes
    String url; // 255 bytes
    Date expiryAt; // 5 bytes

    @Column(nullable = true)
    Integer userId; // 32 bytes
}
