FROM openjdk:17
ADD target/banana-app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]