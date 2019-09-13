FROM java:8
EXPOSE 8080
COPY ./build/libs/todo-rest-0.0.1-SNAPSHOT.jar /var/todo_api/
WORKDIR /var/todo_api/
CMD ["java", "-jar", "todo-rest-0.0.1-SNAPSHOT.jar"]