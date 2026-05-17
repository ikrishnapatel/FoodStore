# Use a standard Java 17 base image (adjust the version if you use Java 21 or 11)
FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
# Copy the built JAR file into the image
COPY target/*.jar app.jar
# Expose your application port
EXPOSE 8080
# Run the application
ENTRYPOINT ["java","-jar","/app.jar"]