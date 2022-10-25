package code.shubham.stores.location.entities;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;

@Data
@Table("location_logs")
public class LocationLogs {

    @PrimaryKeyColumn(name = "query", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.TEXT)
    private String user_id;

    @PrimaryKeyColumn(name = "message_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.BIGINT)
    private Long messageId;

    @PrimaryKeyColumn(name = "timestamp", ordinal = 0, type = PrimaryKeyType.CLUSTERED)
    @Column("timestamp")
    private Date timestamp;

    @CassandraType(type = CassandraType.Name.TEXT)
    @Column("geo_location")
    private String geoLocation;
}