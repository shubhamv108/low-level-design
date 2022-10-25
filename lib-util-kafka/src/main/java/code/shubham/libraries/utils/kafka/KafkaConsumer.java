package code.shubham.libraries.utils.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Slf4j
public class KafkaConsumer<Message> {

    private final Consumer<Long, Message> consumer;
    private final KafkaConstants constants;

    public KafkaConsumer(final Consumer<Long, Message> consumer,
                         final KafkaConstants constants) {
        this.consumer = consumer;
        this.constants = constants;
    }

    public <R> void runConsumer(KafkaConsumerCallback<Message, R> callback) {
        int noMessageFound = 0;

        while (true) {
            ConsumerRecords<Long, Message> consumerRecords = consumer.poll(1000000000);
            // 1000000000 is the time in milliseconds consumer will wait if no record is found at broker.
            if (consumerRecords.count() == 0) {
                noMessageFound++;
                if (noMessageFound > constants.getMaxNoMessageFoundCount())
                    // If no message found count is reached to threshold exit loop.
                    break;
                else
                    continue;
            }

            List<R> result = new ArrayList<>();
            //print each record.
            consumerRecords.forEach(record -> result.add(callback.apply(record)));

            // commits the offset of record to broker.
            consumer.commitAsync();
        }
        consumer.close();
    }
}
