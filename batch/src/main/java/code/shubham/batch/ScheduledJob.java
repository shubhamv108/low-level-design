package code.shubham.batch;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

public class ScheduledJob {
    private String batchCode;
    private Date cronCreatedAt;
    private ScheduledFuture scheduledFuture;

    public ScheduledJob(String batchCode, ScheduledFuture scheduledFuture) {
        super();
        this.batchCode = batchCode;
        this.cronCreatedAt = new Date();
        this.scheduledFuture = scheduledFuture;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public ScheduledFuture getScheduledFuture() {
        return scheduledFuture;
    }

    public Date getCronCreatedAt() {
        return cronCreatedAt;
    }
}
