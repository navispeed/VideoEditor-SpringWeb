pipeline {
  agent any
  tools {
    maven 'maven3'
    jdk 'jdk14'
  }
  stages {
    stage('Initialize') {
      steps {
        sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
      }
    }

    stage('Build') {
      steps {
        sh 'mvn -Dmaven.test.failure.ignore=true clean package'
      }
      post {
        success {
          junit 'target/surefire-reports/**/*.xml'
        }
      }
    }

    stage('Docker') {
      steps {
        sh 'cp target/*.jar docker/.'
        dir('docker') {
          script {
            docker.withRegistry('http://localhost:32000') {
              def jarfiles = findFiles(glob: 'webui*.jar')
              def img = docker.build('video-editor-ui', "--build-arg JAR_FILE=${jarfiles[0].name} .")
              img.push()
              img.push("${env.BRANCH_NAME}-${env.BUILD_NUMBER}".replaceAll("/", "-"))
            }
          }
        }
      }
    }
    stage('Deployment') {
      steps {
        build job: 'VideoEditor - CD', wait: false, parameters: [string(name: 'UI', value: String.valueOf(env.BRANCH_NAME + "-" + env.BUILD_NUMBER))]
      }
    }
  }
}
