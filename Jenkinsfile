
 pipeline {
     agent any

     environment {
             // --- UPDATE THESE VARIABLES ---
               AWS_REGION = 'us-east-1' // 1. Change to your region
               AWS_ACCOUNT_ID = '424227905540' // 2. Replace with your 12-digit AWS Account ID
               ECR_REPO_NAME = 'bookstoreimage'
         // ------------------------------

         ECR_REGISTRY   = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
         IMAGE_TAG      = "latest"
     }

     stages {
         stage('Checkout Code') {
             steps {
                 // Agar GitHub use kar rahe ho toh yahan git checkout aayega
                 checkout scm
             }
         }

         stage('Build Jar') {
             steps {
                 // Spring Boot ka JAR banana
                 sh './mvnw clean package -DskipTests'
             }
         }

         stage('Build Docker Image') {
             steps {
                 // Docker image build karna
//                  sh "docker build -t ${ECR_REPO_NAME} ."
//                  This is to build image for linux not for silicons mac
                 sh "docker build --platform linux/amd64 -t ${ECR_REPO_NAME}:${IMAGE_TAG} ."
                 // Image ko ECR ke format mein tag karna
                 sh "docker tag ${ECR_REPO_NAME}:latest ${ECR_REGISTRY}/${ECR_REPO_NAME}:${IMAGE_TAG}"
             }
         }

         stage('Push to ECR') {
             steps {
                 // Jenkins mein jo ID banayi thi 'aws-ecr-creds' wo use karenge
                 withCredentials([[
                     $class: 'AmazonWebServicesCredentialsBinding',
                     credentialsId: 'aws-ecr-creds',
                     accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                     secretKeyVariable: 'AWS_SECRET_ACCESS_KEY'
                 ]]) {
                     sh """
                         # ECR mein login karna
                         aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${ECR_REGISTRY}
                         # Image push karna
                         docker push ${ECR_REGISTRY}/${ECR_REPO_NAME}:${IMAGE_TAG}
                     """
                 }
             }
         }
     }
 }