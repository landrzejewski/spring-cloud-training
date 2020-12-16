FROM openjdk:11-jre-slim-buster
ADD target/admin-server.jar /
CMD java -jar -Dserver.port=$PORT admin-server.jar

