package com.redhat.appdevpractice.samples.mockBeans;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import com.redhat.appdevpractice.samples.survey.exception.ResourceNotFoundException;
import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;
import com.redhat.appdevpractice.samples.survey.repository.SurveyGroupRepository;
import com.redhat.appdevpractice.samples.survey.service.SurveyService;

import io.quarkus.test.Mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@ApplicationScoped
@Mock
public class MockSurveyServiceImpl implements MockSurveyService {

    private SurveyGroupRepository surveyGroupRepository;

    @Autowired
    public MockSurveyServiceImpl(SurveyGroupRepository surveyGroupRepository) {
        this.surveyGroupRepository = surveyGroupRepository;
    }

    public SurveyGroup createSurveyGroup(SurveyGroup surveyGroup) {
        surveyGroup.setGuid(UUID.randomUUID().toString());
        return surveyGroupRepository.saveAndFlush(surveyGroup);
    }

    public List<SurveyGroup> getSurveyGroups() {
        return surveyGroupRepository.findAll();
    }

    public SurveyGroup getSurveyGroup(String surveyGroupGuid) {
        SurveyGroup surveyGroup = surveyGroupRepository.findByGuid(surveyGroupGuid);
        if( surveyGroup == null){
            throw new ResourceNotFoundException("The survey group does not exist.");
        }
        return surveyGroup;
    }

    @Override
    public SurveyGroup updateSurveyGroup(SurveyGroup oldSurveyGroup, SurveyGroup newSurveyGroup) {
        oldSurveyGroup.updateWith(newSurveyGroup);
        return this.surveyGroupRepository.saveAndFlush(oldSurveyGroup);
    }

}