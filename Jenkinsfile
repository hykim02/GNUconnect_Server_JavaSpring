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

        stage('Post Slack') {
            steps{
                slackSend(channel: '#build-notification', color: 'warning', message: "빌드 시작: 지누가 ${env.JOB_NAME} 서버 ${env.BUILD_NUMBER} 버전을 열심히 빌드중이야!")
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

        stage('Notify Sentry of deployment') {
            environment {
                SENTRY_AUTH_TOKEN = credentials('sentry-auth-token')
                SENTRY_ORG = 'connect-gnu'
                SENTRY_PROJECT = 'java-spring-boot'
                SENTRY_ENVIRONMENT = 'production'
            }
            steps {
                // Install Sentry CLI
                sh 'command -v sentry-cli || curl -sL https://sentry.io/get-cli/ | bash'

                sh '''
                    export SENTRY_RELEASE=$(BUILD_NUMBER)
                    sentry-cli releases new -p $SENTRY_PROJECT $SENTRY_RELEASE
                    sentry-cli releases set-commits $SENTRY_RELEASE --auto
                    sentry-cli releases files $SENTRY_RELEASE upload-sourcemaps /path/to/sourcemaps
                    sentry-cli releases finalize $SENTRY_RELEASE
                    sentry-cli releases deploys $SENTRY_RELEASE new -e $SENTRY_ENVIRONMENT
                '''
            }
        }
    }

    post {
        success {
            slackSend(channel: '#build-notification', color: 'good', message: "빌드 성공: 야호! ${env.JOB_NAME} 서버 ${env.BUILD_NUMBER} 버전이 성공적으로 배포되었어!")
        }
        failure {
            slackSend(channel: '#build-notification', color: 'danger', message: "빌드 실패: 이런... ${env.JOB_NAME} 서버 ${env.BUILD_NUMBER} 버전 빌드에 실패했어 ㅜㅜ\n사유: ${currentBuild.result}")
        }
    }
}
