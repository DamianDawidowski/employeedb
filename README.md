# Employeedb
 
## Features

This app was made with Java version 17 and Spring Boot 3.2.2.

Employeedb provides a control system for managing permission for employees/users. 

Key features are:

1) Application is protected with a JWT encryption protocol for user authorization

2) RBAC mechanism for restricting feature access

3) Primary endpoints for providing basic employee information (name and role(s))

4) Endpoints for granting additional permissions

5) Endpoints are protected with a HTTPS protocol

6) Endpoints generate logs that can be coupled to the ELK system

7) Application produces OpenTelemetry metrics 

## Instructions


To start the application you need to:

1) Clone the repository

2) Provide the keys and passwords in application.properites and application.yml files:
    - KEYSTORE_PKCS12_PATH // define the path for the keystore file (to enable HTTPS), you can use keytool from java package to generate the key. Example "classpath:keystore/myKey.p12"
    - KEYSTORE_PKCS12_PASSWORD // provide the password you used to generate the keystore file 
    - SECRET_JWT_KEY // must be an HMAC hash string of 256 bits, can be obtained at https://www.devglan.com/online-tools/hmac-sha256-online
    - SPRING_LOG_PATH // define the path for the log file, that can pass logs from this application to the ELK logging package

3) Initiate the database:
```
docker run -d -e POSTGRES_HOST_AUTH_METHOD=trust -e POSTGRES_USER=root -e POSTGRES_PASSWORD=secret -e POSTGRES_DB=employeedb -p 5434:5432 postgres:13
```

4) Start the backend server 
``` 
cd employeedb
mvn spring-boot:run
``` 

Alternatively you can utilise the docker-compose file with the command:
```
docker compose up  
```
 