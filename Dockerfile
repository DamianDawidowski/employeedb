# FROM maven:3.8.5-openjdk-17
# WORKDIR /employeedb
# COPY . .
# RUN mvn clean install -DskipTests
# EXPOSE 8008
# CMD mvn spring-boot:run 


#   FROM maven:3.8.5-openjdk-17
# COPY target/demo-0.0.1-SNAPSHOT.jar spring-cloud-open-telemetry.jar
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","/spring-cloud-open-telemetry.jar"]





# COPY  /target/*.jar main.jar
# # ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar .
# # ENV JAVA_TOOL_OPTIONS "-javaagent:./opentelemetry-javaagent.jar"
# CMD ["java", "-jar", "main.jar"]





# FROM openjdk:17-jdk-alpine
# WORKDIR /employeedb
# # COPY . .
# COPY target/my-app.jar .
# CMD ["java",  "-jar", "my-app.jar"]




#####works!
FROM eclipse-temurin:17-jre

ADD target/demo-0.0.1-SNAPSHOT.jar /demo-0.0.1-SNAPSHOT.jar
ADD ./opentelemetry-javaagent.jar /opentelemetry-javaagent.jar

ENTRYPOINT java -javaagent:opentelemetry-javaagent.jar \
                -Dotel.traces.exporter=logging \
                -Dotel.metrics.exporter=logging \
                -Dotel.metrics.exporter.endpoint=http://jaeger:14250 \
                -Dotel.logs.exporter=logging \
                -jar /demo-0.0.1-SNAPSHOT.jar


# FROM openjdk:17-jdk-slim
# WORKDIR /employeedb
# COPY /target/*.jar main.jar
# ADD /target/*.jar main.jar