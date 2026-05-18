# Use a standard Java 17 base image (adjust the version if you use Java 21 or 11)
FROM eclipse-temurin:21-jre-alpine

VOLUME /tmp
# Copy the built JAR file into the image
COPY target/*.jar app.jar
# Expose your application port
EXPOSE 8080
# Run the application
ENTRYPOINT ["java","-jar","/app.jar"]

#Last login: Sun May 17 23:19:23 on ttys000
#/dev/fd/12:25: command not found: compdef
#krishnapatel@Mac EKS % kubectl apply -f kube.yaml
#krishnapatel@Mac EKS % kubectl get pods
#krishnapatel@Mac EKS % kubectl describe pod
#krishnapatel@Mac EKS % kubectl get pods
#krishnapatel@Mac EKS % kubectl describe bookstore-service-59885c754-2q4km
#krishnapatel@Mac EKS % kubectl describe [bookstore-service-59885c754-2q4km]
#krishnapatel@Mac EKS % kubectl describe pod bookstore-service-59885c754-2q4km
#krishnapatel@Mac EKS % kubectl delete deployment bookstore-deployment
#krishnapatel@Mac EKS % kubectl get pods
#krishnapatel@Mac EKS % kubectl get pods
#krishnapatel@Mac EKS % kubectl rollout restart deployment bookstore-service
#krishnapatel@Mac EKS % kubectl get pods -w
#krishnapatel@Mac EKS % kubectl set env daemonset aws-node -n kube-system ENABLEPREFIXDELEGATION=true
#krishnapatel@Mac EKS % kubectl get pods -w
#krishnapatel@Mac EKS % kubectl get pods -n kube-system -w
#krishnapatel@Mac EKS % kubectl get daemonsets -n kube-system
#kubectl delete ingress bookstore-api-gateway
# ekctl gt cluster
#eksctl delete cluster --name bookstore-cluster --region us-east-1