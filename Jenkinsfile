pipeline {
    agent any // Runs on your local Mac Jenkins agent

    environment {
        // --- UPDATE THESE VARIABLES ---
        AWS_REGION = 'us-east-1' // 1. Change to your region
        AWS_ACCOUNT_ID = '424227905540' // 2. Replace with your 12-digit AWS Account ID
        ECR_REPO_NAME = 'bookstoreimage'
        // ------------------------------

        IMAGE_TAG      = "${env.BUILD_NUMBER}"
        ECR_REGISTRY   = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
    }

    stages {
        stage('Build Spring Boot JAR') {
            steps {
                // Ensure your Mac Jenkins has access to maven or the maven wrapper
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                // Build the image using the Dockerfile
                sh "docker build -t ${ECR_REPO_NAME}:${IMAGE_TAG} ."

                // Tag the image for your AWS ECR registry
                sh "docker tag ${ECR_REPO_NAME}:${IMAGE_TAG} ${ECR_REGISTRY}/${ECR_REPO_NAME}:${IMAGE_TAG}"
                sh "docker tag ${ECR_REPO_NAME}:${IMAGE_TAG} ${ECR_REGISTRY}/${ECR_REPO_NAME}:latest"
            }
        }

        stage('Push to AWS ECR') {
            steps {
                // This injects the AWS credentials you saved in Jenkins
                withCredentials([[
                    $class: 'AmazonWebServicesCredentialsBinding',
                    credentialsId: 'aws-ecr-credentials',
                    accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                    secretKeyVariable: 'AWS_SECRET_ACCESS_KEY'
                ]]) {
                    // Authenticate Docker with AWS ECR, then push the images
                    sh """
                        aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${ECR_REGISTRY}
                        docker push ${ECR_REGISTRY}/${ECR_REPO_NAME}:${IMAGE_TAG}
                        docker push ${ECR_REGISTRY}/${ECR_REPO_NAME}:latest
                    """
                }
            }
        }
    }
}