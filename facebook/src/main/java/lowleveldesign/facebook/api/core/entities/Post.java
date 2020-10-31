package lowleveldesign.facebook.api.core.entities;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.UUID;

@Data
@Table("posts")
public class Post {

    @PrimaryKeyColumn(name = "person_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID personId;

    @CassandraType(type = CassandraType.Name.UUID)
    private UUID postId;

    @PrimaryKeyColumn(name = "post_timestamp", type = PrimaryKeyType.CLUSTERED)
    private Date postTimestamp;



}
