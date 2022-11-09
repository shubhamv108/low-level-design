package code.shubham.models.ratelimit.rules;

import lombok.Getter;

import java.util.concurrent.TimeUnit;

@Getter
public class Rule implements IRule {
    private Plan plan;
    private String apiName;
    private String role;
    private Integer userId;
    private Integer allowed;
    private Long duration;
    private TimeUnit timeUnit;
}
