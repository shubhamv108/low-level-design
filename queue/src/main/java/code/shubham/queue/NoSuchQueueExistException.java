package code.shubham.queue;

public class NoSuchQueueExistException extends RuntimeException {

    private static final String key = "queueName";

    public NoSuchQueueExistException(final String queueName) {
        super(String.format("Invalid queue name: {}; Reason: Does not exist.", queueName));
    }

    public String getKey() {
        return key;
    }
}
