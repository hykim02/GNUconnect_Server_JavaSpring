pipeline {
    agent any

    tools {
        jdk 'jdk21'
    }

    environment {
        JAVA_HOME = "tool jdk21"
        DOCKERHUB_CREDENTIALS = credentials('docker-hub-flask')
        APP_PROPERTIES_FILE = 'application.properties'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/GNU-connect/Server-JavaSpring.git'
            }
        }

        stage('Build') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew clean build'
            }
        }

        stage('Tag') {
            steps {
                script {
                    sh 'docker tag ${DOCKERHUB_CREDENTIALS_USR}/connect-gnu-spring ${DOCKERHUB_CREDENTIALS_USR}/connect-gnu-spring:${BUILD_NUMBER}'
                    sh 'docker tag ${DOCKERHUB_CREDENTIALS_USR}/connect-gnu-spring ${DOCKERHUB_CREDENTIALS_USR}/connect-gnu-spring:latest'
                }
            }
        }

        stage('Push') {
            steps {
                script {
                    sh 'docker login -u ${DOCKERHUB_CREDENTIALS_USR} -p ${DOCKERHUB_CREDENTIALS_PSW}'
                    sh 'docker push ${DOCKERHUB_CREDENTIALS_USR}/connect-gnu-spring:${BUILD_NUMBER}'
                    sh 'docker push ${DOCKERHUB_CREDENTIALS_USR}/connect-gnu-spring:latest'
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