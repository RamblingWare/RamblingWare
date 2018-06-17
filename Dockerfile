FROM openjdk:10-jre-slim

LABEL maintainer="Austin Delamar @amdelamar" \
      description="open source blog with an offline-first writer"

# App config
ENV ODDOX_FILE oddox.jar
ENV ODDOX_HOME /usr/oddox

# CouchDB url and credentials
ENV DB_URL=http://localhost:5984/ \
    DB_USER=admin \
    DB_PASS=admin

# Non-root user
RUN groupadd -r oddox && useradd -r -g oddox oddox 
USER oddox

# Copy App resources
COPY --chown=oddox:oddox build/libs/$ODDOX_FILE $ODDOX_HOME/
COPY --chown=oddox:oddox webroot $ODDOX_HOME/webroot/

# Ports
EXPOSE 8080 8443

WORKDIR $ODDOX_HOME
ENTRYPOINT ["sh","-c"]
CMD ["exec java -jar $ODDOX_FILE"]
