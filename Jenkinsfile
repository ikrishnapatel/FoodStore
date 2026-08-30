pipeline {
     agent any
 
     environment {
          AWS_REGION     = 'us-east-1' 
          AWS_ACCOUNT_ID = '272694330898'  
          ECR_REPO_NAME  = 'bookstore'
          ECR_REGISTRY   = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
          IMAGE_TAG      = "latest"
          
          // --- ECS Infrastructure Config ---
          ECS_CLUSTER    = 'bookstore-cluster'
          ECS_SERVICE    = 'bookstore-service'
          
          // --- CloudFormation Parameters (Update these with your values) ---
          VPC_ID         = 'vpc-xxxxxx'           // <-- Update this with your VPC ID
          SUBNET_IDS     = 'subnet-xxx,subnet-yyy' // <-- Update this with your Subnet IDs (comma-separated)
          DB_SECRET_ARN  = 'arn:aws:secretsmanager:us-east-1:272694330898:secret:your-db-secret' // <-- Update this with your Secrets Manager Secret ARN
          DB_URL         = 'jdbc:postgresql://bookstore-db-server.cluster-ci7omoswsvgf.us-east-1.rds.amazonaws.com:5432/bookstoredb'
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

          stage('Provision Infrastructure') {
              steps {
                  withCredentials([[
                      $class: 'AmazonWebServicesCredentialsBinding',
                      credentialsId: 'awscreds',
                      accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                      secretKeyVariable: 'AWS_SECRET_ACCESS_KEY'
                  ]]) {
                      sh """
                          aws cloudformation deploy \
                            --template-file ecs-template.yml \
                            --stack-name bookstore-ecs-stack \
                            --parameter-overrides \
                              VpcId=${VPC_ID} \
                              SubnetIds=${SUBNET_IDS} \
                              DbSecretArn=${DB_SECRET_ARN} \
                              EcrImageUri=${ECR_REGISTRY}/${ECR_REPO_NAME}:${IMAGE_TAG} \
                              DbUrl=${DB_URL} \
                            --capabilities CAPABILITY_IAM \
                            --region ${AWS_REGION}
                      """
                  }
              }
          }

          stage('Deploy to ECS') {
              steps {
                  withCredentials([[
                      $class: 'AmazonWebServicesCredentialsBinding',
                      credentialsId: 'awscreds',
                      accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                      secretKeyVariable: 'AWS_SECRET_ACCESS_KEY'
                  ]]) {
                      sh """
                          aws ecs update-service --cluster ${ECS_CLUSTER} --service ${ECS_SERVICE} --force-new-deployment --region ${AWS_REGION}
                      """
                  }
              }
          }
     }
}
