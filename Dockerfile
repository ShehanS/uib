FROM adoptopenjdk:11-jdk-hotspot
WORKDIR /app
COPY target/uib-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -jar -Dspring.datasource.url=$DB_URL app.jar"]
