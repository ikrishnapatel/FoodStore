pipeline {
    agent any

    tools {
            maven 'M3' // This must match the name you typed in Step 1
        }


    environment {
        AWS_REGION = 'us-east-1' // 1. Change to your region
        AWS_ACCOUNT_ID = '424227905540' // 2. Replace with your 12-digit AWS Account ID
        ECR_REPO_NAME = 'bookstoreimage'

        ECR_REGISTRY = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
        IMAGE_TAG = "build-${env.BUILD_NUMBER}"
    }

    stages {

        stage('Build') {
                        steps {
                            echo 'Compiling Spring Boot application...'
                            sh 'mvn clean package -DskipTests'
                        }
                    }


        stage('2. Build Docker Image') {
            steps {
                echo 'Building image...'
                // Uses the Docker socket mounted from your local machine
                sh "docker build -t ${ECR_REPO_NAME}:${IMAGE_TAG} ."
            }
        }

        stage('3. Push to ECR') {
            steps {
                echo 'Pushing to AWS...'

                // This wrapper pulls your Access Keys from the Jenkins secure vault
                // Make sure 'aws-ecr-creds' matches the exact ID you used in Jenkins!
                withCredentials([aws(credentialsId: 'aws-ecr-creds', accessKeyVariable: 'AWS_ACCESS_KEY_ID', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY')]) {

                    // Jenkins now has permission to run these AWS commands
                    sh "aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${ECR_REGISTRY}"

                    sh "docker tag ${ECR_REPO_NAME}:${IMAGE_TAG} ${ECR_REGISTRY}/${ECR_REPO_NAME}:latest"

                    sh "docker push ${ECR_REGISTRY}/${ECR_REPO_NAME}:${IMAGE_TAG}"
                    sh "docker push ${ECR_REGISTRY}/${ECR_REPO_NAME}:latest"
                }
            }
        }
    }
}