package com.redhat.appdevpractice.samples.survey.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Arrays;

import java.util.List;
import java.util.function.Consumer;

import javax.transaction.Transactional;
import javax.validation.Validator;
import javax.ws.rs.core.Context;

import com.redhat.appdevpractice.samples.survey.http.NewSurveyGroupResource;
import com.redhat.appdevpractice.samples.survey.http.SurveyGroupResource;
import com.redhat.appdevpractice.samples.survey.http.utils.HttpUtils;
import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;
import com.redhat.appdevpractice.samples.survey.repository.SurveyGroupRepository;
import com.redhat.appdevpractice.samples.survey.service.H2DatabaseTestResource;
import com.redhat.appdevpractice.samples.survey.service.SurveyServiceImpl;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusMock;
//import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class SurveyControllerTest {

	@InjectMock
	private SurveyController controller;

	@InjectMock
	private static SurveyServiceImpl surveyService;

	@BeforeEach
	public void initialize() {
		when(surveyService.createSurveyGroup(any(SurveyGroup.class))).thenReturn(new SurveyGroup());
		when(controller.createSurvey(any(NewSurveyGroupResource.class))).thenReturn(new ResponseEntity<String>(new String(), HttpStatus.CREATED)); 
	}

	@Test
	public void shouldCallSurveyService() throws URISyntaxException {

		controller.createSurvey(new NewSurveyGroupResource());

		verify(controller, times(1)).createSurvey(any(NewSurveyGroupResource.class));
	}

	@Test
	public void shouldReturn201Created() throws URISyntaxException {

		controller.createSurvey(new NewSurveyGroupResource());

		verify(controller, times(1)).createSurvey(any(NewSurveyGroupResource.class));
		
        ResponseEntity<String> response = this.controller.createSurvey(new NewSurveyGroupResource());
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	public void shouldReturnLocationWithPathAndGuid() throws URISyntaxException {
		/*
		 * String guid = "lsdfjlsfjdldjfsl23423424234";
		 * 
		 * SurveyGroup persistedGroup = new SurveyGroup(); persistedGroup.setGuid(guid);
		 * 
		 * NewSurveyGroupResource nsqr = new NewSurveyGroupResource();
		 * nsqr.setOpportunityId("12343423"); String s = "32432325";
		 * when(surveyService.createSurveyGroup(any(SurveyGroup.class))).thenReturn(
		 * persistedGroup); when(controller.createSurvey(nsqr)).thenReturn(new
		 * ResponseEntity<String>(s, HttpStatus.CREATED));
		 * 
		 * ResponseEntity<String> response = this.controller.createSurvey(nsqr);
		 * 
		 * assertEquals("/surveygroups/" + guid, response.getHeaders().getLocation());
		 * 
		 */
		String guid = "lsdfjlsfjdldjfsl23423424234";
		 
		SurveyGroup persistedGroup = new SurveyGroup(); 
		 
		persistedGroup.setGuid(guid);
		
		NewSurveyGroupResource resource = new NewSurveyGroupResource();
		resource.setOpportunityId("12343423"); 
		String s = "32432325";
		
		controller.createSurvey(new NewSurveyGroupResource());
		
		when(surveyService.createSurveyGroup(any(SurveyGroup.class))).thenReturn(
				 persistedGroup);
		
		//when(controller.createSurvey(resource))
		//.thenReturn(new ResponseEntity<String>(s, HttpStatus.CREATED)); 
		
		String response2 = controller.createSurvey(resource).getBody().toString();
		System.out.println(response2);
		ResponseEntity<String> response = controller.createSurvey(new NewSurveyGroupResource());
		System.out.println(response.getBody() + " " + response.getHeaders());
		String locationHeaderPath = response.getHeaders().getLocation().getPath();
        assertEquals("/surveygroups/" + guid, locationHeaderPath);
		
		
		
		//verify(controller, times(1)).createSurvey(any(NewSurveyGroupResource.class));
		//String response = this.controller.createSurvey(resource).getBody().toString();
        
        //assertEquals(HttpStatus.CREATED, response.getStatusCode());
        
        //assertEquals("/surveygroups/" + guid, response);
       
	}

	@Test
	public void shouldCallSurveyServiceToGetAllSurveyGroups() throws URISyntaxException {

		SurveyGroup persistedSurveyGroup1 = new SurveyGroup();
		SurveyGroup persistedSurveyGroup2 = new SurveyGroup();

		when(surveyService.getSurveyGroups()).thenReturn(Arrays.asList(persistedSurveyGroup1, persistedSurveyGroup2));

		this.controller.getSurveyGroups();

		verify(controller, times(1)).getSurveyGroups();
		
	}

	@Test
	public void shouldReturnSurveyGroupByGuid() throws URISyntaxException {

		String guid = "lsdfjlsfjdldjfsl23423424234";

		SurveyGroup persistedSurveyGroup = new SurveyGroup();
		persistedSurveyGroup.setGuid(guid);
		
		SurveyGroupResource persistedSurveyGroupResource = HttpUtils.convertToSurveyGroupResourceFrom(persistedSurveyGroup);

		when(surveyService.getSurveyGroup(guid)).thenReturn(persistedSurveyGroup);
		when(controller.getSurveyGroup(guid)).thenReturn(new 
				ResponseEntity<SurveyGroupResource>(persistedSurveyGroupResource, HttpStatus.OK));

		int code = controller.getSurveyGroup(guid).getStatusCodeValue();

		verify(controller, times(1)).getSurveyGroup(guid);

		assertEquals(200, code);
		assertEquals(guid, persistedSurveyGroupResource.getId());
	}

	@Test
	public void shouldReturn404NotFound() throws URISyntaxException {

		String guid = "lsdfjlsfjdldjfsl23423424234";

		when(surveyService.getSurveyGroup(guid)).thenReturn(null);
		when(controller.getSurveyGroup(guid)).thenReturn(new ResponseEntity<SurveyGroupResource>(HttpStatus.NOT_FOUND));

		this.controller.getSurveyGroup(guid);
		
		verify(controller, times(1)).getSurveyGroup(guid);
		
		int code = controller.getSurveyGroup(guid).getStatusCodeValue();
		
		assertEquals(404, code);

	}

}