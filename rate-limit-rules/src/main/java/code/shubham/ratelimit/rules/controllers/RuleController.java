package code.shubham.ratelimit.rules.controllers;

import code.shubham.commons.util.ResponseUtils;
import code.shubham.models.ratelimit.rules.IRule;
import code.shubham.models.ratelimit.rules.Plan;
import code.shubham.ratelimit.rules.service.RuleDAOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
public class RuleController {

    private final RuleDAOService service;

    @Autowired
    public RuleController(final RuleDAOService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> get(
            @RequestHeader(value = "userId", required = false) Integer userId,
            @RequestParam(value = "plan", required = false, defaultValue = "FREE") Plan plan,
            @RequestParam(value = "apiName", required = false) String apiName,
            @RequestParam(value = "role", required = false) String role) {
        Optional<IRule> rule = this.service.get(plan, apiName, role, userId);
        return ResponseUtils.getDataResponseEntity(HttpStatus.OK.value(), rule.orElse(null));
    }

    @GetMapping("/applicable")
    public ResponseEntity<?> getApplicable(
            @RequestHeader(value = "userId", required = false) Integer userId,
            @RequestParam(value = "plan", required = false, defaultValue = "FREE") Plan plan,
            @RequestParam(value = "apiName", required = false) String apiName,
            @RequestParam(value = "role", required = false) String role) {
        Optional<IRule> rulePlanAndApiName = this.service.get(plan, apiName, null, null);
        Optional<IRule> rulePlanAndApiNameAndRole = this.service.get(plan, apiName, role, null);
        Optional<IRule> rulePlanAndApiNameAndRoleAndUserId = this.service.get(plan, apiName, role, userId);
        List<IRule> rules = new ArrayList<>(Arrays.asList(rulePlanAndApiName, rulePlanAndApiNameAndRole, rulePlanAndApiNameAndRoleAndUserId)).
                stream().
                filter(Optional::isPresent).
                map(Optional::get).
                collect(Collectors.toList());
        return ResponseUtils.getDataResponseEntity(
                HttpStatus.OK.value(),
                new HashMap<>() {
                    {
                        put("rules", rules);
                    }
                });
    }

    @GetMapping("/cached")
    public ResponseEntity<?> getCached() {
        List<IRule> rules = this.service.findAllWhereUserIdIsNull();
        return ResponseUtils.getDataResponseEntity(
                HttpStatus.OK.value(),
                new LinkedHashMap() {
                    {
                        put("rules", rules);
                    }
                });
    }
}
