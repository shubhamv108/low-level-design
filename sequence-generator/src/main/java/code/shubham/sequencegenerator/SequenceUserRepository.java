package code.shubham.sequencegenerator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceUserRepository extends JpaRepository<SequenceUser, Integer> {
}
