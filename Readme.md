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
mvnw clean package
```

### Runnning Postgres

```
docker run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 \
           --name postgres-quarkus-hibernate -e POSTGRES_USER=hibernate \
           -e POSTGRES_PASSWORD=hibernate -e POSTGRES_DB=hibernate_db \
           -p 5432:5432 postgres:10.5
```

### Running Locally
You can run the quarkus application locally by issueing the following command:

```
./mvnw quarkus:dev
```

### Current Issues

Tests Passing: 
- SurveyGroupRepositoryIntegrationTest (5/5)
- SurveyControllerIntegrationTest (4/4)
- SurveyControllerTest (5/6)
- SurveyServiceImplTest (4/5)
- EmployeeAssignmentTest (2/2)

