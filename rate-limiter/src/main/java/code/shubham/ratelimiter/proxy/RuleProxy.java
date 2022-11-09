package code.shubham.ratelimiter.proxy;

import code.shubham.client.ratelimitrules.RateLimitRulesClient;
import code.shubham.library.ratelimit.cache.RuleCache;
import code.shubham.models.ratelimit.rules.GetRule;
import code.shubham.models.ratelimit.rules.IRule;
import code.shubham.models.ratelimit.rules.Plan;
import code.shubham.models.ratelimit.rules.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RuleProxy {

    private final RateLimitRulesClient client;
    private final RuleCache cache;

    @Autowired
    public RuleProxy(final RateLimitRulesClient client,
                     final RuleCache cache) {
        this.client = client;
        this.cache = cache;
    }

    public Optional<IRule> get(Plan plan, String apiName, String role, Integer userId, boolean fetchFromRuleServiceIfNotFound) {
        Optional<IRule> rule = this.cache.get(plan, apiName, role, userId);
        if (rule.isPresent())
            return rule;
        if (!fetchFromRuleServiceIfNotFound)
            return Optional.empty();
        return Optional.ofNullable(this.client.getRule(
                GetRule.Request.QueryParams.builder().
                        plan(plan).
                        apiName(apiName).
                        role(role).
                        build(),
                userId));
    }

    public void loadCache() {
        this.cache.put(this.getCached());
    }

    private List<? extends IRule> getCached() {
        return this.client.getCached();
    }
}
