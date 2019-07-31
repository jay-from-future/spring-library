FROM openjdk:11-jre-slim
VOLUME /tmp
COPY target/*.jar spring-library.jar
CMD ["java", "-Dspring.data.mongodb.uri=mongodb://springboot-mongo:27017/springmongo-demo","-Djava.security.egd=file:/dev/./urandom","-jar","/spring-library.jar"]
