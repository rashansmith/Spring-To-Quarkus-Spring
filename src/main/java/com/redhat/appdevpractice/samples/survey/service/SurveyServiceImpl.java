package com.redhat.appdevpractice.samples.survey.service;

import java.util.List;
import java.util.UUID;

import com.redhat.appdevpractice.samples.survey.exception.ResourceNotFoundException;
import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;
import com.redhat.appdevpractice.samples.survey.repository.SurveyGroupRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurveyServiceImpl implements SurveyService {

    private SurveyGroupRepository surveyGroupRepository;

    @Autowired
    public SurveyServiceImpl(SurveyGroupRepository surveyGroupRepository) {
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