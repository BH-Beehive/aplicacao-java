FROM maven:3-jdk-18

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

ONBUILD ADD . /usr/src/main/java/aplication/TelaLogin

ONBUILD RUN mvn install

ONBUILD ADD . /usr/src/app

ONBUILD RUN mvn instal

 CMD ["java","-jar","/projeto-java-1.0-SNAPSHOT-jar-with-dependencies.jar"]

