package com.redhat.appdevpractice.samples.survey.repository;

import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*@Repository
public interface SurveyGroupRepository extends JpaRepository<SurveyGroup, Long> {

	SurveyGroup findByGuid(String surveyGroupGuid);

	void deleteByGuid(String string);
}*/

@ApplicationScoped
public class SurveyGroupRepository implements PanacheRepository<SurveyGroup> {

	public SurveyGroup findByGuid(String surveyGroupGuid) {
		// List<SurveyGroup> surveyGroup= this.listAll();
		// SurveyGroup result = new SurveyGroup();

		// for (SurveyGroup sg : surveyGroup) {
		// if (sg.getGuid() == surveyGroupGuid) {
		// result = sg;
		// }
		// }
		// return result;
		return find("guid", surveyGroupGuid).singleResult();
	}

	public void deleteByGuid(String string) {
		List<SurveyGroup> surveyGroup = this.listAll();
		SurveyGroup result = new SurveyGroup();

		for (SurveyGroup sg : surveyGroup) {
			if (sg.getGuid() == string) {
				this.delete(sg);
			}
		}

		// deleteByGuid(string);
	}

}
