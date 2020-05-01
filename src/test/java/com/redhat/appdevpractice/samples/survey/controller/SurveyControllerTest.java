package com.redhat.appdevpractice.samples.survey.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import javax.ws.rs.core.Response;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import com.redhat.appdevpractice.samples.mockBeans.MockSurveyServiceImpl;
import com.redhat.appdevpractice.samples.survey.http.NewSurveyGroupResource;
import com.redhat.appdevpractice.samples.survey.http.SurveyGroupResource;
import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;
import com.redhat.appdevpractice.samples.survey.repository.SurveyGroupRepository;
import com.redhat.appdevpractice.samples.survey.service.SurveyServiceImpl;
import com.redhat.appdevpractice.samples.survey.utils.ResourceHelper;

import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.RestAssured;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
//import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.ws.rs.core.Response;
import com.redhat.appdevpractice.samples.survey.utils.ResourceHelper;

//@ExtendWith(MockitoExtension.class)

@QuarkusTest
public class SurveyControllerTest {

    @Autowired
    private SurveyController controller;
    
    @Mock
    private static SurveyGroupRepository surveyGroupRepo;

    @Mock
    private static SurveyServiceImpl surveyService;
    
  

    @BeforeAll
    public static void initialize()  {
        /*MockHttpServletRequest request = new MockHttpServletRequest("POST", "/surveygroups"); 
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));*/
    	
       //MockHttpRequest request = MockHttpRequest.post("/surveygroups");
       //MockHttpRequest.create("POST", "/surveygroups");
       //	  mockServer = MockRestServiceServer.createServer(restTemplate)
    	//HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	//HttpSession session = request.getSession();
    	 /*HttpServletRequest request = (HttpServletRequest) requestEvent.getServletRequest();
    	  ServletRequestAttributes attributes = new ServletRequestAttributes(request);
    	  request.setAttribute(REQUEST_ATTRIBUTES_ATTRIBUTE, attributes);
    	  LocaleContextHolder.setLocale(request.getLocale());*/
    	  
    	  //RequestContextHolder.setRequestAttributes(attributes);
    	SurveyServiceImpl mock = Mockito.mock(SurveyServiceImpl.class);  
        QuarkusMock.installMockForType(mock, SurveyServiceImpl.class); 
        surveyService = new SurveyServiceImpl(surveyGroupRepo);
   }

    @Test
    public void shouldCallSurveyService() throws URISyntaxException {

    	NewSurveyGroupResource sg = new NewSurveyGroupResource();
       // when(surveyService.createSurveyGroup(sg)).thenReturn(new SurveyGroup());
    
        
       ResponseEntity<SurveyGroup> r =  this.controller.createSurvey(sg);
       
   	   System.out.println(r.getBody().getGuid());
       assert(r.getBody() != null);
        
        

        //Mockito.verify(surveyService, times(1)).createSurveyGroup(any(SurveyGroup.class));
    }

    @Test
    public void shouldReturn201Created() throws URISyntaxException {

       // when(surveyService.createSurveyGroup(any(SurveyGroup.class))).thenReturn(new SurveyGroup());
       // ResponseEntity<String> response = this.controller.createSurvey(new NewSurveyGroupResource());
        ResponseEntity<SurveyGroup> response = this.controller.createSurvey(new NewSurveyGroupResource());
        System.out.println(response.getBody().getGuid());
        assertEquals(200, response.getStatusCode());
    }

    /*@Test
    public void shouldReturnLocationWithPathAndGuid() throws URISyntaxException {

        String guid = "lsdfjlsfjdldjfsl23423424234";

        SurveyGroup persistedGroup = new SurveyGroup();
        persistedGroup.setGuid(guid);

        //when(surveyService.createSurveyGroup(any(SurveyGroup.class))).thenReturn(persistedGroup);
        //ResponseEntity<String> response = this.controller.createSurvey(new NewSurveyGroupResource());
        
        Response response = this.controller.createSurvey(new NewSurveyGroupResource();
        String locationHeaderPath = response.getHeaders().ge
        //String locationHeaderPath = response.getHeaders().getLocation().getPath();
        assertEquals("/surveygroups/" + guid, locationHeaderPath);
    }*/

    @Test
    public void shouldCallSurveyServiceToGetAllSurveyGroups() throws URISyntaxException {

        //SurveyGroup persistedSurveyGroup1 = new SurveyGroup();
        //SurveyGroup persistedSurveyGroup2 = new SurveyGroup();
    	
    	NewSurveyGroupResource persistedSurveyGroup1 = new NewSurveyGroupResource();
    	NewSurveyGroupResource  persistedSurveyGroup2 = new NewSurveyGroupResource ();
    	
    	this.controller.createSurvey(persistedSurveyGroup1);
    	this.controller.createSurvey(persistedSurveyGroup2);

        //Mockito.when(surveyService.getSurveyGroups()).thenReturn(Arrays.asList(persistedSurveyGroup1, persistedSurveyGroup2));
        
        //List<SurveyGroupResource> surveyGroupResources = this.controller.getSurveyGroups();
        
        ResponseEntity<List<SurveyGroup>> response = this.controller.getSurveyGroups();
        
        
        
        List<SurveyGroup> surveyGroupResources = (List<SurveyGroup>) response.getBody();
        

        //verify(surveyService, times(1)).getSurveyGroups();

        assertEquals(2, surveyGroupResources.size());
    }

    @Test
    public void shouldReturnSurveyGroupByGuid() throws URISyntaxException {

        String guid = "lsdfjlsfjdldjfsl23423424234";

        //persistedSurveyGroup.setGuid(guid);

        ResponseEntity<SurveyGroup> sg1 = this.controller.createSurvey( new NewSurveyGroupResource());
        
        SurveyGroup temp = (SurveyGroup) sg1.getBody();
        
        //SurveyGroup tl = RestTemplate.exchange(uri, HttpMethod.GET, entity, EmployeeList.class);
        
        String gd = temp.getGuid();

       //  Mockito.when(surveyService.getSurveyGroup(guid)).thenReturn(persistedSurveyGroup);
        
       // ResponseEntity<SurveyGroupResource> surveyGroupResource = this.controller.getSurveyGroup(guid);
        Response surveyGroupResource = this.controller.getSurveyGroup(gd);
        
        SurveyGroup sg = (SurveyGroup) surveyGroupResource.getEntity();
        
        //RestAssured.given()
        //		.when()
        //verify(surveyService, times(1)).getSurveyGroup(guid);

        //assertEquals(HttpStatus.OK, surveyGroupResource.getStatusCode());
        //assertEquals(guid, surveyGroupResource.getBody().getId());
        
        assertEquals(HttpStatus.OK, surveyGroupResource.getStatus());
        assertEquals(gd, sg.getGuid());
    }

    @Test
    public void shouldReturn404NotFound() throws URISyntaxException {

        String guid = "lsdfjlsfjdldjfsl23423424234";

       // Mockito.when(surveyService.getSurveyGroup(guid)).thenReturn(null);
        
        Response group = this.controller.getSurveyGroup(guid);

        Integer status = 400;
        assertEquals(401, group.getStatus());
    }

}