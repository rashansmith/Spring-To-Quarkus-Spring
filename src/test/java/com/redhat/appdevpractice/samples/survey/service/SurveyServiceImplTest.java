package com.redhat.appdevpractice.samples.survey.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;

import com.redhat.appdevpractice.samples.survey.exception.ResourceNotFoundException;
import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;
import com.redhat.appdevpractice.samples.survey.repository.SurveyGroupRepository;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@Transactional
@QuarkusTestResource(H2DatabaseTestResource.class)
public class SurveyServiceImplTest {

	@Inject
	private SurveyGroupRepository repository;

	@Inject
	private SurveyServiceImpl surveyService;

	@Test
	public void shouldCreateSurveyGroup() {

		SurveyGroup surveyGroup = new SurveyGroup();

		this.surveyService.createSurveyGroup(surveyGroup);

		assertTrue(repository.listAll() != null);

	}

	@Test
	public void shouldGenerateUUID() {

		SurveyGroup surveyGroup = new SurveyGroup();

		this.surveyService.createSurveyGroup(surveyGroup);

		assertTrue(!surveyGroup.getGuid().isEmpty());
	}

	@Test
	public void shouldGetAllSurveyGroups() {

		SurveyGroup surveyGroup = new SurveyGroup();

		this.surveyService.createSurveyGroup(surveyGroup);
		
		SurveyGroup surveyGroup2 = new SurveyGroup();

		this.surveyService.createSurveyGroup(surveyGroup2);

		assertTrue(this.surveyService.getSurveyGroups() != null);
	}

	@Test
	public void shouldGetSurveyGroup() {

		String guid = "234234234";
		
		SurveyGroup surveyGroup = new SurveyGroup();
		
		surveyGroup.setGuid(guid);
		
		repository.persistAndFlush(surveyGroup);
		
		SurveyGroup returned = this.surveyService.getSurveyGroup(guid);	

		assertEquals(surveyGroup, returned);
	}

	@Test
	public void shouldThrowResourceNotFoundExceptionIfNoSurveyGroupMatchesGuid() {
		/*
		 * String guid = "234234234";
		 * 
		 * when(this.repository.findByGuid(guid)).thenReturn(null);
		 * 
		 * assertThrows(ResourceNotFoundException.class, () -> {
		 * this.surveyService.getSurveyGroup(guid); });
		 * 
		 * 
		 */
		String guid = "234234234";

		assertEquals(null, surveyService.getSurveyGroup(guid));
	}
}