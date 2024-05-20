pipeline {
    agent any

    tools {
        jdk 'jdk21'
    }

    environment {
        JAVA_HOME = "tool jdk21"
        DOCKERHUB_CREDENTIALS = credentials('docker-hub-flask')
        SPRING_APP_PROPERTIES_CREDENTIALS_ID = 'spring-application-properties'
        APP_PROPERTIES_FILE = 'app.properties'
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

        stage('application-properties download') {
            steps {
                withCredentials([file(credentialsId: "${SPRING_APP_PROPERTIES_CREDENTIALS_ID}", variable: 'SPRING_APP_PROPERTIES_FILE_PATH')]) {
                    // 바인딩된 파일을 파이프라인 환경 변수에 로드합니다.
                    APP_PROPERTIES_CONTENT = readFile(file: "${SPRING_APP_PROPERTIES_FILE_PATH}").trim()
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    sh 'docker-compose down'
                    sh "docker-compose -f docker-compose.yml -f ${APP_PROPERTIES_FILE} up -d"
                }
            }
        }
    }
}
