package com.redhat.appdevpractice.samples.survey.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.redhat.appdevpractice.samples.survey.model.Skill;
import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;

//@DataJpaTest
//@ActiveProfiles("test")
@QuarkusTest
public class SurveyGroupRepositoryIntegrationTest {

    @Autowired
    private SurveyGroupRepository surveyGroupRepository;

    @Test
    public void shouldPersistSurveyGroup() {

        SurveyGroup surveyGroup = new SurveyGroup();
        surveyGroup.setGuid("guid123");

        surveyGroup = this.surveyGroupRepository.saveAndFlush(surveyGroup);
        assertNotNull(surveyGroup.getId());
        assertTrue(surveyGroup.getGuid().equals("guid123"));
    }

    @Test
    public void shouldReturnNullIfGuidNotFound() {
        assertNull(this.surveyGroupRepository.findByGuid("guid124"));
    }

    @Test
    public void shouldFindSurveyGroupByGuid() {

        SurveyGroup surveyGroup = new SurveyGroup();
        surveyGroup.setGuid("guid123");

        this.surveyGroupRepository.saveAndFlush(surveyGroup);
        
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

        nasa = this.surveyGroupRepository.saveAndFlush(nasa);
        hogwarts = this.surveyGroupRepository.saveAndFlush(hogwarts);

        assertEquals(nasa.getSkillsUsed().get(0), hogwarts.getSkillsUsed().get(0));
    }

    @Test
    public void removingSurveyGroupShouldNotRemoveSkillFromADifferentSurveyGroup() {

        Skill tdd = new Skill("TDD", "Ability to do TDD.", "Development");

        SurveyGroup nasa = new SurveyGroup();
        nasa.setGuid("nasa");
        nasa.getSkillsUsed().add(tdd);

        SurveyGroup hogwarts = new SurveyGroup();
        hogwarts.setGuid("hogwarts");
        hogwarts.getSkillsUsed().add(tdd);

        this.surveyGroupRepository.saveAndFlush(nasa);
        this.surveyGroupRepository.saveAndFlush(hogwarts);

        this.surveyGroupRepository.deleteByGuid("nasa");

        assertNull(this.surveyGroupRepository.findByGuid("nasa"));

        hogwarts = this.surveyGroupRepository.findByGuid("hogwarts");

        assertNotNull(hogwarts);
        assertTrue(hogwarts.getSkillsUsed().size() == 1);
        assertTrue(hogwarts.getSkillsUsed().get(0).equals(tdd));

    }
}