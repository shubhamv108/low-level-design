package code.shubham.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BatchJobService {

    private final BatchJobRepository repository;

    @Autowired
    public BatchJobService(final BatchJobRepository repository) {
        this.repository = repository;
    }

    public BatchJob save(BatchJob job) {
        return this.repository.save(job);
    }

    public Optional<BatchJob> getByJobCode(String code) {
        return this.repository.findByCode(code);
    }

    public boolean updateStatusToStartedIfStatusNotStartedOrStoppedByJobId(Integer id) {
        return 1 == this.repository.updateStatusToStartedIfStatusNotStartedOrStoppedByJobId(id);
    }
}
