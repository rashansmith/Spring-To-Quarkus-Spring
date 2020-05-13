package com.redhat.appdevpractice.samples.survey.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.redhat.appdevpractice.samples.survey.http.SurveyGroupResource;
import com.redhat.appdevpractice.samples.survey.utils.ResourceHelper;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
@QuarkusTest
public class SurveyControllerIntegrationTest {

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void init() {
        JavaTimeModule module = new JavaTimeModule();
        LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
        mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
    }

    @Test
    public void shouldPersistASurveyGroup() throws Exception {
        RestAssured.given().accept(ContentType.JSON).request().contentType(ContentType.JSON)
                .body(ResourceHelper.getDefaultSurveyGroupResource()).when().post("/surveygroups").then()
                .statusCode(201);
    }

    @Test
    public void shouldGetSurveyGroupByGuid() throws Exception {
    	SurveyGroupResource nasaSurveyGroup = new SurveyGroupResource();
        nasaSurveyGroup.setOpportunityId("NASA");
        
        nasaSurveyGroup.getEmployeeAssignments().add(ResourceHelper.generateEmployeeAssignment("bob@redhat.com"));
        
    	String location = RestAssured.given().accept(ContentType.JSON).request().contentType(ContentType.JSON)
         		.body(nasaSurveyGroup).when().post("/surveygroups").then()
         		.statusCode(201).extract().header("Location");
    	
    	String s = location.toString().replace("http://localhost:8081/surveygroups/", "");
         
        RestAssured.given().accept(ContentType.JSON).request().contentType(ContentType.JSON)
                .when()
                .get("/surveygroups/"+s).then().statusCode(200).body("opportunityId", is("NASA"));
    }

    @Test
    public void shouldGet404NotFound() throws Exception {
    	
        RestAssured.given().when().get("/surveygroups/buster").then().assertThat().statusCode(404);
    }

    @Test
    public void shouldReturnAListOfSurveyGroups() throws Exception {

        SurveyGroupResource nasaSurveyGroup = new SurveyGroupResource();
        nasaSurveyGroup.setOpportunityId("NASA");

        nasaSurveyGroup.getEmployeeAssignments().add(ResourceHelper.generateEmployeeAssignment("bob@redhat.com"));

        SurveyGroupResource coronaVirusSurveyGroup = new SurveyGroupResource();
        coronaVirusSurveyGroup.setOpportunityId("Corona");

        coronaVirusSurveyGroup.getEmployeeAssignments()
                .add(ResourceHelper.generateEmployeeAssignment("bob@redhat.com"));

        RestAssured.given().accept(ContentType.JSON).request().contentType(ContentType.JSON)
                .body(mapper.writeValueAsString(nasaSurveyGroup)).when().post("/surveygroups").then().statusCode(201);

        RestAssured.given().accept(ContentType.JSON).request().contentType(ContentType.JSON)
                .body(mapper.writeValueAsString(coronaVirusSurveyGroup)).when().post("/surveygroups").then()
                .statusCode(201);

        RestAssured.given().when().get("/surveygroups")
                .then().statusCode(200);

    }
}