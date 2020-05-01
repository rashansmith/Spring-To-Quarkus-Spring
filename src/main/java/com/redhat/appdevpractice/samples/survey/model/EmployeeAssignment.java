package com.redhat.appdevpractice.samples.survey.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class EmployeeAssignment extends PanacheEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String employeeId;
    private String email;
    private String role;
    @JsonFormat(pattern = "YYYY-MM-dd")
    private LocalDate startProjectDate;
    @JsonFormat(pattern = "YYYY-MM-dd")
    private LocalDate endProjectDate;

    @ManyToOne
    @JoinTable(name = "surveygroup_employee", 
        joinColumns = { @JoinColumn(name = "employee_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "id") })
    private SurveyGroup surveyGroup;
    public EmployeeAssignment() { }

    public EmployeeAssignment(String email, String role, String employeeId,
                    LocalDate startProjectDate, LocalDate endProjectDate) {

        this.email = email;
        this.role = role;
        this.startProjectDate = startProjectDate;
        this.endProjectDate = endProjectDate;
        this.employeeId = employeeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getStartProjectDate() {
        return startProjectDate;
    }

    public void setStartProjectDate(LocalDate startProjectDate) {
        this.startProjectDate = startProjectDate;
    }

    public LocalDate getEndProjectDate() {
        return endProjectDate;
    }

    public void setEndProjectDate(LocalDate endProjectDate) {
        this.endProjectDate = endProjectDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((endProjectDate == null) ? 0 : endProjectDate.hashCode());
        result = prime * result + ((startProjectDate == null) ? 0 : startProjectDate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EmployeeAssignment other = (EmployeeAssignment) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (endProjectDate == null) {
            if (other.endProjectDate != null)
                return false;
        } else if (!endProjectDate.equals(other.endProjectDate))
            return false;
        if (startProjectDate == null) {
            if (other.startProjectDate != null)
                return false;
        } else if (!startProjectDate.equals(other.startProjectDate))
            return false;
        return true;
    }
    
}
