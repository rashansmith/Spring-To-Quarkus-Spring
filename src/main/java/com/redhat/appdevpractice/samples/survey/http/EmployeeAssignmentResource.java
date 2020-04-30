package com.redhat.appdevpractice.samples.survey.http;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EmployeeAssignmentResource {

    private String email;
    private String role;
    @JsonFormat(pattern = "YYYY-MM-dd")
    private LocalDate startProjectDate;
    @JsonFormat(pattern = "YYYY-MM-dd")
    private LocalDate endProjectDate;

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
