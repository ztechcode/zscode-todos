FROM openjdk:8-jdk-alpine
WORKDIR /usr/zscode/todos
VOLUME /usr/zscode/todos
CMD ["mkdir","images"]
CMD ["mkdir","data"]
CMD ["mkdir","uploads"]
COPY *.jar zscode-todos.jar
COPY application.prod.properties config/application.properties
ENTRYPOINT ["java","-jar","zscode-todos.jar"]