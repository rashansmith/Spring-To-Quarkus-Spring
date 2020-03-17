package com.redhat.appdevpractice.samples.survey.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.appdevpractice.samples.survey.http.SurveyGroupResource;
import com.redhat.appdevpractice.samples.survey.model.EmployeeAssignment;
import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;
import com.redhat.appdevpractice.samples.survey.repository.SurveyGroupRepository;
import com.redhat.appdevpractice.samples.survey.utils.ResourceHelper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SurveyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SurveyGroupRepository surveyGroupRepository;

    @Test
    public void shouldPersistASurveyGroup() throws Exception {
        
        MvcResult result = mockMvc.perform(post("/surveygroups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(ResourceHelper.getDefaultSurveyGroupResource())))
            .andExpect(status().isCreated())
            .andReturn();

        String locationHeader = result.getResponse().getHeader("location");

        List<SurveyGroup> surveyGroups = surveyGroupRepository.findAll();
        SurveyGroup surveyGroup = surveyGroups.get(0);

        assertTrue(surveyGroups.size() == 1);
        assertTrue(locationHeader.contains(surveyGroup.getGuid()));
    }

    @Test
    public void shouldGetSurveyGroupByGuid() throws Exception {
        
        mockMvc.perform(post("/surveygroups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(ResourceHelper.getDefaultSurveyGroupResource())))
            .andExpect(status().isCreated())
            .andReturn();

        List<SurveyGroup> surveyGroups = surveyGroupRepository.findAll();
        SurveyGroup surveyGroup = surveyGroups.get(0);

        MvcResult getResult = mockMvc.perform(get("/surveygroups/{id}", surveyGroup.getGuid()))
            .andExpect(status().isOk())
            .andReturn();

        String jsonContent = getResult.getResponse().getContentAsString();
        
        assertTrue(jsonContent.contains(surveyGroup.getGuid()));
        assertTrue(jsonContent.contains(surveyGroup.getOpportunityId()));
        assertTrue(jsonContent.contains(surveyGroup.getProjectId()));
    }

    @Test
    public void shouldGet404NotFound() throws Exception {

        mockMvc.perform(get("/surveygroups/{id}", "453534545"))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void shouldReturnAListOfSurveyGroups() throws Exception {
        
        SurveyGroupResource nasaSurveyGroup = new SurveyGroupResource();
        nasaSurveyGroup.setOpportunityId("NASA");

        nasaSurveyGroup.getEmployeeAssignments()
            .add(ResourceHelper.generateEmployeeAssignment("bob@redhat.com"));

        SurveyGroupResource coronaVirusSurveyGroup = new SurveyGroupResource();
        coronaVirusSurveyGroup.setOpportunityId("Corona");

        coronaVirusSurveyGroup.getEmployeeAssignments()
            .add(ResourceHelper.generateEmployeeAssignment("bob@redhat.com"));

        mockMvc.perform(post("/surveygroups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(nasaSurveyGroup)))
            .andExpect(status().isCreated());

        mockMvc.perform(post("/surveygroups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(coronaVirusSurveyGroup)))
            .andExpect(status().isCreated());

        MvcResult response = mockMvc.perform(get("/surveygroups"))
            .andExpect(status().isOk())
            .andReturn();

        String responseContent = response.getResponse().getContentAsString();

        List<SurveyGroup> surveyGroups = surveyGroupRepository.findAll();

        assumeTrue(() -> surveyGroups.size() == 2);

        SurveyGroup surveyGroup1 = surveyGroups.get(0);
        SurveyGroup surveyGroup2 = surveyGroups.get(1);

        assumeTrue(() -> surveyGroup1.getEmployeeAssignments().size() == 1);
        assumeTrue(() -> surveyGroup2.getEmployeeAssignments().size() == 1);

        EmployeeAssignment bob1 =  surveyGroup1.getEmployeeAssignments().get(0);
        EmployeeAssignment bob2 =  surveyGroup2.getEmployeeAssignments().get(0);

        assertTrue(bob1.equals(bob2));
        assertTrue(responseContent.contains(bob1.getEmail()));
        assertTrue(responseContent.contains(bob2.getEmail()));
        
    }
}