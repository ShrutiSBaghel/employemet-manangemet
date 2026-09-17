FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /EMS

COPY . .
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /EMS

COPY --from=build /EMS/target/*.jar EMS-0.0.1-SNAPSHOT.jar

EXPOSE 8080
CMD ["java", "-jar", "EMS-0.0.1-SNAPSHOT.jar"]