package code.shubham.ratelimit.rules.load;

import code.shubham.models.ratelimit.rules.IRule;
import code.shubham.ratelimit.rules.service.RuleDAOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RuleLoader {

    private final RuleDAOService ruleService;

    @Autowired
    public RuleLoader(final RuleDAOService ruleService) {
        this.ruleService = ruleService;
    }

    public void load() {
        List<IRule> rules = this.ruleService.findAllWhereUserIdIsNull();
        this.ruleService.cache(rules);
    }
}
