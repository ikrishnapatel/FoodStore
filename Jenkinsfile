pipeline {
     agent any
 
     environment {
          AWS_REGION = 'us-east-1' 
          AWS_ACCOUNT_ID = '272694330898'  
          ECR_REPO_NAME = 'bookstore'
          ECR_REGISTRY   = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
          IMAGE_TAG      = "latest"
     }
 
     stages {
          stage('Checkout Code') {
              steps {
                  checkout scm
              }
          }

          stage('Build Jar') {
              steps {
                  sh './mvnw clean package -DskipTests'
              }
          }

          stage('Build & Push to ECR via Buildx') {
              steps {
                  retry(3) {
                      withCredentials([[
                          $class: 'AmazonWebServicesCredentialsBinding',
                          credentialsId: 'awscreds',
                          accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                          secretKeyVariable: 'AWS_SECRET_ACCESS_KEY'
                      ]]) {
                          sh """
                              aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${ECR_REGISTRY}
                              docker buildx create --name ecr-builder --use || true
                              
                              docker buildx build --platform linux/amd64 \
                                -t ${ECR_REGISTRY}/${ECR_REPO_NAME}:${IMAGE_TAG} \
                                --push .
                                
                              docker buildx rm ecr-builder || true
                          """
                      }
                  }
              }
          }
     }
}
