
pipeline {
    triggers {
        pollSCM('') //Empty quotes tells it to build on a push
    }
    agent any

    tools {
        maven "Maven3"
        nodejs "nodejs16"
    }
    stages {
        stage('Pull') {
            steps {
                git url: 'https://github.com/harmijay/AdministrasiPegawai/'
            }
        }
        stage('Build') {
                steps {
                    bat "mvn clean package"
                }
        }
        stage('Deploy') {
            steps {
                bat "heroku git:remote -a administrasi-pegawai"
                bat "git push -f heroku master"
            }
        }
        stage('Test') {
            steps {
                bat "mvn test"
            }

            post {
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                }

            }
        }
//         stage('TestPostman') {
//             steps {
//                 bat "newman run path/to/json/tabungan.postman_collection.json"
//             }
//         }
    }
    post {
        failure {
            mail bcc: '', body: 'Hi, AdministrasiPegawai Service build is failed. Please check it immediately.', cc: '', from: '', replyTo: '', subject: 'AdministrasiPegawai - Build Failed', to: 'zdev.aria@gmail.com'
        }
    }
}