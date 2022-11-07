FROM openjdk:11
VOLUME /tmp
EXPOSE 9046
ADD ./target/bootcoin-0.0.1-SNAPSHOT.jar ms-bootcoin.jar
ENTRYPOINT ["java", "-jar", "ms-bootcoin.jar"]