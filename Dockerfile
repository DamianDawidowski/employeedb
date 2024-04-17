FROM maven:3.8.5-openjdk-17

WORKDIR /employeedb
COPY . .
RUN mvn clean install -DskipTests
EXPOSE 8008
CMD mvn spring-boot:run