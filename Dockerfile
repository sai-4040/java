FROM amazoncorretto:11-alpine-jdk

RUN mkdir -p /var/demo

RUN chmod -R 777 /var/demo

WORKDIR /var/demo

COPY ./build/libs/demo-0.0.1-SNAPSHOT.jar demo.jar

CMD ["java", "-jar", "demo.jar"]