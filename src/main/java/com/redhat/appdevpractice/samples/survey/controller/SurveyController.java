package com.redhat.appdevpractice.samples.survey.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.redhat.appdevpractice.samples.survey.http.NewSurveyGroupResource;
import com.redhat.appdevpractice.samples.survey.http.SurveyGroupResource;
import com.redhat.appdevpractice.samples.survey.http.utils.HttpUtils;
import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;
import com.redhat.appdevpractice.samples.survey.service.SurveyServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin
@RestController
public class SurveyController {

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
    public ResponseEntity<String> createSurvey(@RequestBody NewSurveyGroupResource newSurveyGroup) {

        SurveyGroup surveyGroup = HttpUtils.convertToSurveyGroupFrom(newSurveyGroup);

        surveyGroup = this.surveyService.createSurveyGroup(surveyGroup);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{surveyGroupId}")
                .buildAndExpand(surveyGroup.getGuid()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Get all the survey groups", description = "", tags = { "surveygroup" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "An array of all survey groups in the system. If no survey groups exist, an empty list.")
        })
    @GetMapping("/surveygroups")
    public List<SurveyGroupResource> getSurveyGroups() {
        
        List<SurveyGroup> surveyGroups = surveyService.getSurveyGroups();
        
        List<SurveyGroupResource> resourceCollection = new ArrayList<>();
        surveyGroups.forEach(sg -> resourceCollection.add(HttpUtils.convertToSurveyGroupResourceFrom(sg)));
        
        return resourceCollection;
    }

    @Operation(summary = "Get the survey group matching the given ID.", description = "", tags = { "surveygroup" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The survey group matching the ID in the URL."),
            @ApiResponse(responseCode = "401", description = "A survey group with the given ID was not found.")
        })
    @GetMapping("/surveygroups/{surveyGroupId}")
    public ResponseEntity<SurveyGroupResource> getSurveyGroup(@PathVariable String surveyGroupId) {
        
        SurveyGroup surveyGroup = surveyService.getSurveyGroup(surveyGroupId);
        if (surveyGroup == null) {
            return ResponseEntity.notFound().build();
        }

        SurveyGroupResource resource = HttpUtils.convertToSurveyGroupResourceFrom(surveyGroup);
    
        return ResponseEntity.ok().body(resource);
    }
}
