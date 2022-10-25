package code.shubham.libraries.utils.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerFactory {

    public static <R> code.shubham.libraries.utils.kafka.KafkaConsumer<R> create(KafkaConstants constants) {
        return new code.shubham.libraries.utils.kafka.KafkaConsumer<>(createConsumer(constants), constants);
    }

    public static <R> Consumer<Long, R> createConsumer(KafkaConstants constants) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, constants.getKafkaBrokers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, constants.getGroupIdConfig());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, constants.getMaxPollRecords());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, constants.getAutoCommmit());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, constants.getOffsetResetEarlier());

        Consumer<Long, R> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(constants.getTopicName()));
        return consumer;
    }

}
