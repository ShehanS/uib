#FROM adoptopenjdk:11-jdk-hotspot
#WORKDIR /app
#COPY target/uib-0.0.1-SNAPSHOT.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["sh", "-c", "java -jar -Dspring.datasource.url=$DB_URL app.jar"]
# Use a base image with Java and Maven pre-installed
FROM openjdk:17-ea-3-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file into the container
COPY target/uib-0.0.1-SNAPSHOT.jar uib-0.0.1-SNAPSHOT.jar

# Expose the port on which your Spring Boot application listens
EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "uib-0.0.1-SNAPSHOT.jar"]
