pipeline {
    agent any

    tools {
        jdk 'jdk21'
    }

    environment {
        JAVA_HOME = "tool jdk21"
        DOCKER_USER_ID = "${credentials('docker-hub-flask').username}"
        DOCKER_USER_PASSWORD = "${credentials('docker-hub-flask').password}"
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
                sh 'docker-compose build backend_spring_server'
            }
        }

        stage('Tag') {
            steps {
                script {
                    sh '''
                    docker tag backend_spring_server ${DOCKER_USER_ID}/connect-gnu-spring:${BUILD_NUMBER}
                    docker tag backend_spring_server ${DOCKER_USER_ID}/connect-gnu-spring:latest
                    '''
                }
            }
        }

        stage('Push') {
            steps {
                script {
                    sh 'docker login -u ${DOCKER_USER_ID} -p ${DOCKER_USER_PASSWORD}'
                    sh 'docker push ${DOCKER_USER_ID}/connect-gnu-spring:${BUILD_NUMBER}'
                    sh 'docker push ${DOCKER_USER_ID}/connect-gnu-spring:latest'
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    sh 'docker-compose down'
                    sh 'docker-compose up -d backend_spring_server'
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
