```
To start the application you need to:

1) Initiate the database:
```
docker run -d -e POSTGRES_HOST_AUTH_METHOD=trust -e POSTGRES_USER=root -e POSTGRES_PASSWORD=secret -e POSTGRES_DB=employeedb -p 5434:5432 postgres:13
```
2) Start the backend server 
``` 
mvn spring-boot:run