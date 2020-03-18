package com.redhat.appdevpractice.samples.survey.http;

import java.util.ArrayList;
import java.util.List;

public class NewSurveyGroupResource {
    private String opportunityId;
    private String projectId;
    private String projectName;
    private String tsmId;
    private String projectCreatorId;

    private List<SkillResource> skillsUsed;
    private List<EmployeeAssignmentResource> employeeAssignments;

    public NewSurveyGroupResource() {
        this.skillsUsed = new ArrayList<>();
        this.employeeAssignments = new ArrayList<>();
    }

    public String getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(String opportunityId) {
        this.opportunityId = opportunityId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTsmId() {
        return tsmId;
    }

    public void setTsmId(String tsmId) {
        this.tsmId = tsmId;
    }

    public String getProjectCreatorId() {
        return projectCreatorId;
    }

    public void setProjectCreatorId(String projectCreatorId) {
        this.projectCreatorId = projectCreatorId;
    }

    public List<EmployeeAssignmentResource> getEmployeeAssignments() {
        return employeeAssignments;
    }

    public void setEmployeeAssignments(List<EmployeeAssignmentResource> employeeAssignments) {
        this.employeeAssignments = employeeAssignments;
    }

    public List<SkillResource> getSkillsUsed() {
        return skillsUsed;
    }

    public void setSkillsUsed(List<SkillResource> skillsUsed) {
        this.skillsUsed = skillsUsed;
    }
}