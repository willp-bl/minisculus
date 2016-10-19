# Note: each step creates a new intermediate container
# Have steps that change less frequently up at the top, so more build
# steps are cached

# Take a look at the Dockerfile reference docs: https://docs.docker.com/engine/reference/builder/

# using JDK as we will build the app within the container because it's
# convenient.  It's probably not something we should do for production
# containers.
FROM openjdk:8-jdk

# You can add useful metadata here
# MAINTAINER name <email.name @ domain>

# More labels are available
LABEL version="1.0"

# Install Maven.  Need to update first.
RUN apt-get update && apt-get update -y
RUN apt-get install -y maven

# We want to use Java8, make sure we are
RUN update-java-alternatives --set java-1.8.0-openjdk-amd64

# Run a healthcheck on the app
HEALTHCHECK --interval=10s --timeout=2s CMD curl http://localhost:8081/healthcheck || exit 1

# Set the directory where commands will be run from
WORKDIR /usr/src/microservice/

# Bit of a hack - because the pom file changes less frequently than the
# rest of the app, copy it in to the container and download dependencies
# That means they will only be re-downloaded when there is a change to the
# pom.xml
COPY ./microservice/pom.xml /usr/src/microservice/

# Download dependencies only
RUN mvn dependency:resolve

# 'cp -R' app into the container
COPY ./microservice/ /usr/src/microservice/

# Package the app
RUN mvn package

# Expose the application port
EXPOSE 8080

# Run!
CMD java -jar ./target/microservice-1.0.jar server config/microservice.yml

