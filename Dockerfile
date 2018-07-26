FROM openjdk:8
ADD target/demo.jar /demo.jar
ENTRYPOINT ["/usr/bin/java","-Dserver.port=8083","-jar","/demo.jar"]
EXPOSE 8083