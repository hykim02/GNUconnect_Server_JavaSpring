pipeline {
    agent any
    
    environment {
        DOCKER_USER_ID = credentials('docker-hub-flask').username
        DOCKER_USER_PASSWORD = credentials('docker-hub-flask').password
    }

    stages {
        stage('Checkout') {
            steps {
                // Git 저장소에서 코드 체크아웃
                git branch: 'main', url: 'https://github.com/mooh2jj/docker-jenkins-pipeline-test2.git'
            }
        }

        stage('Build') {
            steps {
                // 스프링 부트 애플리케이션 빌드
                sh 'chmod +x gradlew'
                sh './gradlew clean build'
                sh 'ls -al ./build'
            }
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
