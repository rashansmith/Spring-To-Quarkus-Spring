package com.redhat.appdevpractice.samples.survey.utils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import com.redhat.appdevpractice.samples.survey.http.EmployeeAssignmentResource;
import com.redhat.appdevpractice.samples.survey.http.SkillResource;
import com.redhat.appdevpractice.samples.survey.http.SurveyGroupResource;

public class ResourceHelper {

    public static SurveyGroupResource getDefaultSurveyGroupResource() {
       
        SurveyGroupResource newSurveyGroupResource = new SurveyGroupResource();
        newSurveyGroupResource.setOpportunityId("Voldermort's Destruction");
        newSurveyGroupResource.setProjectId("Horcrux 1");
        newSurveyGroupResource.setProjectCreatorId("harrypotter@hogwarts.edu");
        newSurveyGroupResource.setTsmId("dumbledore@hogwarts");

        EmployeeAssignmentResource harry = new EmployeeAssignmentResource();
        harry.setEmail("harrypotter@hogwarts.edu");
        harry.setRole("student");
        harry.setStartProjectDate(LocalDate.now());
        harry.setEndProjectDate(LocalDate.now().plusDays(25));

        EmployeeAssignmentResource hermione = new EmployeeAssignmentResource();
        hermione.setEmail("hermione@hogwarts.edu");
        hermione.setRole("the best student");
        hermione.setStartProjectDate(LocalDate.now());
        hermione.setEndProjectDate(LocalDate.now().plusDays(25));

        newSurveyGroupResource.setEmployeeAssignments(Arrays.asList(harry, hermione));

        SkillResource skill = new SkillResource();
        skill.setSkill("defense of the dark arts");
        skill.setCategory("offensive");
        skill.setDescription("The ability to attack an opponent that uses dark magic.");

        newSurveyGroupResource.setSkillsUsed(Arrays.asList(skill));

        return newSurveyGroupResource;
    }

    public static EmployeeAssignmentResource generateEmployeeAssignment(String email) {
        
        EmployeeAssignmentResource employeeResource = new EmployeeAssignmentResource();
        employeeResource.setEmail(email);
        employeeResource.setRole("Consultant [+" + UUID.randomUUID().toString() + "]");
        employeeResource.setStartProjectDate(LocalDate.now());
        employeeResource.setEndProjectDate(LocalDate.now().plusDays(25));

        return employeeResource;
    }

    public static SkillResource generateSkillResource(String skill) {
        
        SkillResource skillResource = new SkillResource();
        skillResource.setSkill(skill);
        skillResource.setCategory(skill);
        skillResource.setDescription("Random description " + UUID.randomUUID().toString());

        return skillResource;
    }
}