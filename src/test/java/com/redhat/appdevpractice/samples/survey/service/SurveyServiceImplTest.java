package com.redhat.appdevpractice.samples.survey.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.inject.Inject;
import javax.transaction.Transactional;

import com.redhat.appdevpractice.samples.survey.controller.SurveyController;
import com.redhat.appdevpractice.samples.survey.exception.ResourceNotFoundException;
import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;
import com.redhat.appdevpractice.samples.survey.repository.SurveyGroupRepository;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

@QuarkusTest
@Transactional
@QuarkusTestResource(H2DatabaseTestResource.class)
public class SurveyServiceImplTest {

	@InjectMock
	private SurveyGroupRepository repository;

	@InjectMock
	private SurveyServiceImpl surveyService;

	/*@BeforeEach
	public void setup() {
		surveyService = Mockito.mock(SurveyServiceImpl.class);
		repository = Mockito.mock(SurveyGroupRepository.class);
	}*/

	
	  @Test public void shouldCreateSurveyGroup() {
	  
	  SurveyGroup surveyGroup = new SurveyGroup();
	  
	  this.surveyService.createSurveyGroup(surveyGroup);
	  
	  verify(this.repository).persistAndFlush(surveyGroup);
	  
	  }
	  
	  @Test public void shouldGenerateUUID() {
	  
	  SurveyGroup surveyGroup = new SurveyGroup();
	  
	  this.surveyService.createSurveyGroup(surveyGroup);
	  
	  assertTrue(!surveyGroup.getGuid().isEmpty()); }
	  
	  @Test public void shouldGetAllSurveyGroups() {
	  
	  this.surveyService.getSurveyGroups();
	  
	  verify(this.repository).listAll(); }
	  
	  @Test public void shouldGetSurveyGroup() {
	  
	  String guid = "234234234";
	  
	  SurveyGroup surveyGroup = new SurveyGroup(); surveyGroup.setGuid(guid);
	  //when(this.repository.findByGuid(guid)).thenReturn(surveyGroup);
	  //when(this.repository.findByGuid(guid)).thenCallRealMethod();
	  repository.persistAndFlush(surveyGroup); SurveyGroup returned =
	  this.surveyService.getSurveyGroup(guid);
	  verify(this.repository).findByGuid(guid);
	  
	  assertEquals(surveyGroup, returned); }
	  
	  @Test public void
	  shouldThrowResourceNotFoundExceptionIfNoSurveyGroupMatchesGuid() {
	  
	  String guid = "234234234";
	  
	  when(this.repository.findByGuid(guid)).thenReturn(null);
	  
	  assertThrows(ResourceNotFoundException.class, () -> {
	  this.surveyService.getSurveyGroup(guid); }); }
	 

}