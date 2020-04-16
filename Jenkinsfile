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
        		sh './mvnw test -P dev'
                echo '****** mvn test ******'
        		sh './mvnw sonar:sonar -Dsonar.host.url=https://sonarqube-app-dev-ci-cd.apps.shared-dev.dev.openshift.opentlc.com/ -Dsonar.login=34e38f4789b25a558f9ecaf36047381dad86f75e'
        		echo '****** mvn clean package ******'
        		sh './mvnw package -DskipTests -P dev'
                sh 'mkdir deployments' 
                sh 'cp target/*.jar deployments/' 
            }
        }
        stage('Ensure Sonar Webhook is configured') {
            when {
                expression {
                    withSonarQubeEnv('sonar') {
                        def retVal = sh(returnStatus: true, script: "curl -u \"${SONAR_AUTH_TOKEN}:\" http://sonarqube:9000/api/webhooks/list | grep Jenkins")
                        echo "CURL COMMAND: ${retVal}"
                        return (retVal > 0)
                    }
                }
            }
            steps {
                 withSonarQubeEnv('sonar') {
                     sh "/usr/bin/curl -X POST -u \"${SONAR_AUTH_TOKEN}:\" -F \"name=Jenkins\" -F \"url=http://jenkins/sonarqube-webook/\" http://sonarqube:9000/api/webhooks/create"
                 }
            }
        }
        stage('Sonar Quality Gate') {
            steps {
                script {
                    withSonarQubeEnv('sonar') {
                        sh 'unset JAVA_TOOL_OPTIONS'
                    }
                    def qualityGate = waitForQualityGate()
                    if (qualityGate.status != "OK") {
                        error "Pipeline aborted due to quality gate failure: ${qualityGate.status}"
                    }
                }
            }
        }


        stage('Promote to Dev') { 
            steps {
                echo '***** Promoting Spring CRUD Service to DEV *****' 
                sh 'ls target'
                script {
                    openshift.withCluster() {
                        openshift.withProject('app-dev-ci-cd') {
                            	openshift.selector("bc", "survey-service").startBuild("--from-dir=deployments")
				                openshift.tag("survey-service:latest", "consultant-360-dev/survey-service:latest")
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
