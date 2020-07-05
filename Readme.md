# Foreign Exchange Transaction processor
This is a interview answer
## Link for validation
#### Message Consumption
`http://localhost:8080/api/transaction/history`
#### Message Frontend
If you run from source code: `http://localhost:3000`

If you run with docker or packaged jar: `http://localhost:8080`
## Assumption
As it is a test, the validation and test cases are not 100% coverage. But different kinds of test technology used,

Since it is a backend test, frontend do not implement with complex structure and test not implemented
## Local Run
The project is running and test on JDK 11

The frontend project is wrote by ReactJs

For local run from source code, start backend first. Then start frontend
```sh
mvn spring-boot:run
cd frontend
npm start
```
Then you can visit through `http://localhost:3000`

Or You can run both of them with the packaged jar
```sh
mvn clean install package
cd target
jar -jar inventory-0.0.1-SNAPSHOT.jar
```  
Then you can visit through `http://localhost:8080`

You can also run with Docker, docker file is also ready
## Reset the database
Each time restart the database will be reset
## Local Develop
**Lombok** is being used. Plugin need to be installed if you want to develop with IDE.
#### Intellij IDEA
https://plugins.jetbrains.com/plugin/6317-lombok
#### Eclipse
https://projectlombok.org/setup/eclipse