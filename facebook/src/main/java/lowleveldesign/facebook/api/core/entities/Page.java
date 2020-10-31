package lowleveldesign.facebook.api.core.entities;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@Table("pages")
public class Page {

    @PrimaryKeyColumn(name = "page_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID id;

    @Column("page_name")
    private String name;

    @Column
    private String avatarUrl;

    @Column
    private String backgroundUrl;

    @Column
    private String thumbnailUrl;

    private String pageSummary;

}
