package com.redhat.appdevpractice.samples.survey.service;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import com.redhat.appdevpractice.samples.survey.exception.ResourceNotFoundException;
import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;
import com.redhat.appdevpractice.samples.survey.repository.SurveyGroupRepository;

import org.springframework.beans.factory.annotation.Autowired;

@ApplicationScoped
public class SurveyServiceImpl implements SurveyService {

    @Inject
    private SurveyGroupRepository surveyGroupRepository;

    @Autowired
    public SurveyServiceImpl(SurveyGroupRepository surveyGroupRepository) {
        this.surveyGroupRepository = surveyGroupRepository;
    }

    @Transactional
    public SurveyGroup createSurveyGroup(SurveyGroup surveyGroup) {
        String guid = UUID.randomUUID().toString();
        surveyGroup.setGuid(guid);
        surveyGroupRepository.persistAndFlush(surveyGroup);
        return surveyGroupRepository.findByGuid(guid);
    }

    public List<SurveyGroup> getSurveyGroups() {
        List<SurveyGroup> surveyGroups = surveyGroupRepository.findAll().list();
        return surveyGroups;
    }

    @Transactional
    public SurveyGroup getSurveyGroup(String surveyGroupGuid) {
        try {
            return surveyGroupRepository.findByGuid(surveyGroupGuid);
        } catch (NoResultException e) {
            throw new ResourceNotFoundException("The survey group does not exist.");
        }
    }

    @Override
    @Transactional
    public SurveyGroup updateSurveyGroup(SurveyGroup oldSurveyGroup, SurveyGroup newSurveyGroup) {
        oldSurveyGroup.updateWith(newSurveyGroup);
        surveyGroupRepository.persistAndFlush(oldSurveyGroup);
        return oldSurveyGroup;
    }

}