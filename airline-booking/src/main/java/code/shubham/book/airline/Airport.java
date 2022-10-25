package code.shubham.book.airline;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "airports")
public class Airport extends BaseAbstractAuditableEntity {

    private String name;

    @Column(unique = true, nullable = false)
    private String code;

    private String address;
    private String geoLocation;
}
