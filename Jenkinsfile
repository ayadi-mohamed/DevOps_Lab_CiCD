#!/usr/bin/env groovy

def gv

pipeline {
    agent any
    environment {
        DEPLOYMENT_SERVER_IP = "deployment"
        DEPLOYMENT_SERVER_USER= "ayadinou"
        SONARQUBE_SERVER_IP ="sonarqube"
        SONARQUBE_SERVER_USER="admin"
        JENKINS_SERVER_IP ="jenkins_container"
        JENKINS_SERVER_USER="ayadinou"
        IMAGE_VERSION="1.0.0"
    }
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
        stage("SonarQube Testing and Scan") {
            steps {
                script {
                    echo 'Testing and scaning  '

                  //  gv.sonarScan("${SONARQUBE_SERVER_IP}","${SONARQUBE_SERVER_USER}") 
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
        stage("Push JAR to Nexus"){
            steps {
                script {
                     echo 'Pushing to nexus'
                  /*   gv.pushToNexus()  */
                }
            }
        }
        stage("build image") {
            steps {
                script {
                    gv.buildImage()
                }
            }
        }
        stage("deploy") {
            steps {
                script {
                    echo 'Deploying '

                    /* gv.deployApp("${DEPLOYMENT_SERVER_IP}","${DEPLOYMENT_SERVER_USER}")  */
                }
            }
        }

    }

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
        }
    }*/
}
