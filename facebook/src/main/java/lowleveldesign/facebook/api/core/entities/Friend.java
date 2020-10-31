package lowleveldesign.facebook.api.core.entities;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table("friends")
public class Friend {

    @PrimaryKeyColumn(name = "person_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @Column("person_id")
    private UUID personId;

    @Column("friend_id")
    private UUID friendId;

    @Column("friend_name")
    private String friendName;

    @Column("friend_thumbnail_url")
    private String friendThumbnailUrl;

}
