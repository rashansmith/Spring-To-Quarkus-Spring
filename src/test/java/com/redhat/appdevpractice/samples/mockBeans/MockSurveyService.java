package com.redhat.appdevpractice.samples.mockBeans;

import java.util.List;

import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;

public interface MockSurveyService {

	public SurveyGroup createSurveyGroup(SurveyGroup surveyGroup);

	public SurveyGroup updateSurveyGroup(SurveyGroup oldSurveyGroup, SurveyGroup newSurveyGroup);

	public List<SurveyGroup> getSurveyGroups();

	public SurveyGroup getSurveyGroup(String surveyGroupGuid);
}