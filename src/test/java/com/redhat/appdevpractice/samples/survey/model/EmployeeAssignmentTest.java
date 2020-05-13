package com.redhat.appdevpractice.samples.survey.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class EmployeeAssignmentTest {

	EmployeeAssignment john;
	EmployeeAssignment sara;

	@BeforeEach
	public void setup() {

		john = new EmployeeAssignment("john@redhat.com", "consultant", "UUID123", LocalDate.now(),
				LocalDate.now().plusDays(60));

		sara = new EmployeeAssignment("sara@redhat.com", "consultant", "UUID312", LocalDate.now().minusDays(30),
				LocalDate.now().plusDays(60));
	}

	@Test
	public void employeeShouldBeEqualWithSameEmail() {

		EmployeeAssignment sally = new EmployeeAssignment();
		sally.setEmail(sara.getEmail());
		sally.setStartProjectDate(sara.getStartProjectDate());
		sally.setEndProjectDate(sara.getEndProjectDate());

		assertEquals(sally, sara, "Employees with same email should be equal.");
		assertNotEquals(john, sara, "Different email addresses should cause employees to not be equal.");
	}

	@Test
	public void hashCodeShouldBeEqual() {

		EmployeeAssignment sally = new EmployeeAssignment();
		sally.setEmail(sara.getEmail());
		sally.setStartProjectDate(sara.getStartProjectDate());
		sally.setEndProjectDate(sara.getEndProjectDate());

		assertEquals(sally.hashCode(), sara.hashCode(), "Employees with same email should have the same hashCode.");
		assertNotEquals(john.hashCode(), sara.hashCode(),
				"Employees with different email should not have the same hashCode.");
	}
}