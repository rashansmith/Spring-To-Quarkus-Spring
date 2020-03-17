package com.redhat.appdevpractice.samples.survey.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.redhat.appdevpractice.samples.survey.exception.ResourceNotFoundException;
import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;
import com.redhat.appdevpractice.samples.survey.repository.SurveyGroupRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SurveyServiceImplTest {

    @Mock
    private SurveyGroupRepository repository;

    @InjectMocks
    private SurveyServiceImpl surveyService;

    @Test
    public void shouldCreateSurveyGroup() {

        SurveyGroup surveyGroup = new SurveyGroup();

        when(this.repository.saveAndFlush(surveyGroup)).thenReturn(surveyGroup);

        this.surveyService.createSurveyGroup(surveyGroup);

        verify(this.repository).saveAndFlush(surveyGroup);
    }

    @Test
    public void shouldGenerateUUID() {

        SurveyGroup surveyGroup = new SurveyGroup();

        when(this.repository.saveAndFlush(surveyGroup)).thenReturn(surveyGroup);

        this.surveyService.createSurveyGroup(surveyGroup);

        assertTrue(!surveyGroup.getGuid().isEmpty());
    }

    @Test
    public void shouldGetAllSurveyGroups() {

        this.surveyService.getSurveyGroups();
        verify(this.repository).findAll();
    }

    @Test
    public void shouldGetSurveyGroup() {
        
        String guid = "234234234";

        SurveyGroup surveyGroup = new SurveyGroup();
        when(this.repository.findByGuid(guid)).thenReturn(surveyGroup);

        SurveyGroup returned = this.surveyService.getSurveyGroup(guid);
        verify(this.repository).findByGuid(guid);

        assertEquals(surveyGroup, returned);
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionIfNoSurveyGroupMatchesGuid() {
        
        String guid = "234234234";

        when(this.repository.findByGuid(guid)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            this.surveyService.getSurveyGroup(guid);
        });
    }

}