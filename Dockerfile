FROM maven:3.5-jdk-8 as BUILD
COPY src /usr/jobOonJa/src
COPY pom.xml /usr/jobOonJa
RUN mvn -f /usr/jobOonJa/pom.xml clean package

FROM tomcat:9.0.20-jre11
COPY --from=BUILD /usr/jobOonJa/target/IERIA.war /usr/local/tomcat/webapps/
CMD ["catalina.sh", "run"]