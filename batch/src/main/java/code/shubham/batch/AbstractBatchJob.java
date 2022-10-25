package code.shubham.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

@Slf4j
public abstract class AbstractBatchJob {

    private final BatchJobService batchJobService;
    private final BatchJobExecutionService batchJobExecutionService;

    @Autowired
    protected AbstractBatchJob(final BatchJobService batchJobService,
                               final BatchJobExecutionService batchJobExecutionService) {
        this.batchJobService = batchJobService;
        this.batchJobExecutionService = batchJobExecutionService;
    }

    public void doExecute(final Map<String, String> properties) {
        var batchJobOptional = this.batchJobService.getByJobCode(properties.get(Constants.JOB_CODE_KEY));
        String errorMessage = null;
        if (batchJobOptional.isEmpty()) {
            log.error("No details found in master for the job: " + properties.get(Constants.JOB_CODE_KEY));
            return;
        }
        var batchJob = batchJobOptional.get();

        try {
            if (Status.STARTED.equals(batchJob.getLastRunStatus())) {
                log.error("Previous execution has not finished yet for the job: " + properties.get(Constants.JOB_CODE_KEY));
                return;
            }

            if (Status.STARTED.equals(batchJob.getLastRunStatus())) {
                log.info("Batch execution is currently stopped. Please enable the batch to start execution.");
                return;
            }

            this.logBatchStart(batchJob);
            properties.put(Constants.CRON_KEY, batchJob.getCron());

            this.execute(properties);
            batchJob.setLastRunStatus(Status.SUCCESS);
        } catch (Exception exception) {
            log.error("Exception Occurred While executing the job", exception);
            batchJob.setLastRunStatus(Status.FAIL);
            errorMessage = exception.getMessage();
            if (errorMessage != null)
                errorMessage.substring(0, 1023);
        } finally {
            this.logBatchEnd(batchJob, errorMessage);
        }
    }

    public void logBatchStart(BatchJob job) {
        job.setLastRunStartTimestamp(new Date());
        job.setLastRunEndTimestamp(null);
        job.setLastRunStatus(Status.STARTED);
        var isStarted =
                this.batchJobService.updateStatusToStartedIfStatusNotStartedOrStoppedByJobId(job.getId());
        if (!isStarted) {
            throw new RuntimeException("Error starting job, job is in start or stopped state");
        }
    }

    public void logBatchEnd(BatchJob job, String message) {
        job.setLastRunEndTimestamp(new Date());
        var jobExecution = BatchJobExecution.builder().
                bathJobId(job.getId()).
                lastRunStatus(job.getLastRunStatus()).
                lastRunStartTimestamp(job.getLastRunStartTimestamp()).
                lastRunEndTimestamp(job.getLastRunEndTimestamp()).
                reasonForFailure(message).
                build();
        this.batchJobExecutionService.save(jobExecution);
        this.batchJobService.save(job);
    }

    public abstract void execute(Map<String, String> properties);

}
