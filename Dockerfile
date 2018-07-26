FROM openjdk:8
ENTRYPOINT ["/usr/bin/java","-Dserver.port=8083","-jar","target/demo.jar"]
EXPOSE 8083