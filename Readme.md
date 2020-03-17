# Spring Boot ReSTful API Example
The App Dev Practice has created a Spring Boot application that exposes a ReSTful API. The application can be used as a starter spring-boot application or as a unit to deploy to OCP clusters. 

## Survey Groups API
The application manages the concept of a **Survey Group**. This represents the target of surveys for a Red Hat consulting project, the skills required to complete the project, and the employee assigned to the project. 

## Development

### Technologies
| name | version |
| ---  | --- |
| [Java 8](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html) | 1.8.0_231 | 
| [Apache Maven](https://maven.apache.org/install.html) | 3.6.3 | 
| [Spring Boot](https://spring.io/projects/spring-boot#learn) | 2.2.2 | 
| [H2 Database](https://www.h2database.com/html/main.html) | 1.4 | 

### Building 
```
./mvnw clean package
```

### Running Locally
You can run the spring application locally by issueing the following command:

```
./mvnw spring-boot:run
```

This will start the application on port 8080. Once started, you should be able to navigate to [OpenAPI Spec](http://localhost:8080/swagger-ui.html) for a description of the API exposed. 

