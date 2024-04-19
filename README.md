```
To start the application you need to:

1) Provide the keys and passwords in application.properites and application.yml files:
    - KEYSTORE_PKCS12_PATH // define the path for the keystore file (to enable HTTPS), you can use keytool from java package to generate the key. Example "classpath:keystore/myKey.p12"
    - KEYSTORE_PKCS12_PASSWORD // provide the password you used to generate the keystore file 
    - SECRET_JWT_KEY // must be an HMAC hash string of 256 bits, can be obtained at https://www.devglan.com/online-tools/hmac-sha256-online
    - SPRING_LOG_PATH // define the path for the log file, that can pass logs from this application to the ELK logging package


2) Initiate the database:
```
docker run -d -e POSTGRES_HOST_AUTH_METHOD=trust -e POSTGRES_USER=root -e POSTGRES_PASSWORD=secret -e POSTGRES_DB=employeedb -p 5434:5432 postgres:13
```
3) Start the backend server 
``` 
cd employeedb
mvn spring-boot:run


To run the 