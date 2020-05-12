package com.redhat.appdevpractice.samples.survey.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.redhat.appdevpractice.samples.mockBeans.MockSurveyServiceImpl;
//import com.redhat.appdevpractice.samples.survey.http.NewSurveyGroupResource;
import com.redhat.appdevpractice.samples.survey.http.SurveyGroupResource;
import com.redhat.appdevpractice.samples.survey.model.EmployeeAssignment;
import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;
import com.redhat.appdevpractice.samples.survey.repository.SurveyGroupRepository;
import com.redhat.appdevpractice.samples.survey.utils.ResourceHelper;

import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;*/

//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
@Transactional
@QuarkusTest
public class SurveyControllerIntegrationTest {

    // @Autowired
    // private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SurveyGroupRepository surveyGroupRepository;

    @BeforeEach
    public void init() {
        JavaTimeModule module = new JavaTimeModule();
        LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
        mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);

        // MockSurveyServiceImpl mock = Mockito.mock(MockSurveyServiceImpl.class);
        // QuarkusMock.installMockForType(mock, MockSurveyServiceImpl.class);
    }

    @Test
    public void shouldPersistASurveyGroup() throws Exception {
        RestAssured.given().accept(ContentType.JSON).request().contentType(ContentType.JSON)
                .body(ResourceHelper.getDefaultSurveyGroupResource()).when().post("/surveygroups").then()
                .statusCode(200);
    }

    @Test
    public void shouldGetSurveyGroupByGuid() throws Exception {

        /*
         * mockMvc.perform(post("/surveygroups")
         * .contentType(MediaType.APPLICATION_JSON)
         * .content(mapper.writeValueAsString(ResourceHelper.
         * getDefaultSurveyGroupResource()))) .andExpect(status().isCreated())
         * .andReturn();
         * 
         * List<SurveyGroup> surveyGroups = surveyGroupRepository.findAll(); SurveyGroup
         * surveyGroup = surveyGroups.get(0);
         * 
         * MvcResult getResult = mockMvc.perform(get("/surveygroups/{id}",
         * surveyGroup.getGuid())) .andExpect(status().isOk()) .andReturn();
         * 
         * String jsonContent = getResult.getResponse().getContentAsString();
         * 
         * assertTrue(jsonContent.contains(surveyGroup.getGuid()));
         * assertTrue(jsonContent.contains(surveyGroup.getOpportunityId()));
         * assertTrue(jsonContent.contains(surveyGroup.getProjectId()));
         */

        RestAssured.given().accept(ContentType.JSON).request().contentType(ContentType.JSON)
                .body(mapper.writeValueAsString(ResourceHelper.getDefaultSurveyGroupResource())).when()
                .post("/surveygroups").then().statusCode(200).body("projectId", is("Horcrux 1"));
    }

    @Test
    public void shouldGet404NotFound() throws Exception {

        /*
         * mockMvc.perform(get("/surveygroups/{id}", "453534545"))
         * .andExpect(status().isNotFound());
         */
        // RestAssured.given().when().get("/surveygroups/{guid}", "453534545")
        // .then().assertThat().statusCode(401);
        RestAssured.given().when().get("/surveygroups/buster").then().assertThat().statusCode(404);
    }

    @Test
    // @Transactional
    @javax.transaction.Transactional
    public void shouldReturnAListOfSurveyGroups() throws Exception {

        SurveyGroupResource nasaSurveyGroup = new SurveyGroupResource();
        nasaSurveyGroup.setOpportunityId("NASA");

        nasaSurveyGroup.getEmployeeAssignments().add(ResourceHelper.generateEmployeeAssignment("bob@redhat.com"));

        SurveyGroupResource coronaVirusSurveyGroup = new SurveyGroupResource();
        coronaVirusSurveyGroup.setOpportunityId("Corona");

        coronaVirusSurveyGroup.getEmployeeAssignments()
                .add(ResourceHelper.generateEmployeeAssignment("bob@redhat.com"));

        /*
         * mockMvc.perform(post("/surveygroups")
         * .contentType(MediaType.APPLICATION_JSON)
         * .content(mapper.writeValueAsString(nasaSurveyGroup)))
         * .andExpect(status().isCreated());
         * 
         * mockMvc.perform(post("/surveygroups")
         * .contentType(MediaType.APPLICATION_JSON)
         * .content(mapper.writeValueAsString(coronaVirusSurveyGroup)))
         * .andExpect(status().isCreated());
         * 
         * MvcResult response = mockMvc.perform(get("/surveygroups"))
         * .andExpect(status().isOk()) .andReturn();
         * 
         * String responseContent = response.getResponse().getContentAsString();
         * 
         * List<SurveyGroup> surveyGroups = surveyGroupRepository.findAll();
         * 
         * assumeTrue(() -> surveyGroups.size() == 2);
         * 
         * SurveyGroup surveyGroup1 = surveyGroups.get(0); SurveyGroup surveyGroup2 =
         * surveyGroups.get(1);
         * 
         * assumeTrue(() -> surveyGroup1.getEmployeeAssignments().size() == 1);
         * assumeTrue(() -> surveyGroup2.getEmployeeAssignments().size() == 1);
         * 
         * EmployeeAssignment bob1 = surveyGroup1.getEmployeeAssignments().get(0);
         * EmployeeAssignment bob2 = surveyGroup2.getEmployeeAssignments().get(0);
         * 
         * assertTrue(bob1.equals(bob2));
         * assertTrue(responseContent.contains(bob1.getEmail()));
         * assertTrue(responseContent.contains(bob2.getEmail()));
         */

        // Mockito.when(surveyService.createSurveyGroup(surveyGroup1)).getMock();
        RestAssured.given().accept(ContentType.JSON).request().contentType(ContentType.JSON)
                .body(mapper.writeValueAsString(nasaSurveyGroup)).when().post("/surveygroups").then().statusCode(200);

        RestAssured.given().accept(ContentType.JSON).request().contentType(ContentType.JSON)
                .body(mapper.writeValueAsString(coronaVirusSurveyGroup)).when().post("/surveygroups").then()
                .statusCode(200);

        RestAssured.given().accept(ContentType.JSON).request().contentType(ContentType.JSON).when().get("/surveygroups")
                .then().statusCode(200);

        // String responseContent = response.getResponse().getContentAsString();

        // @SuppressWarnings("unchecked")
        // List<SurveyGroup> surveyGroups = (List<SurveyGroup>)
        // surveyGroupRepository.findAll();

        // assumeTrue(() -> surveyGroups.size() == 2);

        // SurveyGroup surveyGroup1 = surveyGroups.get(0);
        // SurveyGroup surveyGroup2 = surveyGroups.get(1);

        // assumeTrue(() -> surveyGroup1.getEmployeeAssignments().size() == 1);
        // assumeTrue(() -> surveyGroup2.getEmployeeAssignments().size() == 1);

        // EmployeeAssignment bob1 = surveyGroup1.getEmployeeAssignments().get(0);
        // EmployeeAssignment bob2 = surveyGroup2.getEmployeeAssignments().get(0);

        // assertTrue(bob1.equals(bob2));
        // assertTrue(responseContent.contains(bob1.getEmail()));
        // assertTrue(responseContent.contains(bob2.getEmail()));

    }
}