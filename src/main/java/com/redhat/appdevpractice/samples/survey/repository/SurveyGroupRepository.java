package com.redhat.appdevpractice.samples.survey.repository;

import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SurveyGroupRepository extends JpaRepository<SurveyGroup, Long> {

	SurveyGroup findByGuid(String surveyGroupGuid);

	void deleteByGuid(String string);
}

