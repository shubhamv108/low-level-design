package code.shubham.scheduler.job;

import code.shubham.commons.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/job")
public class JobController {

    private final JobService service;

    @Autowired
    public JobController(final JobService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> add(
            @RequestHeader("userId") Integer userId,
            @RequestBody Job job) {
        var newJob = this.service.create(job);
        return ResponseUtils.getDataResponseEntity(HttpStatus.CREATED, newJob);
    }

    @PatchMapping
    public ResponseEntity<?> edit(
            @RequestHeader("userId") Integer userId,
            @RequestBody Job job) {
        return null;
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<?> remove(
            @RequestHeader("userId") Integer userId,
            @PathVariable("jobId") Integer jobId) {
        var job = this.service.setStatus(Status.DEACTIVATED, jobId, userId);
        return null;
    }

    @PostMapping("/{jobId}")
    public ResponseEntity<?> suspend(
            @RequestHeader("userId") Integer userId,
            @PathVariable("jobId") Integer jobId) {
        var job = this.service.setStatus(Status.SUSPENDED, jobId, userId);
        return null;
    }
}
