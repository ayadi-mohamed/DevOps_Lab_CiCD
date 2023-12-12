#!/usr/bin/env groovy

def gv

pipeline {
    agent any
   
    tools {
        maven 'maven'
    }
    stages {
        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        
        stage('increment version') {
            steps {
                script {
                    echo 'incrementing app version...'
                    sh 'mvn build-helper:parse-version versions:set \
                        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                        versions:commit'
                    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                    def version = matcher[0][1]
                    env.IMAGE_VERSION = "$version-$BUILD_NUMBER"
                
                }
            }
        }
        /*stage("SonarQube Testing and Scan") {
            steps {
                script {
                    echo 'Testing and scaning  '

                  //  gv.sonarScan("${SONARQUBE_SERVER_IP}","${SONARQUBE_SERVER_USER}") 
                }
            }
        }*/
        stage("SonarQube Analysis") {
                    steps {
                        script {
                            gv.sonarScan()
                        }
                    }
                }

        
        stage("build JAR"){

            steps {
                script {
                    gv.buildJar()
                }
            }
        }
        /*
        stage("Push JAR to Nexus"){
            steps {
                script {
                     echo 'Pushing to nexus'
                     gv.pushToNexus()  
                }
            }
        }*/
        stage("build image") {
            steps {
                script {
                    gv.buildImage()
                }
            }
        }
        stage("Trivy Scan") {
            steps {
                script {
                    gv.trivyScan()
                }
            }
        }
        stage("Push to deployment github") {
            steps {
                script {
                    gv.pushDeploymentGithub()
                }
            }
        }
        
        /*
        stage("deploy") {
            steps {
                script {
                    echo 'Deploying '

                     gv.deployApp("${DEPLOYMENT_SERVER_IP}","${DEPLOYMENT_SERVER_USER}")  
                }
            }
        }

    }
*/

 /*    post {
        success {
            script {
                echo 'removing the old images from the Jenkins server..'
                gv.cleanUntaggedImages("${JENKINS_SERVER_IP}","${JENKINS_SERVER_USER}")
                //emailext body: 'Your backend pipeline finished the buit and deployment of the project successfully', recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']], subject: 'Success of digihunt pipeline stages'

            }
        }
        failure {
            script {
                echo 'removing the old images from the Jenkins server..'
                gv.cleanUntaggedImages("${JENKINS_SERVER_IP}","${JENKINS_SERVER_USER}")
                //emailext body: 'Your backend pipeline failed the built and deployment of the project successfully', recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']], subject: 'Failure of digihunt pipeline stages'

            }
        }*/
    }
}
