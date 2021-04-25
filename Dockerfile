FROM openjdk:11
ADD build/libs/github-stalker-0.0.1-SNAPSHOT.jar .
EXPOSE 9090
CMD java -jar github-stalker-0.0.1-SNAPSHOT.jar