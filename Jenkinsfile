@Library('jenkins_lib')_
pipeline {
  agent {label 'slave'}
 
  environment {
    // Define global environment variables in this section

    buildNum = currentBuild.getNumber() ;
    buildType = BRANCH_NAME.split("/").first();
    branchVersion = BRANCH_NAME.split("/").last().toUpperCase();

    
    
    SLACK_CHANNEL = 'jenkins-cdap-alerts'
    CHECKSTYLE_FILE = 'target/javastyle-result.xml'
    UNIT_RESULT = 'target/surefire-reports/*.xml'
    COBERTURA_REPORT = 'target/site/cobertura/coverage.xml'
    ALLURE_REPORT = 'allure-report/'
    HTML_REPORT = 'index.html'

    SONAR_PATH = './'
  }
  parameters {
        booleanParam(name: 'DeployJar', defaultValue: false, description: 'Check this to Deploy mvn build artifacts to guavus artifactory.')
  }
    stages 
    {
        stage("Define Release Version") {
            steps {
                script {
                    //Global Lib for Environment Versions Definition
                    versionDefine()
                }
            }
        }

        stage("Push mvn build artifacts?") {
            steps {
                script {
                    PUSH_JAR = false;
                    if( params.DeployJar == true)
                    {
                        PUSH_JAR = true;
                    }
                }
            }
        }

        stage ("Compile, Build and Deploy Janusgraph")
        {
            stages {
                stage("Compile and build Janusgraph") {
                    steps {
                        script {
                            echo "Running Build"

                            sh "mvn clean install -U   -Dgpg.skip=true -DskipTests=true  -Dcheckstyle.skip=true -Drat.skip=true  -Dskip.solr_examples=true"
                        }
                    }
                }

                stage("Push JAR to Maven Artifactory") {
                    when {
                        expression { PUSH_JAR == true }
                    }
                    steps {
                        script {
                            echo "Pushing JAR to Maven Artifactory"

                            sh "mvn deploy -U -DskipTests=true -Dcheckstyle.skip=true -Drat.skip=true -Drat.ignoreErrors=true -Dfindbugs.skip=true -Dgpg.skip=true    -Dskip.solr_examples=true;"
                        }
                    }
                }

                
            }
        }
    }
    
    post {
       always {
            //Global Lib for Reports publishing
            reports_alerts(env.CHECKSTYLE_FILE, env.UNIT_RESULT, env.COBERTURA_REPORT, env.ALLURE_REPORT, env.HTML_REPORT)
 
            //Global Lib for post build actions eg: artifacts archive
            postBuild(env.ARCHIVE_ZIP_PATH_JANUSGRAPH)

            //Global Lib for slack alerts
            slackalert(env.SLACK_CHANNEL)
      }
    }
}
