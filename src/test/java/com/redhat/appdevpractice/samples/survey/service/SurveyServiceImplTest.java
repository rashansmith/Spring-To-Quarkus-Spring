package com.redhat.appdevpractice.samples.survey.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.transaction.Transactional;

import com.redhat.appdevpractice.samples.mockBeans.MockSurveyServiceImpl;
import com.redhat.appdevpractice.samples.survey.exception.ResourceNotFoundException;
import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;
import com.redhat.appdevpractice.samples.survey.repository.SurveyGroupRepository;

import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

@Transactional
@QuarkusTest
public class SurveyServiceImplTest {

	@InjectMocks
	private SurveyGroupRepository repository;

	@InjectMocks
	private MockSurveyServiceImpl surveyService;

	@BeforeAll
	public static void initialize() {
		MockSurveyServiceImpl mock = Mockito.mock(MockSurveyServiceImpl.class);
		QuarkusMock.installMockForType(mock, MockSurveyServiceImpl.class);
	}

	@Test
	public void shouldCreateSurveyGroup() {

		SurveyGroup surveyGroup = new SurveyGroup();

		this.surveyService.createSurveyGroup(surveyGroup);

		verify(this.repository).persistAndFlush(surveyGroup);

	}

	@Test
	public void shouldGenerateUUID() {

		SurveyGroup surveyGroup = new SurveyGroup();

		this.surveyService.createSurveyGroup(surveyGroup);

		assertTrue(!surveyGroup.getGuid().isEmpty());
	}

	@Test
	public void shouldGetAllSurveyGroups() {

		this.surveyService.getSurveyGroups();

		verify(this.repository).listAll();
	}

	@Test
	public void shouldGetSurveyGroup() {

		String guid = "234234234";

		SurveyGroup surveyGroup = new SurveyGroup();
		Mockito.when(this.repository.findByGuid(guid)).thenReturn(surveyGroup);
		when(this.repository.findByGuid(guid)).thenCallRealMethod();
		SurveyGroup returned = this.surveyService.getSurveyGroup(guid);
		verify(this.repository).findByGuid(guid);

		assertEquals(surveyGroup, returned);
	}

	@Test
	public void shouldThrowResourceNotFoundExceptionIfNoSurveyGroupMatchesGuid() {

		String guid = "234234234";

		Mockito.when(this.repository.findByGuid(guid)).thenReturn(null);

		assertThrows(ResourceNotFoundException.class, () -> {
			this.surveyService.getSurveyGroup(guid);
		});
	}

}