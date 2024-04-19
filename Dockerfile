FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /employeedb
COPY . .
RUN mvn -f /employeedb/pom.xml clean package

FROM eclipse-temurin:17-jre as prod
COPY --from=build /employeedb/target/demo-0.0.1-SNAPSHOT.jar /demo-0.0.1-SNAPSHOT.jar
ADD ./opentelemetry-javaagent.jar /opentelemetry-javaagent.jar

ENTRYPOINT java -javaagent:opentelemetry-javaagent.jar \
                -Dotel.traces.exporter=logging \
                -Dotel.metrics.exporter=logging \
                -Dotel.metrics.exporter.endpoint=http://jaeger:14250 \
                -Dotel.logs.exporter=logging \
                 -jar /demo-0.0.1-SNAPSHOT.jar