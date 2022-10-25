package code.shubham.libraries.utils.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface KafkaConsumerCallback<Message, R> {

    default R apply(ConsumerRecord<Long, Message> consumerRecord) {
        KafkaConsumerRecordUtil.log(consumerRecord);
        return consume(consumerRecord.value());
    }

    R consume(Message message);
}
