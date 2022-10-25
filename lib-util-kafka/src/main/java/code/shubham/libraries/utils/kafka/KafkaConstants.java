package code.shubham.libraries.utils.kafka;

public interface KafkaConstants {
    String KAFKA_BROKERS = "localhost:9092";

    Integer MESSAGE_COUNT=1000;

    String CLIENT_ID="client1";

    String TOPIC_NAME="demo";

    String GROUP_ID_CONFIG="consumerGroup1";

     Integer MAX_NO_MESSAGE_FOUND_COUNT=100;

     String OFFSET_RESET_LATEST="latest";

     String OFFSET_RESET_EARLIER="earliest";

     Integer MAX_POLL_RECORDS=1;

     boolean AUTO_COMMIT_ENABLED = false;

     default String getKafkaBrokers() {
         return KAFKA_BROKERS;
     }
     default Integer getMessageCount() {
         return MESSAGE_COUNT;
     }
     default String getClientId() {
         return CLIENT_ID;
     }
     default String getTopicName() {
         return TOPIC_NAME;
     }
    default String getGroupIdConfig() {
        return GROUP_ID_CONFIG;
    }
    default String getOffsetResetEarlier() {
         return OFFSET_RESET_EARLIER;
    }
    default Integer getMaxPollRecords() {
         return MAX_POLL_RECORDS;
    }

    default boolean getAutoCommmit() {
         return AUTO_COMMIT_ENABLED;
    }

    default Integer getMaxNoMessageFoundCount() {
         return MAX_NO_MESSAGE_FOUND_COUNT;
    }
}