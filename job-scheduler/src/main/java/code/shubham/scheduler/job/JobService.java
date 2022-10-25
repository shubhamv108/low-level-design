package code.shubham.scheduler.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobService {

    private final JobRepository jobRepository;

    @Autowired
    public JobService(final JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job create(Job job) {
        return this.jobRepository.save(job);
    }

    public Optional<Job> setStatus(Status status, Integer jobId, Integer userId) {
        this.jobRepository.setStatusByIdAndUserId(status, jobId, userId);
        return this.jobRepository.findByIdAndUserId(userId, jobId);
    }
}
