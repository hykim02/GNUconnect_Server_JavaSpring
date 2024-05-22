pipeline {
    agent any

    tools {
        jdk 'jdk21'
    }

    environment {
        JAVA_HOME = "tool jdk21"
        DOCKERHUB_CREDENTIALS = credentials('docker-hub-flask')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/GNU-connect/Server-JavaSpring.git'
            }
        }

        stage('Download Application Properties') {
            steps {
                withCredentials([file(credentialsId: 'spring-application-properties', variable: 'configFile')]) {
                    script {
                        sh 'cp ${configFile} src/main/resources/application.properties'
                        sh 'chmod 644 src/main/resources/application.properties' // 권한 설정
                        sh 'ls -l src/main/resources/' // 파일이 제대로 복사되었는지 확인
                    }
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    sh 'docker-compose build backend_spring_server'
                }
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