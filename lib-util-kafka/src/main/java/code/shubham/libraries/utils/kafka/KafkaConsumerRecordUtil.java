package code.shubham.libraries.utils.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

@Slf4j
public class KafkaConsumerRecordUtil {

    public static void log(ConsumerRecord<Long, ?> record) {
        log.info("Record Key " + record.key());
        log.info("Record value " + record.value());
        log.info("Record partition " + record.partition());
        log.info("Record offset " + record.offset());
    }

}
