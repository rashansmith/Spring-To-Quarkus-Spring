package com.redhat.appdevpractice.samples.survey.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.redhat.appdevpractice.samples.survey.http.NewSurveyGroupResource;
import com.redhat.appdevpractice.samples.survey.http.SurveyGroupResource;
import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;
import com.redhat.appdevpractice.samples.survey.repository.SurveyGroupRepository;
import com.redhat.appdevpractice.samples.survey.service.H2DatabaseTestResource;
import com.redhat.appdevpractice.samples.survey.utils.ResourceHelper;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
@QuarkusTest
public class SurveyControllerIntegrationTest {

	@Autowired
	private ObjectMapper mapper;

	@Inject
	SurveyGroupRepository repository;
	
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
		
		String location = RestAssured.given().accept(ContentType.JSON).request().contentType(ContentType.JSON)
				.body(ResourceHelper.getDefaultSurveyGroupResource()).when().post("/surveygroups").then()
				.statusCode(201).extract().header("Location");

		String guid = location.toString().replace("http://localhost:8081/surveygroups/", "");

		assertTrue(repository.findByGuid(guid).isPersistent());

	}

	@Test
	public void shouldGetSurveyGroupByGuid() throws Exception {
		
		SurveyGroupResource nasaSurveyGroup = new SurveyGroupResource();
		nasaSurveyGroup.setOpportunityId("NASA");

		nasaSurveyGroup.getEmployeeAssignments().add(ResourceHelper.generateEmployeeAssignment("bob@redhat.com"));

		String location = RestAssured.given().accept(ContentType.JSON).request().contentType(ContentType.JSON)
				.body(nasaSurveyGroup).when().post("/surveygroups").then().statusCode(201).extract().header("Location");

		String s = location.toString().replace("http://localhost:8081/surveygroups/", "");

		RestAssured.given().accept(ContentType.JSON).request().contentType(ContentType.JSON).when()
				.get("/surveygroups/" + s).then().statusCode(200).body("opportunityId", is("NASA"));
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

		RestAssured.given().when().get("/surveygroups").then().statusCode(200).body("size()", is(2))
				.body("opportunityId[0]", containsString("NASA"))
				.body("opportunityId[1]", containsString("Corona"));
			
	}
}