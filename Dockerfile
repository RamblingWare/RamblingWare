FROM tomcat:8.5-jre8-alpine

MAINTAINER Austin Delamar @amdelamar

# TODO Copy Tomcat files. Server, Tomcat-Users, Web.xml
# Configure HTTPS and default admin

# TODO Accept ENV couchdb url and credentials

COPY /build/libs/rant-1.0.war /usr/local/tomcat/webapps/

ENV RANT_VERSION 1.0

EXPOSE 8080
EXPOSE 8443

CMD ["catalina.sh", "run"]