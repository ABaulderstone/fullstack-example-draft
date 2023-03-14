# Build stage - create our jar file
FROM maven:3.8.4-openjdk-17 as builder

WORKDIR /app

#Copy pom
COPY pom.xml .
RUN mvn dependency:go-offline

#copy source code
COPY src ./src
RUN mvn package -DskipTests

#Second stage - run our jar file
FROM eclipse-temurin:17

WORKDIR /app
EXPOSE 8080

COPY --from=builder /app/target/pokemon-backend.jar .

CMD ["java", "-jar", "pokemon-backend.jar"]


