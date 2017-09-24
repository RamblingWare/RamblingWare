FROM tomcat:9.0.0-jre8-alpine

MAINTAINER Austin Delamar @amdelamar

# Tomcat HTTPS
#RUN apk add --no-cache --virtual openssl
#RUN openssl genrsa -des3 -passout pass:changeit -out ca.key 4096 -noout
#RUN openssl req -new -x509 -days 1826 -key ca.key -out ca.crt -passin pass:changeit \
#    -subj "/C=US/ST=-/L=-/O=Rant/OU=Rant/CN=IT/emailAddress=webmaster@ramblingware.com"

# Tomcat configs
COPY /deploy/tomcat/* /usr/local/tomcat/conf/

# Remove all other apps
RUN rm -rf /usr/local/tomcat/webapps/

# Add this app and make it root
COPY /build/libs/rant*.war /usr/local/tomcat/webapps/ROOT.war

# TODO Accept ENV couchdb url and credentials
ENV RANT_VERSION 1.0.0
ENV DB_URL http://localhost:5984/
ENV DB_USER admin
ENV DB_PASS admin

EXPOSE 8080
EXPOSE 8443

CMD ["catalina.sh", "run"]

# docker pull rant/rant
# docker build --no-cache --rm -t rant/rant .
# docker run -p 8080:8080 -p 8443:8443 --name rant rant/rant
# docker push rant/rant