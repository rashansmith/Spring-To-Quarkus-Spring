package com.redhat.appdevpractice.samples.survey.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;

import java.util.Arrays;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Validator;
import javax.ws.rs.core.Context;

import com.redhat.appdevpractice.samples.survey.http.NewSurveyGroupResource;
import com.redhat.appdevpractice.samples.survey.http.SurveyGroupResource;
import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;
import com.redhat.appdevpractice.samples.survey.repository.SurveyGroupRepository;
import com.redhat.appdevpractice.samples.survey.service.SurveyServiceImpl;

//import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@QuarkusTest
@Transactional
public class SurveyControllerTest {

    @InjectMocks
    private SurveyController controller;

    @InjectMocks
    private static SurveyServiceImpl surveyService;
   

    @BeforeEach
    public void initialize()  {

    	//SurveyServiceImpl mock = Mockito.mock(SurveyServiceImpl.class);  
        //QuarkusMock.installMockForType(mock, SurveyServiceImpl.class); 
        //surveyService = new SurveyServiceImpl(surveyGroupRepo);
        //MockitoAnnotations.initMocks(this);
    	 surveyService = Mockito.mock(SurveyServiceImpl.class);
   	     controller = Mockito.mock(SurveyController.class); 
   	     when(surveyService.createSurveyGroup(any(SurveyGroup.class))).thenReturn(new SurveyGroup());
   }
    
    @Test
    public void shouldCallSurveyService() throws URISyntaxException {
         
         controller.createSurvey(new NewSurveyGroupResource());

         verify(controller, times(1)).createSurvey(any(NewSurveyGroupResource.class));
    }

    /*@Test
    public void shouldReturn201Created() throws URISyntaxException {
         
    	controller.createSurvey(new NewSurveyGroupResource()).getStatusCode();

         verify(controller, times(1)).createSurvey(any(NewSurveyGroupResource.class));
         
    }*/

    /*@Test
    public void shouldReturnLocationWithPathAndGuid() throws URISyntaxException {

        String guid = "lsdfjlsfjdldjfsl23423424234";

        SurveyGroup persistedGroup = new SurveyGroup();
        persistedGroup.setGuid(guid);

        when(surveyService.createSurveyGroup(any(SurveyGroup.class))).thenReturn(persistedGroup);
        ResponseEntity<String> response = controller.createSurvey(new NewSurveyGroupResource());
        
        String locationHeaderPath = response.getHeaders().getLocation().getPath();
        assertEquals("/surveygroups/" + guid, locationHeaderPath);
    }*/

    @Test
    public void shouldCallSurveyServiceToGetAllSurveyGroups() throws URISyntaxException {

    	SurveyGroup persistedSurveyGroup1 = new SurveyGroup();
        SurveyGroup persistedSurveyGroup2 = new SurveyGroup();

        when(surveyService.getSurveyGroups()).thenReturn(Arrays.asList(persistedSurveyGroup1, persistedSurveyGroup2));
           
        
        controller.createSurvey(new NewSurveyGroupResource());
        controller.createSurvey(new NewSurveyGroupResource());
        
        controller.getSurveyGroups();

        verify(controller, times(1)).getSurveyGroups();
        
    }

    @Test
    public void shouldReturnSurveyGroupByGuid() throws URISyntaxException {

        String guid = "lsdfjlsfjdldjfsl23423424234";

        SurveyGroup persistedSurveyGroup = new SurveyGroup();
        
        persistedSurveyGroup.setGuid(guid);

        when(surveyService.getSurveyGroup(guid)).thenReturn(persistedSurveyGroup);
        
        controller.getSurveyGroup(guid);

        verify(controller, times(1)).getSurveyGroup(guid);

    }

    /*@Test
    public void shouldReturn404NotFound() throws URISyntaxException {

        String guid = "lsdfjlsfjdldjfsl23423424234";

        when(surveyService.getSurveyGroup(guid)).thenReturn(null);
        
        ResponseEntity<SurveyGroupResource> group = this.controller.getSurveyGroup(guid);

        assertEquals("404", group.getStatusCodeValue());
    }*/

}