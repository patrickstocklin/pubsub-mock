FROM google/cloud-sdk:latest

RUN echo "Building Image"

CMD ["gcloud", "beta", "emulators", "pubsub", "start", "--host-port", "pubsub-mock:8085", "--project=mock-project", "--verbosity=debug"]
