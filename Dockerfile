FROM jenkins/jenkins:lts
USER root

# Install Maven for Spring Boot and Unzip for AWS CLI
RUN apt-get update && apt-get install -y maven unzip curl

# Install Docker CLI so Jenkins can run 'docker build' inside the pipeline
RUN curl -fsSL https://get.docker.com | sh

# Install AWS CLI so Jenkins can securely log into Amazon ECR
RUN curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip" \
    && unzip awscliv2.zip \
    && ./aws/install \
    && rm -rf aws awscliv2.zip

USER jenkins