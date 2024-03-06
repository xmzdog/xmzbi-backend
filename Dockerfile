# Docker 镜像构建
# @author <a href="https://github.com/xmzdog/xmzbi-backend">程序员xmz</a>
#
FROM maven:3.8.1-jdk-8-slim as builder

ENV PORT=8101
EXPOSE ${PORT}
RUN mkdir /usr/local/work
WORKDIR /usr/local/work
COPY xmzbi-0.0.1-SNAPSHOT.jar ./app.jar
ENTRYPOINT java -jar ./app.jar --server.port=${PORT}

# Copy local code to the container image.
#WORKDIR /app
#COPY pom.xml .
#COPY src ./src
#
## Build a release artifact.
#RUN mvn package -DskipTests
#
## Run the web service on container startup.
#CMD ["java","-jar","/app/target/xmzbi-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]