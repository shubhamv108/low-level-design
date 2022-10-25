package code.shubham.stores.location.entities;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;

@Data
@Table("bucket_id_sequence_generator")
public class BucketIdSequenceGenerator {
    @PrimaryKeyColumn(name = "timestamp", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @Column("timestamp")
    private Date date;

    @PrimaryKeyColumn(name = "timestamp", ordinal = 0, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = CassandraType.Name.COUNTER)
    private Long bucketId;

}
