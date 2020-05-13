package com.redhat.appdevpractice.samples.mockBeans;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.redhat.appdevpractice.samples.survey.exception.ResourceNotFoundException;
import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;
import com.redhat.appdevpractice.samples.survey.repository.SurveyGroupRepository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.Mock;

import org.springframework.beans.factory.annotation.Autowired;

@ApplicationScoped
@Mock
public class MockSurveyServiceImpl implements MockSurveyService {

	@Inject
	private SurveyGroupRepository surveyGroupRepository;

	@Autowired
	public MockSurveyServiceImpl(SurveyGroupRepository surveyGroupRepository) {
		this.surveyGroupRepository = surveyGroupRepository;
	}

	@Transactional
	public SurveyGroup createSurveyGroup(SurveyGroup surveyGroup) {
		String guid = UUID.randomUUID().toString();
		surveyGroup.setGuid(guid);
		surveyGroupRepository.persistAndFlush(surveyGroup);
		return surveyGroupRepository.findByGuid(guid);
	}

	@Transactional
	public List<SurveyGroup> getSurveyGroups() {
		PanacheQuery<SurveyGroup> surveyGroup = surveyGroupRepository.findAll();
		List<SurveyGroup> result = surveyGroup.list();
		return result;
	}

	@Transactional
	public SurveyGroup getSurveyGroup(String surveyGroupGuid) {
		SurveyGroup surveyGroup = surveyGroupRepository.findByGuid(surveyGroupGuid);

		if (surveyGroup == null) {
			throw new ResourceNotFoundException("The survey group does not exist.");
		}
		return surveyGroup;
	}

	@Override
	public SurveyGroup updateSurveyGroup(SurveyGroup oldSurveyGroup, SurveyGroup newSurveyGroup) {
		oldSurveyGroup.updateWith(newSurveyGroup);
		surveyGroupRepository.persistAndFlush(oldSurveyGroup);
		return oldSurveyGroup;
	}
}