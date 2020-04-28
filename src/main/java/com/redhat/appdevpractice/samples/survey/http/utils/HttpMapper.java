package com.redhat.appdevpractice.samples.survey.http.utils;

import com.redhat.appdevpractice.samples.survey.http.EmployeeAssignmentResource;
import com.redhat.appdevpractice.samples.survey.http.NewSurveyGroupResource;
import com.redhat.appdevpractice.samples.survey.http.SkillResource;
import com.redhat.appdevpractice.samples.survey.http.SurveyGroupResource;
import com.redhat.appdevpractice.samples.survey.model.EmployeeAssignment;
import com.redhat.appdevpractice.samples.survey.model.Skill;
import com.redhat.appdevpractice.samples.survey.model.SurveyGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface HttpMapper {
    HttpMapper MAPPER = Mappers.getMapper( HttpMapper.class );

    SurveyGroup convertToSurveyGroupFrom(NewSurveyGroupResource newSurveyGroup);

    SurveyGroup convertToSurveyGroupFrom(SurveyGroupResource surveyGroupResource);

    Skill convertToSkillFrom(SkillResource skillResource);

    EmployeeAssignment convertToEmployeeFrom(EmployeeAssignmentResource employeeAssignmentResource);

    @Mapping( source = "guid", target = "id")
    SurveyGroupResource convertToSurveyGroupResourceFrom(SurveyGroup surveyGroup);

    SkillResource convertToSkillResourceFrom(Skill skill);

    EmployeeAssignmentResource convertToEmployeeAssignmentResourceFrom(EmployeeAssignment ea);
}
