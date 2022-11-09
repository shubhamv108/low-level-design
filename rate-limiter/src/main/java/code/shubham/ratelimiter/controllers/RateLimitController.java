package code.shubham.ratelimiter.controllers;

import code.shubham.models.ratelimit.rules.Plan;
import code.shubham.ratelimiter.service.RateLimitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/v1")
public class RateLimitController {

    private final RateLimitService service;

    @Autowired
    public RateLimitController(final RateLimitService service) {
        this.service = service;
    }

    @PostMapping("/allow/{key}")
    public void allow(
            @RequestHeader(value = "userId", required = false) Integer userId,
            @RequestParam(value = "apiName", required = false) String apiName,
            @RequestParam(value = "role", required = false) String role,
            @RequestParam(value = "fetchFromDbIfNotCached", required = false, defaultValue = "false") boolean fetchFromDbIfNotCached,
            @PathVariable(value = "key", required = true) String key,
            HttpServletRequest request,
            HttpServletResponse response) {
        try {
            Plan plan = Plan.get(key);
            boolean result = this.service.allow(key, plan, apiName, role, userId, fetchFromDbIfNotCached, request, response);
            if (!result) {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                return;
            }
        } catch (Exception exception) {
            log.error("Something went wrong: ", exception.getMessage());
        } finally {
            response.setStatus(HttpStatus.OK.value());
        }
    }

    @PostMapping
    public void gateway(
            @RequestHeader(value = "userId", required = false) Integer userId,
            @RequestParam(value = "apiName", required = false) String apiName,
            @RequestParam(value = "role", required = false) String role,
            @RequestParam(value = "fetchFromDbIfNotCached", required = false, defaultValue = "false") boolean fetchFromDbIfNotCached,
            @PathVariable(value = "key", required = true) String key,
            HttpServletRequest request,
            HttpServletResponse response) {
        Plan plan = Plan.get(key);
        boolean result = this.service.allow(key, plan, apiName, role, userId, fetchFromDbIfNotCached, request, response);
        if (!result) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            return;
        }
        // forward to service
    }

}
