package com.redhat.appdevpractice.samples.survey.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class SurveyGroup extends PanacheEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String guid;

    private String opportunityId;

    private String projectId;

    private String projectName;

    private String tsmId;

    private String projectCreatorId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "surveygroup_employee_assignments", 
        joinColumns = { @JoinColumn(name = "id") }, 
        inverseJoinColumns = { @JoinColumn(name = "employee_assignment_id") })
    private List<EmployeeAssignment> employeeAssignments;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "surveygroup_skills", 
        joinColumns = { @JoinColumn(name = "id") }, 
        inverseJoinColumns = { @JoinColumn(name = "skill_id") })
    private List<Skill> skillsUsed;

    public SurveyGroup() {
        this.employeeAssignments = new ArrayList<>();
        this.skillsUsed = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
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

    public List<EmployeeAssignment> getEmployeeAssignments() {
        return employeeAssignments;
    }



	public void setEmployeeAssignments(List<EmployeeAssignment> employeeAssignments) {
        this.employeeAssignments = employeeAssignments;
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

    public List<Skill> getSkillsUsed() {
        return skillsUsed;
    }

    public void setSkillsUsed(List<Skill> skillsUsed) {
        this.skillsUsed = skillsUsed;
    }

	public void updateWith(SurveyGroup newSurveyGroup) {
        this.opportunityId = newSurveyGroup.getOpportunityId();
        this.projectCreatorId = newSurveyGroup.getProjectCreatorId();
        this.projectId = newSurveyGroup.getProjectId();
        this.projectName = newSurveyGroup.getProjectName();
        this.tsmId = newSurveyGroup.getTsmId();

        this.skillsUsed = newSurveyGroup.getSkillsUsed();
        this.employeeAssignments = newSurveyGroup.getEmployeeAssignments();
	}

}