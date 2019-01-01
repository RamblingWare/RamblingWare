FROM openjdk:10-jre-slim

ARG oddox_version=0.1.0
ARG vcs_ref=unspecified
ARG build_date=unspecified

LABEL org.label-schema.name="oddox-server" \
      org.label-schema.description="open source blog with an offline-first writer" \
      org.label-schema.build-date="${build_date}" \
      org.label-schema.vcs-url="https://github.com/amdelamar/oddox-server" \
      org.label-schema.vcs-ref="${vcs_ref}" \
      org.label-schema.version="${oddox_version}" \
      org.label-schema.schema-version="1.0" \
      maintainer="amdelamar"

# App config
ENV ODDOX_VERSION=$oddox_version \
    ODDOX_HOME="/opt/oddox" \
    PORT="8080" \
    HTTPS_ENABLED="true" \
    HTTPS_PORT="8443"

# CouchDB url and credentials
ENV DB_URL="http://localhost:5984/" \
    DB_USER="admin" \
    DB_PASS="admin"

# Non-root user
RUN groupadd -r oddox && useradd -r -g oddox oddox
USER oddox

# Copy App resources
COPY --chown=oddox:oddox build/libs/oddox-${ODDOX_VERSION}.jar $ODDOX_HOME/
COPY --chown=oddox:oddox webroot $ODDOX_HOME/webroot/

# Ports http and https
EXPOSE 8080 8443

WORKDIR $ODDOX_HOME
ENTRYPOINT ["sh","-c"]
CMD ["exec java $JVM_OPTS -jar oddox-${ODDOX_VERSION}.jar"]
