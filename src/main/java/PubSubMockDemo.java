import com.google.api.core.ApiFuture;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.GrpcTransportChannel;
import com.google.api.gax.rpc.FixedTransportChannelProvider;
import com.google.api.gax.rpc.TransportChannelProvider;
import com.google.cloud.pubsub.v1.*;
import com.google.common.collect.ImmutableMap;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PubSubMockDemo {

    private static final String PROJECT_ID = "mock-project";
    private static final String TOPIC_ID = "test-topic";
    private static final String SUBSCRIPTION_ID = "test-subscription";

    private static final TopicName TOPIC_NAME =
            TopicName.ofProjectTopicName(PROJECT_ID, TOPIC_ID);

    private static final ProjectSubscriptionName SUBSCRIPTION_NAME =
            ProjectSubscriptionName.of(PROJECT_ID, SUBSCRIPTION_ID);

    private static final String HOST_PORT = "pubsub-mock:8085"; //String hostPort = System.getenv("PUBSUB_EMULATOR_HOST");

    public static void main(String[] args) throws IOException {
        System.out.println("Starting PubSubMockDemo");
        System.out.println("hostPort - " + HOST_PORT);

        //No Creds
        CredentialsProvider credentialsProvider = NoCredentialsProvider.create();

        //Plaintext channel
        ManagedChannel channel =
                ManagedChannelBuilder.forTarget(HOST_PORT).usePlaintext().build();

        //TransportChannelProvider using PT Channel
        TransportChannelProvider channelProvider =
                FixedTransportChannelProvider.create(GrpcTransportChannel.create(channel));

        //Admin
        TopicAdminClient client = TopicAdminClient.create(
                TopicAdminSettings.newBuilder()
                        .setCredentialsProvider(credentialsProvider)
                        .setTransportChannelProvider(channelProvider)
                        .setEndpoint(HOST_PORT)
                        .build());

        //Admin
        SubscriptionAdminClient subClient = SubscriptionAdminClient.create(
                SubscriptionAdminSettings.newBuilder()
                        .setCredentialsProvider(credentialsProvider)
                        .setTransportChannelProvider(channelProvider)
                        .setEndpoint(HOST_PORT)
                        .build());

        //Publisher
        Publisher publisher = Publisher.newBuilder(TOPIC_NAME)
                .setCredentialsProvider(credentialsProvider)
                .setEndpoint(HOST_PORT)
                .setChannelProvider(channelProvider)
                .build();

        //Anon Fn Msg Receiver
        MessageReceiver receiver =
                (PubsubMessage message, AckReplyConsumer consumer) -> {
                    // Handle incoming message, then ack the received message.
                    System.out.println("Id: " + message.getMessageId());
                    System.out.println("Data: " + message.getData().toStringUtf8());
                    consumer.ack();
                };

        //Subscriber
        Subscriber subscriber = Subscriber.newBuilder(SUBSCRIPTION_NAME, receiver)
                .setChannelProvider(channelProvider)
                .setCredentialsProvider(credentialsProvider)
                .setEndpoint(HOST_PORT)
                .build();

        try {
            Topic response = client.createTopic(TOPIC_NAME);
            System.out.println("Created Topic - " + response.toString());

            Subscription subResp = subClient.createSubscription(SUBSCRIPTION_NAME, TOPIC_NAME, PushConfig.getDefaultInstance(), 10);
            System.out.println("Created Sub - " + subResp.toString());

            List<Integer> ints = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
            for (Integer e : ints) {
                String message = "Mock Message - " + e.toString();
                ByteString data = ByteString.copyFromUtf8(message);
                PubsubMessage pubsubMessage =
                        PubsubMessage.newBuilder()
                                .setData(data)
                                .putAllAttributes(ImmutableMap.of("year", "2020", "author", "unknown"))
                                .build();

                ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);
                String messageId = messageIdFuture.get();
                System.out.println("Published a message with custom attributes: " + messageId);
            }

            // Start the subscriber.
            subscriber.startAsync().awaitRunning();
            System.out.printf("Listening for messages on %s:\n", SUBSCRIPTION_NAME.toString());
            // Allow the subscriber to run for 10s unless an unrecoverable error occurs.
            subscriber.awaitTerminated(10, TimeUnit.SECONDS);

            client.deleteTopic(TOPIC_NAME);
            System.out.println("Deleted Topic");
        } catch (TimeoutException e) {
            subscriber.stopAsync();
        } catch (Exception e) {
            throw new RuntimeException("Uh Oh", e);
        } finally {
            System.out.println("Closing clients");
            subscriber.stopAsync();
            publisher.shutdown();
            subClient.close();
            client.close();
        }
        System.out.println("Exiting PubSubMockDemo");
    }

}
