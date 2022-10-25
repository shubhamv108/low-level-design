package code.shubham.batch;

import code.shubham.commons.Constants;
import code.shubham.commons.contexts.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Component
public class BatchJobsScheduler {

    private final TaskScheduler scheduler;

    private final ApplicationContext applicationContext;

    private final BatchJobService batchJobService;

    private final static Map<String, ScheduledJob> SCHEDULED_JOBS = new HashMap<>();

    @Autowired
    public BatchJobsScheduler(final TaskScheduler scheduler,
                              final ApplicationContext applicationContext,
                              final BatchJobService batchJobService) {
        this.scheduler = scheduler;
        this.applicationContext = applicationContext;
        this.batchJobService = batchJobService;
    }

    @PostConstruct
    public void init() {
        log.info("Processing batch definitions...");
        UserContextHolder.setUserId(Constants.SYSTEM_USER_ID);
        this.schedule();
    }

    private void schedule() {
        Map<String, Object> beans = this.applicationContext.getBeansWithAnnotation(Job.class);
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            var beanName = entry.getKey();
            var batchJobBean = (AbstractBatchJob) entry.getValue();
            var job = batchJobBean.getClass().getAnnotation(Job.class);
            var batchJobOptional = this.batchJobService.getByJobCode(job.code());
            if (batchJobOptional.isEmpty())
                continue;

            var batchJob = batchJobOptional.get();
            var scheduledJob = SCHEDULED_JOBS.get(job.code());
            if (scheduledJob != null && scheduledJob.getCronCreatedAt().before(batchJob.getCreatedAt()))
                continue;

            scheduledJob = SCHEDULED_JOBS.get(job.code());
            scheduledJob.getScheduledFuture().cancel(false);

            ScheduledFuture scheduledFuture = scheduler.schedule(
                    this.getSchedulableTask(batchJobBean), new CronTrigger(batchJob.getCron()));
            ScheduledJob item =  new ScheduledJob(batchJob.getName(), scheduledFuture);
            SCHEDULED_JOBS.put(batchJob.getCode(), item);
        }
    }

    private Runnable getSchedulableTask(AbstractBatchJob jobBean) {
        return () -> this.scheduleJob(jobBean);
    }

    private void scheduleJob(final AbstractBatchJob jobBean) {
        log.info(jobBean.getClass().getAnnotation(Job.class).code() + " Starting as a new process...");
        final Map<String, String> properties = new HashMap<>();
        properties.put(code.shubham.batch.Constants.JOB_CODE_KEY, jobBean.getClass().getAnnotation(Job.class).code());

        this.executeAsync(() -> this.executeJob(jobBean, properties));

        log.info(jobBean.getClass().getAnnotation(Job.class).code() + " is started & executing as a new Process...");
    }

    private void executeJob(final AbstractBatchJob jobBean,
                            final Map<String, String> properties) {
        jobBean.doExecute(properties);
        log.info(jobBean.getClass().getAnnotation(Job.class).code() + " completed execution at " + new Date());
    }

    private void executeAsync(final Runnable runnable) {
        new Thread(runnable).start();
    }
}