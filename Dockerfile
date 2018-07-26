FROM openjdk:8
ADD target/demo.jar /demo.jar
ENTRYPOINT ["/usr/bin/java","-Dserver.port=80","-jar","/demo.jar"]
EXPOSE 80