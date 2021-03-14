```
   Patricks-MacBook-Pro-2:pubsubmockpoc patrickstocklin$ docker-compose up
   Building pubsub-mock
   Step 1/3 : FROM google/cloud-sdk:latest
    ---> 55ef8753037e
   Step 2/3 : RUN echo "Building Image"
    ---> Running in db3a8977685d
   Building Image
    ---> 38cc2590ca5b
   Removing intermediate container db3a8977685d
   Step 3/3 : CMD gcloud beta emulators pubsub start --host-port pubsub-mock:8085 --project=mock-project --verbosity=debug
    ---> Running in 50ed45422b86
    ---> 61699b226dd1
   Removing intermediate container 50ed45422b86
   Successfully built 61699b226dd1
   WARNING: Image for service pubsub-mock was built because it did not already exist. To rebuild this image you must use `docker-compose build` or `docker-compose up --build`.
   Building java
   Step 1/7 : FROM adoptopenjdk/openjdk11
    ---> e6c8ea0feb8b
   Step 2/7 : RUN mkdir /root/app
    ---> Running in 23ce392ddd11
    ---> 68f69f7023b0
   Removing intermediate container 23ce392ddd11
   Step 3/7 : ENV HOME /root/app
    ---> Running in 557903e23ed1
    ---> b4b59f95304f
   Removing intermediate container 557903e23ed1
   Step 4/7 : WORKDIR /root/app
    ---> b916bf897953
   Removing intermediate container 691dd67d1651
   Step 5/7 : EXPOSE 8080
    ---> Running in 1a509af1de3c
    ---> dddcecb0c38f
   Removing intermediate container 1a509af1de3c
   Step 6/7 : COPY ./build/libs/pubsubmockpoc-1.0-SNAPSHOT-all.jar app.jar
    ---> 21dc351af39e
   Removing intermediate container 486744ea8957
   Step 7/7 : CMD java -jar app.jar
    ---> Running in 1334410050a3
    ---> 06b51a2a9270
   Removing intermediate container 1334410050a3
   Successfully built 06b51a2a9270
   WARNING: Image for service java was built because it did not already exist. To rebuild this image you must use `docker-compose build` or `docker-compose up --build`.
   Creating pubsubmockpoc_pubsub-mock_1
   Creating pubsubmockpoc_java_1
   Attaching to pubsubmockpoc_pubsub-mock_1, pubsubmockpoc_java_1
   pubsub-mock_1  | DEBUG: Running [gcloud.beta.emulators.pubsub.start] with arguments: [--host-port: "<googlecloudsdk.calliope.arg_parsers.HostPort object at 0x7f68e9836da0>", --project: "mock-project", --verbosity: "debug"]
   pubsub-mock_1  | DEBUG: Found Cloud SDK root: /usr/lib/google-cloud-sdk
   pubsub-mock_1  | Executing: /usr/lib/google-cloud-sdk/platform/pubsub-emulator/bin/cloud-pubsub-emulator --host=pubsub-mock --port=8085
   java_1         | Starting PubSubMockDemo
   java_1         | hostPort - pubsub-mock:8085
   pubsub-mock_1  | [pubsub] This is the Google Pub/Sub fake.
   pubsub-mock_1  | [pubsub] Implementation may be incomplete or differ from the real system.
   pubsub-mock_1  | [pubsub] Mar 14, 2021 7:49:12 AM com.google.cloud.pubsub.testing.v1.Main main
   pubsub-mock_1  | [pubsub] INFO: IAM integration is disabled. IAM policy methods and ACL checks are not supported
   pubsub-mock_1  | [pubsub] SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
   pubsub-mock_1  | [pubsub] SLF4J: Defaulting to no-operation (NOP) logger implementation
   pubsub-mock_1  | [pubsub] SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
   pubsub-mock_1  | [pubsub] Mar 14, 2021 7:49:14 AM com.google.cloud.pubsub.testing.v1.Main main
   pubsub-mock_1  | [pubsub] INFO: Server started, listening on 8085
   pubsub-mock_1  | [pubsub] Mar 14, 2021 7:49:15 AM io.gapi.emulators.netty.HttpVersionRoutingHandler channelRead
   pubsub-mock_1  | [pubsub] INFO: Detected HTTP/2 connection.
   java_1         | Created Topic - name: "projects/mock-project/topics/test-topic"
   java_1         | 
   java_1         | Created Sub - name: "projects/mock-project/subscriptions/test-subscription"
   java_1         | topic: "projects/mock-project/topics/test-topic"
   java_1         | push_config {
   java_1         | }
   java_1         | ack_deadline_seconds: 10
   java_1         | message_retention_duration {
   java_1         |   seconds: 604800
   java_1         | }
   java_1         | 
   java_1         | Published a message with custom attributes: 1
   java_1         | Published a message with custom attributes: 2
   java_1         | Published a message with custom attributes: 3
   java_1         | Published a message with custom attributes: 4
   java_1         | Published a message with custom attributes: 5
   java_1         | Published a message with custom attributes: 6
   java_1         | Published a message with custom attributes: 7
   java_1         | Published a message with custom attributes: 8
   java_1         | Published a message with custom attributes: 9
   java_1         | Published a message with custom attributes: 10
   java_1         | Listening for messages on projects/mock-project/subscriptions/test-subscription:
   java_1         | Id: 1
   java_1         | Data: Mock Message - 1
   java_1         | Id: 2
   java_1         | Id: 3
   java_1         | Data: Mock Message - 3
   java_1         | Id: 4
   java_1         | Data: Mock Message - 4
   java_1         | Id: 7
   java_1         | Data: Mock Message - 7
   java_1         | Id: 6
   java_1         | Data: Mock Message - 6
   java_1         | Data: Mock Message - 2
   java_1         | Id: 5
   java_1         | Data: Mock Message - 5
   java_1         | Id: 10
   java_1         | Data: Mock Message - 10
   java_1         | Id: 9
   java_1         | Data: Mock Message - 9
   java_1         | Id: 8
   java_1         | Data: Mock Message - 8
   java_1         | Closing clients
   java_1         | Exiting PubSubMockDemo
   pubsubmockpoc_java_1 exited with code 0
```