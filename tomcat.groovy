pipeline {
    agent {
        label 'my-tomcat'
    }
    stages {
        stage('git-pull') {
            steps { 
                sh 'sudo apt-get update -y'
                sh 'sudo apt-get install git -y'
                git 'https://github.com/palashgadpayle/student-ui.git'
                sh 'ls'
            }
        }
        stage('build-maven') {
            steps { 
                sh 'sudo apt-get update -y'
                sh 'sudo apt-get install maven curl unzip -y'
                sh 'mvn clean package'
            }
        }
        stage('build-artifacts') {
            steps { 
                //sh 'curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"'
                //sh 'unzip awscliv2.zip'
                //sh 'sudo ./aws/install'
                sh 'aws s3 cp **/*.war s3://tomcat-bucket-1/student-${BUILD_ID}.war'
            }
        }
