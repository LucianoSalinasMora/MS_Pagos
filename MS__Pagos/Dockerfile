FROM eclipse-temurin:17-jdk-alpine


WORKDIR /app


COPY target/Pagos-0.0.1-SNAPSHOT.jar app.jar


EXPOSE 8007


ENTRYPOINT ["java", "-jar", "app.jar"]
