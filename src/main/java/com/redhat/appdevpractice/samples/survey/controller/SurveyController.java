package com.redhat.appdevpractice.samples.survey.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.redhat.appdevpractice.samples.survey.http.NewSurveyGroupResource;
import com.redhat.appdevpractice.samples.survey.http.SurveyGroupResource;
import com.redhat.appdevpractice.samples.survey.http.utils.HttpMapper;
import com.redhat.appdevpractice.samples.survey.http.utils.HttpUtils;
import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;
import com.redhat.appdevpractice.samples.survey.repository.SurveyGroupRepository;
import com.redhat.appdevpractice.samples.survey.service.SurveyServiceImpl;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin
@RestController
@ApplicationScoped  
@RegisterRestClient
public class SurveyController {

	@Inject 
	private SurveyGroupRepository sgp;
	
    private SurveyServiceImpl surveyService;

    @Autowired
    public SurveyController(SurveyServiceImpl surveyService) {
        this.surveyService = surveyService;
    }

    @Operation(summary = "Create a new survey group.", description = "", tags = { "surveygroup" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The survey group was created and the URL of the new resource is in the location HTTP header."), 
            @ApiResponse(responseCode = "500", description = "Something unexpected happened on our side.")
        })
    @PostMapping("/surveygroups")
    public ResponseEntity<SurveyGroup> createSurvey(@RequestBody NewSurveyGroupResource newSurveyGroup) {
        
        //SurveyGroup surveyGroup = HttpMapper.MAPPER.convertToSurveyGroupFrom(newSurveyGroup);
    	SurveyGroup surveyGroup = HttpUtils.convertToSurveyGroupFrom(newSurveyGroup);

        SurveyGroup surveyGroup2 = this.surveyService.createSurveyGroup(surveyGroup);

        
		/*URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{surveyGroupId}")
                .buildAndExpand(surveyGroup2.getGuid()).toUri();
		return ResponseEntity.created(location).build();*/
        
        return ResponseEntity.ok().body(surveyGroup2);
    }

    @SuppressWarnings("static-access")
	@Operation(summary = "Get all the survey groups", description = "", tags = { "surveygroup" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "An array of all survey groups in the system. If no survey groups exist, an empty list.")
        })
    @GetMapping("/surveygroups")
    public ResponseEntity<List<SurveyGroup>> getSurveyGroups() {
        
        List<SurveyGroup> surveyGroups = this.surveyService.getSurveyGroups();
        
        List<SurveyGroup> resourceCollection = new ArrayList<>();
       // surveyGroups.forEach(sg -> resourceCollection.add(HttpMapper.MAPPER.convertToSurveyGroupResourceFrom(sg)));
        //surveyGroups.forEach(sg -> resourceCollection.add(HttpUtils.convertToSurveyGroupFrom(sg)));
        
        //return resourceCollection;
        //return Response.ok(resourceCollection).status(200).build();
        
        
		/* URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{surveyGroupId}")
                .buildAndExpand(surveyGroup.getGuid()).toUri();*/
        return ResponseEntity.ok().body(surveyGroups).status(200).build();
    }

    @Operation(summary = "Get the survey group matching the given ID.", description = "", tags = { "surveygroup" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The survey group matching the ID in the URL."),
            @ApiResponse(responseCode = "401", description = "A survey group with the given ID was not found.")
        })
    @GetMapping("/surveygroups/{surveyGroupId}")
    public Response getSurveyGroup(@PathParam String surveyGroupId) {
        
        SurveyGroup surveyGroup = this.surveyService.getSurveyGroup(surveyGroupId);
        if (surveyGroup == null) {
            //return ResponseEntity.notFound().build();
        	return Response.noContent().status(401).build();
        }

        //SurveyGroupResource resource = HttpMapper.MAPPER.convertToSurveyGroupResourceFrom(surveyGroup);
        SurveyGroupResource resource = HttpMapper.MAPPER.convertToSurveyGroupResourceFrom(surveyGroup);
     	
        //return ResponseEntity.ok().body(resource);
        return Response.ok(resource).status(200).build();
    }

    @Operation(summary = "Update the survey group.", description = "", tags = { "surveygroup" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The survey group was successfully edited."),
            @ApiResponse(responseCode = "401", description = "A survey group with the given ID was not found.")
        })
    @PutMapping("/surveygroups/{surveyGroupId}")
    public ResponseEntity<SurveyGroupResource> saveSurveyGroup(@PathVariable String surveyGroupId, 
        @RequestBody SurveyGroupResource surveyGroupResource) {
        
        SurveyGroup surveyGroup = surveyService.getSurveyGroup(surveyGroupId);
        
        if (surveyGroup == null) {
            return ResponseEntity.notFound().build();
        }

        //SurveyGroup newSurveyGroup = HttpMapper.MAPPER.convertToSurveyGroupFrom(surveyGroupResource);
        SurveyGroup newSurveyGroup = HttpUtils.convertToSurveyGroupFrom(surveyGroupResource);
        surveyGroup = surveyService.updateSurveyGroup(surveyGroup, newSurveyGroup);

        //SurveyGroupResource resource = HttpMapper.MAPPER.convertToSurveyGroupResourceFrom(surveyGroup);
        SurveyGroupResource resource = HttpMapper.MAPPER.convertToSurveyGroupResourceFrom(surveyGroup);
    
        return ResponseEntity.ok().body(resource);
    }
}
