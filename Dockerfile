FROM jenkins/jenkins:lts
USER root

RUN apt-get update && apt-get install -y maven unzip curl
RUN curl -fsSL https://get.docker.com | sh

# IMPORTANT: This MUST say aarch64 for your Mac!
RUN curl "https://awscli.amazonaws.com/awscli-exe-linux-aarch64.zip" -o "awscliv2.zip" \
    && unzip awscliv2.zip \
    && ./aws/install \
    && rm -rf aws awscliv2.zip
