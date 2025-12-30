FROM eclipse-temurin:17.0.17_10-jdk AS builder

LABEL authors="Lawliet"

WORKDIR /app

COPY pom.xml .
COPY ./.mvn ./.mvn
COPY ./mvnw .

RUN chmod +x mvnw

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./src ./src

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17.0.17_10-jre

WORKDIR /app

COPY --from=builder /app/target/arithmetic.calculator.api-v0.0.1.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
