package code.shubham.libraries.utils.kafka;

public abstract class KafkaMessage {
    private int retired = 0;

    public void incrementRetries() {
        this.retired++;
    }
}
