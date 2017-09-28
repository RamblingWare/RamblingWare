FROM tomcat:9.0.0-jre8-alpine

LABEL maintainer="Austin Delamar @amdelamar" \
      description="Blog system on CouchDB."

# Tomcat HTTPS over HTTP/2
#RUN apk add --no-cache --virtual openssl \
# && openssl genrsa -des3 -passout pass:changeit -out ca.key 4096 -noout \
# && openssl req -new -x509 -days 1826 -key ca.key -out ca.crt -passin pass:changeit \
#    -subj "/C=US/ST=-/L=-/O=Rant/OU=Rant/CN=IT/emailAddress=webmaster@ramblingware.com"

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
#EXPOSE 8443

CMD ["catalina.sh", "run"]

# docker pull rant/rant
# docker build --no-cache --rm -t rant/rant .
# docker run -p 8080:8080 -p 8443:8443 --name rant rant/rant
# docker run -e DB_URL=http://<container-ip>:5984/ -e DB_USER=admin -e DB_PASS=admin -p 8080:8080 -p 8443:8443 --name backend rant/rant
# docker push rant/rant
