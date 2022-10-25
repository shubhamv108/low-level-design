package code.shubham.analytics.logs.entities;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;

@Data
@Table("query_logs")
public class QueryLog {

//    @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
//    @CassandraType(type = CassandraType.Name.UUID)
//    private UUID id;

    @PrimaryKeyColumn(name = "query", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.TEXT)
    private String query;

    @PrimaryKeyColumn(name = "bucket_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.TEXT)
    private String bucketId;

    @PrimaryKeyColumn(name = "timestamp", ordinal = 0, type = PrimaryKeyType.CLUSTERED)
    @Column("timestamp")
    private Date timestamp;
}
