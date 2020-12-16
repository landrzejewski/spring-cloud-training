FROM openjdk:11-jre-slim-buster
ADD target/gateway-server.jar /
CMD java -jar -Dspring.profiles.active=$ACTIVE_PROFILE \
    -Dspring.cloud.config.uri=$CONFIGURATION_SERVER_URI \
    gateway-server.jar