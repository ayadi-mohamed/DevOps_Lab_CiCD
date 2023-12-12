
def buildImage() {
    echo "building the docker image..."
    sh "ls"
    sh "ls target/"
    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "docker build -t ayadinou/tp_devops_spring_boot_app:${IMAGE_VERSION} -t ayadinou/tp_devops_spring_boot_app:latest ."
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh "docker push ayadinou/tp_devops_spring_boot_app --all-tags"
    }
}

def buildJar() {
    echo "building the JAR."
    sh "mvn clean package -Dmaven.test.skip=true"

   
}

def sonarScan() {
    echo "Running SonarQube Scanner..."
    withSonarQubeEnv("secret-sonar") {
        sh "mvn verify sonar:sonar -Dsonar.projectKey=pet_store_pipeline_ci_dev -Dsonar.projectName=pet_store_pipeline_ci_develop"
    }
}
def trivyScan(){
    echo "Running Trivy Security Scan..."
    sh "trivy image --format template --template '@/usr/local/share/trivy/templates/html.tpl' -o TrivyReport.html ayadinou/tp_devops_spring_boot_app:${IMAGE_VERSION} --scanners vuln"
}


/*
def pushToNexus() {
    echo "pushing the jar file to Nexus maven-snapshots repo..."
    sh 'mvn clean deploy -Dmaven.test.skip=true'

      }



def deployApp(String serverIp, String serverUser) {
    echo 'deploying the application...'
    def composeRun = '"export MYSQLDB_USER=root MYSQLDB_ROOT_PASSWORD=sofiene MYSQLDB_DATABASE=pet_store MYSQLDB_LOCAL_PORT=3306 MYSQLDB_DOCKER_PORT=3306 SPRING_LOCAL_PORT=8080 SPRING_DOCKER_PORT=8080 && docker-compose up -d"'
    sshagent (credentials: ['deployment-server']) {
        sh "ssh -o StrictHostKeyChecking=no ${serverUser}@${serverIp} ${composeRun}"
    }
}

def cleanUntaggedImages(String serverIp, String serverUser){
    def cleanImages = 'docker image prune --force --filter "dangling=true"'
    sshagent (credentials: ['jenkins-server']) {
        sh "ssh -o StrictHostKeyChecking=no ${serverUser}@${serverIp} ${cleanImages}"
    }
}
*/
return this
