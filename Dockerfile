FROM openjdk:11-jdk
ADD target/*.jar /app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
