INSERT into  SKILL (ID, CATEGORY, NAME, DESCRIPTION) 
    VALUES 
        (1, 'Network Automation', 'Ansible Network Automation', 'Knowledge of network modules in Ansible'),
        (2, 'Web Services', 'Rest API', 'Ability to build out and understand technologies around REST API'),
        (3, 'Container Platforms', 'Openshift 4', 'Understanding of the Openshift 4 platform');

INSERT into  SURVEY_GROUP (ID, GUID, OPPORTUNITY_ID, PROJECT_CREATOR_ID, PROJECT_ID, PROJECT_NAME, TSM_ID) 
    VALUES 
        (6, 'ada229a8-07b7-4d03-a9dd-2bc03771a4d6', '1_oPP_iD', '1_pROJ_cREATOR', '1_pROJ_iD', 'Example Project', '1_tSM_iD');



INSERT into  SURVEYGROUP_SKILLS (ID, SKILL_ID) 
    VALUES 
        (6, 1),
        (6, 2),
        (6, 3);


INSERT into  EMPLOYEE_ASSIGNMENT (ID, EMAIL, EMPLOYEE_ID, END_PROJECT_DATE, ROLE, START_PROJECT_DATE) 
    VALUES 
        (7, 'joe@example.com', '53ce0458-5470-4fe9-b8cb-f25cf484da4b', now(), 'consultant', now());

INSERT into  SURVEYGROUP_EMPLOYEE_ASSIGNMENTS (ID, EMPLOYEE_ASSIGNMENT_ID) 
    VALUES 
        (6, 7);