package code.shubham.ratelimiter.service;

import code.shubham.commons.util.StringUtils;
import code.shubham.models.ratelimit.rules.IRule;
import code.shubham.models.ratelimit.rules.Plan;
import code.shubham.ratelimiter.proxy.RuleProxy;
import code.shubham.ratelimiter.service.ratelimiters.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class RateLimitService {

    private final RuleProxy ruleProxy;
    private final RateLimiter rateLimiterStrategy;

    @Autowired
    public RateLimitService(final RuleProxy ruleProxy,
                            final RateLimiter rateLimiterStrategy) {
        this.ruleProxy = ruleProxy;
        this.rateLimiterStrategy = rateLimiterStrategy;
    }

    public boolean allow(String key, Plan plan, String apiName,
                         String role, Integer userId,
                         boolean fetchFromDBIfNotFound,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        Optional<IRule> rulePlanAndApiName = this.ruleProxy.get(plan, apiName, null, null, fetchFromDBIfNotFound);
        if (!allowForRule(key, rulePlanAndApiName, response))
            return false;

        if (StringUtils.isNotEmpty(role)) {
            Optional<IRule> rulePlanAndApiNameAndRole = this.ruleProxy.get(plan, apiName, role, null, fetchFromDBIfNotFound);
            if (!allowForRule(key, rulePlanAndApiNameAndRole, response))
                return false;
        }

        if (userId != null) {
            Optional<IRule> rulePlanAndApiNameAndRoleAndUserId = this.ruleProxy.get(plan, apiName, role, userId, fetchFromDBIfNotFound);
            if (!allowForRule(key, rulePlanAndApiNameAndRoleAndUserId, response))
                return false;
        }

        return true;
    }

    private boolean allowForRule(String key, Optional<IRule> rule, HttpServletResponse response) {
        if (rule.isPresent())
            if (!this.rateLimiterStrategy.allow(key, rule.get(), response))
                return false;
        return true;
    }

    private void setHeaders(HttpServletResponse response, int limitPerMinute) {
        response.addHeader("X-Rate-Limit-Limit", String.valueOf(limitPerMinute));
    }
}
