pipeline {
    agent any
    
    environment {
        DOCKER_USER_ID = credentials('docker-hub-flask').username
        DOCKER_USER_PASSWORD = credentials('docker-hub-flask').password
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/GNU-connect/Server-JavaSpring.git'
            }
        }

        stage('Build') {
            sh(script: 'docker-compose build backend_spring_server')
        }

        stage('Tag and Push') {
            steps {
                // Docker 이미지 빌드 및 푸시
                sh '''
                    docker build -t ${DOCKER_USER_ID}/connect-gnu-spring:${BUILD_NUMBER} .
                    docker push ${DOCKER_USER_ID}/connect-gnu-spring:${BUILD_NUMBER}
                    docker tag ${DOCKER_USER_ID}/connect-gnu-spring:${BUILD_NUMBER} ${DOCKER_USER_ID}/connect-gnu-spring:latest
                    docker push ${DOCKER_USER_ID}/connect-gnu-spring:latest
                '''
            }
        }

        stage('Deploy') {
            steps {
                // 배포
                sh 'docker-compose down'
                sh 'docker-compose up -d backend_spring_server'
            }
        }
    }
}
