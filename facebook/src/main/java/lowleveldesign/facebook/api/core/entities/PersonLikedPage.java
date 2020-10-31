package lowleveldesign.facebook.api.core.entities;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@Table("person__liked__pages")
public class PersonLikedPage {

    @PrimaryKeyColumn(name = "person_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @Column("person_id")
    private UUID personId;

    @Column("page_id")
    private UUID pageId;

    @Column("page_name")
    private String pageName;

    @Column("page_thumbnail_url")
    private String pageThumbnailUrl;

}
