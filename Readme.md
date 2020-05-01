# Spring Boot ReSTful API Example - Migration To Quarkus
The App Dev Practice has created a Spring Boot application that exposes a ReSTful API. The application can be used as a starter spring-boot application or as a unit to deploy to OCP clusters. 

Here is a link to the original repo: https://github.com/redhat-appdev-practice/spring-rest-surveygroups

In this repo I am migrating the Spring Boot application to Quarkus: https://quarkus.io/

This is currently a work in progress.

### Current Strategy
To get the dependencies needed to quarkify this app, a sample Quarkus app was made: https://github.com/rashansmith/Spring-To-Quarkus-Quarkus/tree/master/survey-quarkus-spring-service

and it's pom.xml elements were transferred to this repo, and non quarkus Spring dependencies were deleted. 


### Building 
```
mvn clean package
```

### Runnning Postgres

```
docker run -p 5432:5432 -e POSTGRES_PASSWORD=password -e POSTGRES_USER=test_user -e POSTGRES_DB=surveydb -d postgres
```

### Running Locally
You can run the quarkus application locally by issueing the following command:

```
mvn quarkus:dev
```

### Current Issues

Right now most of the tests are failing. I suspect they may be related to 
  - not mocking properly
  - Jpa incompatibility/may need to switch to Panache
  - Return types of controller methods




----------------------------------------------------------------------------------------------------------------------
## Survey Groups API
The application manages the concept of a **Survey Group**. This represents the target of surveys for a Red Hat consulting project, the skills required to complete the project, and the employee assigned to the project. 

## API Definition
This service is built in a contract-first manner.  You can find the API definition hosted at https://studio.apicur.io/apis/26569.


### Technologies
| name                                                                           | version   |
| ------------------------------------------------------------------------------ | --------- |
| [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)| 11.-.7    |
| [Apache Maven](https://maven.apache.org/install.html)                          | 3.6.3     |
| [Spring Boot](https://spring.io/projects/spring-boot#learn)                    | 2.2.2     |
| [H2 Database](https://www.h2database.com/html/main.html)                       | 1.4       |

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


## Deploying the application in Openshift

This service comes ready to deploy in an Openshift cluster using the provided Jenkinsfile.


#### Steps to build and deploy in Openshift

Refer to the instructions here: https://github.com/redhat-appdev-practice/infrastructure/wikis/Deploying-A-Service <br/>

This is documentation for deploying the all of Consultant360's CI/CD pipelines and applications. 
Follow all the instructions to deploy the node service and the jenkins pipeline that will manage the build.
You will specifically deploy "spring-rest-surveygroups".

### Manually testing the API

The best way to test the API manually is to use Postman and make calls to your service whether it's on localhost or Openshift. <br/>

Alternatively, you can use curl. Ex:
```
curl http://localhost:8080/surveyGroups/
```


### Code Ready Workspace

Can be deployed in code ready workspace using: http://`CRW_route_url.com`/f?url=https://raw.githubusercontent.com/redhat-appdev-practice/spring-rest-surveygroups/master/devfile.yaml
