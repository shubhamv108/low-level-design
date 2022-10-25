package code.shubham.batch;

import org.springframework.stereotype.Service;

@Service
public class BatchJobExecutionService {

    private final BatchExecutionRepository batchExecutionRepository;

    public BatchJobExecutionService(final BatchExecutionRepository batchExecutionRepository) {
        this.batchExecutionRepository = batchExecutionRepository;
    }

    public BatchJobExecution save(BatchJobExecution execution) {
        return this.batchExecutionRepository.save(execution);
    }

}
