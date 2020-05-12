package com.redhat.appdevpractice.samples.survey.repository;

import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class SurveyGroupRepository implements PanacheRepository<SurveyGroup> {

	public SurveyGroup findByGuid(String surveyGroupGuid) {
		return find("guid", surveyGroupGuid).singleResult();
	}

	public void deleteByGuid(String string) {

		delete(findByGuid(string));
	}

}
