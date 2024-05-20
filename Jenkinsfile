pipeline {
    agent any

    tools {
        jdk 'jdk21'
    }

    environment {
        JAVA_HOME = "tool jdk21"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/GNU-connect/Server-JavaSpring.git'
            }
        }

        stage('Build') {
            steps {
                docker.build(imageName: 'backend_spring_server', dockerfilePath: 'Dockerfile')
            }
        }
    }
}
