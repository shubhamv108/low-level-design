package code.shubham.blob;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "blobs")
public class Blob extends BaseAbstractAuditableEntity {
    private String store;
    private String bucketName;
    private String checksum;
    private String ownerService;
    private String signedUrl;
    private Integer userId;

    @Enumerated(EnumType.STRING)
    private BlobStatus status;

    @OneToMany(mappedBy = "blob", fetch = FetchType.LAZY)
    private Collection<BlobAssociation> associations;
}
