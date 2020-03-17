package com.redhat.appdevpractice.samples.survey.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import com.redhat.appdevpractice.samples.survey.http.SurveyGroupResource;
import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;
import com.redhat.appdevpractice.samples.survey.service.SurveyServiceImpl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ExtendWith(MockitoExtension.class)
public class SurveyControllerTest {

    @InjectMocks
    private SurveyController controller;

    @Mock
    private SurveyServiceImpl surveyService;

    @BeforeAll
    public static void initialize() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/surveygroups"); 
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void shouldCallSurveyService() throws URISyntaxException {

        when(surveyService.createSurveyGroup(any(SurveyGroup.class))).thenReturn(new SurveyGroup());
        
        this.controller.createSurvey(new SurveyGroupResource());

        verify(surveyService, times(1)).createSurveyGroup(any(SurveyGroup.class));
    }

    @Test
    public void shouldReturn201Created() throws URISyntaxException {

        when(surveyService.createSurveyGroup(any(SurveyGroup.class))).thenReturn(new SurveyGroup());
        ResponseEntity<String> response = this.controller.createSurvey(new SurveyGroupResource());
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void shouldReturnLocationWithPathAndGuid() throws URISyntaxException {

        String guid = "lsdfjlsfjdldjfsl23423424234";

        SurveyGroup persistedGroup = new SurveyGroup();
        persistedGroup.setGuid(guid);

        when(surveyService.createSurveyGroup(any(SurveyGroup.class))).thenReturn(persistedGroup);
        ResponseEntity<String> response = this.controller.createSurvey(new SurveyGroupResource());
        
        String locationHeaderPath = response.getHeaders().getLocation().getPath();
        assertEquals("/surveygroups/" + guid, locationHeaderPath);
    }

    @Test
    public void shouldCallSurveyServiceToGetAllSurveyGroups() throws URISyntaxException {

        SurveyGroup persistedSurveyGroup1 = new SurveyGroup();
        SurveyGroup persistedSurveyGroup2 = new SurveyGroup();

        when(surveyService.getSurveyGroups()).thenReturn(Arrays.asList(persistedSurveyGroup1, persistedSurveyGroup2));
        
        List<SurveyGroupResource> surveyGroupResources = this.controller.getSurveyGroups();

        verify(surveyService, times(1)).getSurveyGroups();

        assertEquals(2, surveyGroupResources.size());
    }

    @Test
    public void shouldReturnSurveyGroupByGuid() throws URISyntaxException {

        String guid = "lsdfjlsfjdldjfsl23423424234";

        SurveyGroup persistedSurveyGroup = new SurveyGroup();
        persistedSurveyGroup.setGuid(guid);

        when(surveyService.getSurveyGroup(guid)).thenReturn(persistedSurveyGroup);
        
        ResponseEntity<SurveyGroupResource> surveyGroupResource = this.controller.getSurveyGroup(guid);

        verify(surveyService, times(1)).getSurveyGroup(guid);

        assertEquals(HttpStatus.OK, surveyGroupResource.getStatusCode());
        assertEquals(guid, surveyGroupResource.getBody().getId());
    }

    @Test
    public void shouldReturn404NotFound() throws URISyntaxException {

        String guid = "lsdfjlsfjdldjfsl23423424234";

        when(surveyService.getSurveyGroup(guid)).thenReturn(null);
        
        ResponseEntity<SurveyGroupResource> group = this.controller.getSurveyGroup(guid);

        assertEquals(HttpStatus.NOT_FOUND, group.getStatusCode());
    }

}