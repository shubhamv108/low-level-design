package code.shubham.blob;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntity;
import code.shubham.queue.models.TaskPriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Queue;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "blob_associations")
public class BlobAssociation extends BaseAbstractAuditableEntity {
    private String service;
    private String application;
    private String collection;
    private String subRef;

    @ManyToOne
    @JoinColumn(name = "blob_id", referencedColumnName = "id", nullable = false)
    private Blob blob;
}
