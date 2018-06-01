FROM openjdk:9-jre-slim

LABEL maintainer="Austin Delamar @amdelamar" \
      description="open source blog with an offline-first writer"

# App config
ENV ODDOX_FILE oddox-1.0.0.jar
ENV ODDOX_HOME /usr/oddox

# CouchDB url and credentials
ENV APP_VERSION=1.0.0 \
    DB_URL=http://localhost:5984/ \
    DB_USER=admin \
    DB_PASS=admin

# Copy App and resources
COPY build/libs/$ODDOX_FILE $ODDOX_HOME/
COPY webroot $ODDOX_HOME/webroot/

# Ports
EXPOSE 8080 8443

WORKDIR $ODDOX_HOME
ENTRYPOINT ["sh","-c"]
CMD ["exec java -jar $ODDOX_FILE"]