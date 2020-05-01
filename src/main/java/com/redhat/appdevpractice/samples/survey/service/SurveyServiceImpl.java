package com.redhat.appdevpractice.samples.survey.service;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.redhat.appdevpractice.samples.survey.exception.ResourceNotFoundException;
import com.redhat.appdevpractice.samples.survey.http.utils.HttpUtils;
import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;
import com.redhat.appdevpractice.samples.survey.repository.SurveyGroupRepository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@ApplicationScoped
public class SurveyServiceImpl implements SurveyService {

	@Inject
    private SurveyGroupRepository surveyGroupRepository;
    
    //@Inject
    //EntityManager em;

    @Autowired
    public SurveyServiceImpl(SurveyGroupRepository surveyGroupRepository) {
        this.surveyGroupRepository = surveyGroupRepository;
    }

    @Transactional
    public SurveyGroup createSurveyGroup(SurveyGroup surveyGroup) {
    	String guid = UUID.randomUUID().toString();
    	surveyGroup.setGuid(guid);
        //surveyGroup.setGuid(UUID.randomUUID().toString());
        //em.persist(surveyGroup);
        surveyGroupRepository.persistAndFlush(surveyGroup);
        return surveyGroupRepository.findByGuid(guid);
        //return surveyGroupRepository.saveandflush(surveyGroup);
    }

    @Transactional
    public List<SurveyGroup> getSurveyGroups() {
        //return surveyGroupRepository.findAll();
		List<SurveyGroup> surveyGroups = surveyGroupRepository.findAll().list();
    	return surveyGroups;
    }

    @Transactional
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
        //em.persist(oldSurveyGroup);
        //return this.surveyGroupRepository.saveandflush(oldSurveyGroup);
        surveyGroupRepository.persistAndFlush(oldSurveyGroup);
        return oldSurveyGroup;
    }

}