package com.redhat.appdevpractice.samples.survey.http.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.redhat.appdevpractice.samples.survey.http.EmployeeAssignmentResource;
import com.redhat.appdevpractice.samples.survey.http.NewSurveyGroupResource;
import com.redhat.appdevpractice.samples.survey.http.SkillResource;
import com.redhat.appdevpractice.samples.survey.http.SurveyGroupResource;
import com.redhat.appdevpractice.samples.survey.model.EmployeeAssignment;
import com.redhat.appdevpractice.samples.survey.model.Skill;
import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;

public class HttpUtils  {

    private HttpUtils() { }

    public static SurveyGroup convertToSurveyGroupFrom(NewSurveyGroupResource newSurveyGroup) {

        SurveyGroup group = new SurveyGroup();
        group.setOpportunityId(newSurveyGroup.getOpportunityId());
        group.setProjectId(newSurveyGroup.getProjectId());
        group.setProjectName(newSurveyGroup.getProjectName());
        group.setTsmId(newSurveyGroup.getTsmId());
        group.setProjectCreatorId(newSurveyGroup.getProjectCreatorId());

        List<EmployeeAssignment> employeeAssignments = new ArrayList<>();
        newSurveyGroup.getEmployeeAssignments()
                .forEach(employeeResource ->
            employeeAssignments.add(convertToEmployeeFrom(employeeResource)) );

        group.setEmployeeAssignments(employeeAssignments);

        List<Skill> skills = new ArrayList<>();
        newSurveyGroup.getSkillsUsed().forEach(skillResource -> 
            skills.add(convertToSkillFrom(skillResource)));

        group.setSkillsUsed(skills);

        return group;
    }
    
    public static SurveyGroup convertToSurveyGroupFrom(SurveyGroupResource surveyGroupResource) {

        SurveyGroup group = new SurveyGroup();
        group.setOpportunityId(surveyGroupResource.getOpportunityId());
        group.setProjectId(surveyGroupResource.getProjectId());
        group.setProjectName(surveyGroupResource.getProjectName());
        group.setTsmId(surveyGroupResource.getTsmId());
        group.setProjectCreatorId(surveyGroupResource.getProjectCreatorId());

        List<EmployeeAssignment> employeeAssignments = new ArrayList<>();
        surveyGroupResource.getEmployeeAssignments()
                .forEach(employeeResource ->
            employeeAssignments.add(convertToEmployeeFrom(employeeResource)) );

        group.setEmployeeAssignments(employeeAssignments);

        List<Skill> skills = new ArrayList<>();
        surveyGroupResource.getSkillsUsed().forEach(skillResource -> 
            skills.add(convertToSkillFrom(skillResource)));

        group.setSkillsUsed(skills);

        return group;
    }

    public static Skill convertToSkillFrom(SkillResource skillResource) {
        
        return new Skill(skillResource.getSkill(), skillResource.getDescription(), 
            skillResource.getCategory());
    }

    public static EmployeeAssignment convertToEmployeeFrom(EmployeeAssignmentResource employeeAssignmentResource) {
        
       return new EmployeeAssignment(employeeAssignmentResource.getEmail(), employeeAssignmentResource.getRole(), UUID.randomUUID().toString(),
       employeeAssignmentResource.getStartProjectDate(), employeeAssignmentResource.getStartProjectDate());
    }

    public static SurveyGroupResource convertToSurveyGroupResourceFrom(SurveyGroup surveyGroup) {
        SurveyGroupResource resource = new SurveyGroupResource();
        
        resource.setId(surveyGroup.getGuid());
        resource.setOpportunityId(surveyGroup.getOpportunityId());
        resource.setProjectId(surveyGroup.getProjectId());
        resource.setProjectName(surveyGroup.getProjectName());
        resource.setProjectCreatorId(surveyGroup.getProjectCreatorId());
        resource.setTsmId(surveyGroup.getTsmId());
        
        List<EmployeeAssignmentResource> employeeAssignments = new ArrayList<>();
        surveyGroup.getEmployeeAssignments().forEach(ea -> 
        employeeAssignments.add(convertToEmployeeAssignmentResourceFrom(ea)));

        resource.setEmployeeAssignments(employeeAssignments);

        List<SkillResource> skillResources = new ArrayList<>();
        surveyGroup.getSkillsUsed().forEach(skill -> skillResources.add(convertToSkillResourceFrom(skill)));

        resource.setSkillsUsed(skillResources);

        return resource;
    }

    public static SkillResource convertToSkillResourceFrom(Skill skill) {
        SkillResource skillResource = new SkillResource();
        skillResource.setSkill(skill.getName());
        skillResource.setCategory(skill.getCategory());
        skillResource.setDescription(skill.getDescription());

        return skillResource;
    }

    public static EmployeeAssignmentResource convertToEmployeeAssignmentResourceFrom(EmployeeAssignment ea) {
        
        EmployeeAssignmentResource employeeAssignmentResource = new EmployeeAssignmentResource();
        employeeAssignmentResource.setRole(ea.getRole());
        employeeAssignmentResource.setEmail(ea.getEmail());
        employeeAssignmentResource.setStartProjectDate(ea.getStartProjectDate());
        employeeAssignmentResource.setEndProjectDate(ea.getEndProjectDate());

        return employeeAssignmentResource;
    }

}
