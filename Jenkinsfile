pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/GNU-connect/Server-JavaSpring.git'
            }
        }

        stage('Build') {
            steps {
               sh '''
                echo 'start bootJar'
                chmod +x gradlew
                ./gradlew clean bootJar
                '''
            }
        }
    }
}
