package com.redhat.appdevpractice.samples.survey.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import com.redhat.appdevpractice.samples.survey.model.Skill;
import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@Transactional
@QuarkusTest
public class SurveyGroupRepositoryIntegrationTest {

    @Inject
    private SurveyGroupRepository surveyGroupRepository;

    @Test
    public void shouldPersistSurveyGroup() {

        SurveyGroup surveyGroup = new SurveyGroup();
        surveyGroup.setGuid("guid1234");
        this.surveyGroupRepository.persistAndFlush(surveyGroup);
        SurveyGroup sg = surveyGroupRepository.findByGuid("guid1234");
        assertTrue(sg.getGuid().equals("guid1234"));
    }

    @Test
    public void shouldReturnNullIfGuidNotFound() {
    	
    	Exception exception = assertThrows(NoResultException.class, () -> {
    		this.surveyGroupRepository.findByGuid("guid124");
    	   });
    	 
    	String expectedMessage = "No entity found for query";
    	String actualMessage = exception.getMessage();
    	 
    	assertTrue(actualMessage.contains(expectedMessage));
    }
    	
      

    @Test
    public void shouldFindSurveyGroupByGuid() {

        SurveyGroup surveyGroup = new SurveyGroup();
        surveyGroup.setGuid("guid123");

        this.surveyGroupRepository.persistAndFlush(surveyGroup);
        
        assertNotNull(this.surveyGroupRepository.findByGuid("guid123"));
    }

    @Test
    public void skillsShouldBelongToMultipleSurveyGroups() {

        Skill tdd = new Skill("TDD", "Ability to do TDD.", "Development");

        SurveyGroup nasa = new SurveyGroup();
        nasa.setGuid("nasa");
        nasa.getSkillsUsed().add(tdd);

        SurveyGroup hogwarts = new SurveyGroup();
        hogwarts.setGuid("hogwarts");
        hogwarts.getSkillsUsed().add(tdd);
        
        this.surveyGroupRepository.persistAndFlush(nasa);
        this.surveyGroupRepository.persistAndFlush(hogwarts);      
        
        assertEquals(nasa.getSkillsUsed().get(0), hogwarts.getSkillsUsed().get(0));
    }

    @Test
    public void removingSurveyGroupShouldNotRemoveSkillFromADifferentSurveyGroup() {

        Skill tdd = new Skill("TDD", "Ability to do TDD.", "Development");

        SurveyGroup nasa2 = new SurveyGroup();
        nasa2.setGuid("nasa2");
        nasa2.getSkillsUsed().add(tdd);

        SurveyGroup hogwarts2 = new SurveyGroup();
        hogwarts2.setGuid("hogwarts2");
        hogwarts2.getSkillsUsed().add(tdd);

        this.surveyGroupRepository.persistAndFlush(nasa2);
        this.surveyGroupRepository.persistAndFlush(hogwarts2);

        this.surveyGroupRepository.deleteByGuid("nasa2");

        Exception exception = assertThrows(NoResultException.class, () -> {
    		this.surveyGroupRepository.findByGuid("nasa2");
    	   });
    	 
    	String expectedMessage = "No entity found for query";
    	String actualMessage = exception.getMessage();
    	assertTrue(actualMessage.contains(expectedMessage));

        hogwarts2 = this.surveyGroupRepository.findByGuid("hogwarts2");

        assertNotNull(hogwarts2);
        assertTrue(hogwarts2.getSkillsUsed().size() == 1);
        assertTrue(hogwarts2.getSkillsUsed().get(0).equals(tdd));

    }
}