pipeline {
    agent any

    tools {
        jdk 'jdk21'
    }

    environment {
        JAVA_HOME = toolName('jdk21')  // Use toolName for clarity
        DOCKERHUB_CREDENTIALS = credentialsId('docker-hub-flask')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/GNU-connect/Server-JavaSpring.git'
            }
        }

        stage('Add Env') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'spring-application-properties', usernameVariable: 'SPRING_CONFIG_USERNAME', passwordVariable: 'SPRING_CONFIG_PASSWORD')]) {
                    sh 'cp ${SPRING_CONFIG_USERNAME} ./application.properties'
                }
            }
        }

        stage('Build') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew clean build'
            }
        }

        stage('Tag & Push') { // Combine Tag and Push stages for efficiency
            steps {
                script {
                    sh 'docker login -u ${DOCKERHUB_CREDENTIALS_USR} -p ${DOCKERHUB_CREDENTIALS_PSW}'
                    def imageName = "${DOCKERHUB_CREDENTIALS_USR}/connect-gnu-spring"
                    sh "docker tag ${imageName}:${BUILD_NUMBER} ${imageName}:latest"
                    sh "docker push ${imageName}:${BUILD_NUMBER}"
                    sh "docker push ${imageName}:latest"
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    sh 'docker-compose down'
                    sh "docker-compose up -d backend_spring_server"
                }
            }
        }
    }
}