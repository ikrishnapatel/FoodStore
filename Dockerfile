FROM jenkins/jenkins:lts
USER root

# Install tools
RUN apt-get update && apt-get install -y maven unzip curl

# Install Docker CLI
RUN curl -fsSL https://get.docker.com | sh

# Install AWS CLI (Apple Silicon ARM Version)
RUN curl "https://awscli.amazonaws.com/awscli-exe-linux-aarch64.zip" -o "awscliv2.zip" \
    && unzip awscliv2.zip \
    && ./aws/install \
    && rm -rf aws awscliv2.zip