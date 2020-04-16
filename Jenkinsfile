pipeline {
    agent {
        label "jenkins-slave-mvn"
    }
    stages {
        stage('Build') {
            steps {
        		echo '****** Compilation and Testing of Spring CRUD Service ******'
        		echo '****** Show Maven Wrapper Version ******'
        		sh './mvnw -v'
                echo '****** mvn test ******'
        		sh './mvnw test'
                echo '****** mvn test ******'
        		sh './mvnw sonar:sonar -Dsonar.host.url=https://sonarqube-app-dev-ci-cd.apps.shared-dev.dev.openshift.opentlc.com/ -Dsonar.login=34e38f4789b25a558f9ecaf36047381dad86f75e'
        		echo '****** mvn clean package ******'
        		sh './mvnw package -DskipTests'
                sh 'mkdir deployments' 
                sh 'cp target/*.jar deployments/' 
            }
        }
        
        stage('Promote to Dev') { 
            steps {
                echo '***** Promoting Spring CRUD Service to DEV *****' 
                sh 'ls target'
                script {
                    openshift.withCluster() {
                        openshift.withProject('appdev-example-ci-cd') {
                            	openshift.selector("bc", "survey-service").startBuild("--from-dir=deployments")
				                openshift.tag("survey-service:latest", "appdev-example-dev/survey-service:latest")
                        }
                    }
                }
            }
        }
    }
}

def getJenkinsMaster() {
    return env.BUILD_URL.split('/')[2].split(':')[0]
}

def getURL() {
    return "http://" + getJenkinsMaster() + ":8080"
}
