FROM eclipse-temurin:17-jdk-alpine
COPY target/debricked-scan-notification-0.0.1-SNAPSHOT.jar debricked-scan-notification.jar
ENTRYPOINT ["java", "-jar", "debricked-scan-notification.jar"]