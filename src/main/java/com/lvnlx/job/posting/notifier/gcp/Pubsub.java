package com.lvnlx.job.posting.notifier.gcp;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Pubsub {
    private final Publisher publisher;

    Pubsub() throws IOException {
        String projectId = System.getenv("PROJECT_ID");
        String topicId = System.getenv("PUBSUB_TOPIC");
        ProjectTopicName topic = ProjectTopicName.of(projectId, topicId);
        this.publisher = Publisher.newBuilder(topic).build();
    }

    public void publish(String message) {
        publisher.publish(createPubsubMessage(ByteString.copyFromUtf8(message)));
    }

    private PubsubMessage createPubsubMessage(ByteString value) {
        return PubsubMessage.newBuilder().setData(value).build();
    }
}
