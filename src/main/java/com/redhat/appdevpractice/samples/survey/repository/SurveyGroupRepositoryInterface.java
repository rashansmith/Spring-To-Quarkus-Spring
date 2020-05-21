package com.redhat.appdevpractice.samples.survey.repository;

import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface SurveyGroupRepositoryInterface {
	
	public SurveyGroup findByGuid(String surveyGroupGuid);

	public void deleteByGuid(String string);

}
