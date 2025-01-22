FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY build/libs/finanja-0.0.1-SNAPSHOT.jar /app/finanja.jar

EXPOSE 3000

CMD ["java", "-jar", "/app/finanja.jar"]
