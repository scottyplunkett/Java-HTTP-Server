FROM openjdk:9
COPY out/artifacts/HTTPServer/HTTPServer.jar /usr/src/server/HTTPServer.jar
CMD ["java", "-jar", "HTTPServer.jar", "5000", "/usr/src/server/HTTPServer.jar"]