FROM maven
COPY . .
RUN mvn clean
RUN mvn package

FROM tomcat
COPY target/IERIA.war $CATALINA_HOME/webapps/IERIA.war
EXPOSE 8080