package code.shubham.ratelimit.rules.repository;

import code.shubham.models.ratelimit.rules.Plan;
import code.shubham.ratelimit.rules.entities.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Integer> {
    Optional<Rule> findByPlanAndApiNameAndRoleAndUserId(Plan plan, String apiName, String role, Integer userId);

    @Query(nativeQuery = true, value = "SELECT * FROM rules WHERE user_id IS NULL;")
    List<Rule> findAllWhereUserIdIsNull();
}
