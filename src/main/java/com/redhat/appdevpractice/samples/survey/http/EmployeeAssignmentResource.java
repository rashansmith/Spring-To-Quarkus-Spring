package com.redhat.appdevpractice.samples.survey.http;

import java.time.LocalDate;

public class EmployeeAssignmentResource {

    private String id;
    private String email;
    private String role;
    private LocalDate startProjectDate;
    private LocalDate endProjectDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
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

    
}
