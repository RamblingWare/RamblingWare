FROM tomcat:9.0.0-jre8-alpine

LABEL maintainer="Austin Delamar @amdelamar" \
      description="Blog system on CouchDB."

# Tomcat HTTPS over HTTP/2
RUN apk add --no-cache --virtual openssl \
 && openssl genrsa -out privkey.pem 4096 -noout \
 && openssl req -new -x509 -days 1826 -key privkey.pem -out cert.pem -subj "/C=US/ST=-/L=-/O=Rant/OU=Rant/CN=IT/emailAddress=webmaster@ramblingware.com" \
 && mv privkey.pem cert.pem /usr/local/tomcat/conf/

# Tomcat configs
COPY /deploy/tomcat/* /usr/local/tomcat/conf/

# Remove all other apps
RUN rm -rf /usr/local/tomcat/webapps/

# Add this app and make it root
COPY /build/libs/rant*.war /usr/local/tomcat/webapps/ROOT.war

# CouchDB url and credentials
ENV RANT_VERSION=1.0.0 \
    DB_URL=http://localhost:5984/ \
    DB_USER=admin \
    DB_PASS=admin

EXPOSE 8080 8443
CMD ["catalina.sh", "run"]