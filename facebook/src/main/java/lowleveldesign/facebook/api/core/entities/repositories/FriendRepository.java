package lowleveldesign.facebook.api.core.entities.repositories;

import lowleveldesign.facebook.api.core.entities.Friend;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FriendRepository extends CassandraRepository<Friend, UUID> {
}
