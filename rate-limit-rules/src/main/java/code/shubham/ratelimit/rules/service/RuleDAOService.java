package code.shubham.ratelimit.rules.service;

import code.shubham.library.ratelimit.cache.RuleCache;
import code.shubham.models.ratelimit.rules.IRule;
import code.shubham.models.ratelimit.rules.Plan;
import code.shubham.ratelimit.rules.entities.Rule;
import code.shubham.ratelimit.rules.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RuleDAOService {

    private final RuleCache cache;
    private final RuleRepository repository;

    @Autowired
    public RuleDAOService(final RuleCache cache,
                          final RuleRepository repository) {
        this.cache = cache;
        this.repository = repository;
    }


    public Optional<IRule> get(Plan plan, String apiName, String role, Integer userId) {
        if (plan == null)
            plan = Plan.FREE;

        Optional<IRule> rule = this.cache.get(plan, apiName, role, userId);
        if (rule.isPresent())
            return rule;
        Optional<Rule> ruleOptional =
                this.repository.findByPlanAndApiNameAndRoleAndUserId(plan, apiName, role, userId);
        return ruleOptional.map(e -> (IRule) e);
    }

    public List<IRule> findAllWhereUserIdIsNull() {
        List<Rule> rules = this.repository.findAllWhereUserIdIsNull();
        return rules.stream().map(rule -> (IRule) rule).collect(Collectors.toList());
    }

    public List<Boolean> cache(List<IRule> rules) {
        return rules.stream().map(this::cache).collect(Collectors.toList());
    }

    public boolean cache(IRule rule) {
        return this.cache.set(rule);
    }
}
