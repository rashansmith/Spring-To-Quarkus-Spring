package com.redhat.appdevpractice.samples.mockBeans;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.redhat.appdevpractice.samples.survey.exception.ResourceNotFoundException;
import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;
import com.redhat.appdevpractice.samples.survey.repository.SurveyGroupRepository;
import com.redhat.appdevpractice.samples.survey.service.SurveyService;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.Mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@ApplicationScoped
@Mock
public class MockSurveyServiceImpl implements MockSurveyService {


	@Inject
    private SurveyGroupRepository surveyGroupRepository;
    
    //@Inject
    //EntityManager em;

    @Autowired
    public MockSurveyServiceImpl(SurveyGroupRepository surveyGroupRepository) {
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
		PanacheQuery<SurveyGroup> surveyGroup= surveyGroupRepository.findAll();
    	List<SurveyGroup> result = surveyGroup.list(); 
    	return result;
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