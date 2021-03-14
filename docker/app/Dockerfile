FROM adoptopenjdk/openjdk11

# Set environment variables.
RUN mkdir /root/app
ENV HOME /root/app
WORKDIR /root/app

EXPOSE 8080
COPY ./build/libs/pubsub-mock-1.0-SNAPSHOT-all.jar app.jar

# Define default command.
CMD ["java", "-jar", "app.jar"]
