package com.lvnlx.job.posting.notifier.gcp;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Pubsub {
    private static final Logger logger = LoggerFactory.getLogger(Pubsub.class);

    private final Publisher publisher;

    Pubsub() throws IOException {
        try {
            String projectId = System.getenv("PROJECT_ID");
            String topicId = System.getenv("PUBSUB_TOPIC");
            ProjectTopicName topic = ProjectTopicName.of(projectId, topicId);
            this.publisher = Publisher.newBuilder(topic).build();
        } catch (Exception exception) {
            logger.error("Error setting up Pub/Sub client", exception);
            throw exception;
        }
    }

    public void publish(String message) {
        publisher.publish(createPubsubMessage(ByteString.copyFromUtf8(message)));
    }

    private PubsubMessage createPubsubMessage(ByteString value) {
        return PubsubMessage.newBuilder().setData(value).build();
    }
}
