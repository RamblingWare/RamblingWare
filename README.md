# Oddox

[![Build](https://img.shields.io/travis/oddoxorg/oddox.svg)](https://travis-ci.org/oddoxorg/oddox)
[![Codacy grade](https://img.shields.io/codacy/grade/ae13ca0369824fda9b4d32d43398495c.svg)](https://www.codacy.com/app/amdelamar/oddox)
[![Codecov](https://img.shields.io/codecov/c/github/oddoxorg/oddox.svg)](https://codecov.io/gh/oddoxorg/oddox)
[![Docker Pulls](https://img.shields.io/docker/pulls/oddoxorg/oddox.svg)](https://hub.docker.com/r/oddoxorg/oddox/)
[![License](https://img.shields.io/:license-apache-blue.svg)](https://github.com/oddoxorg/oddox/blob/master/LICENSE)

Offline-first, Open-Source, Blogging platform.

> Live Example: coming soon...

## Features

 *  Multi-Author blog system with editable roles (Author, Editor, Owner, Admin)
 *  Organize blog posts by Tags, Categories, Featured, Authors, and Dates
 *  SEO & Social Media ready with Facebook (OpenGraph), Twitter, Google+ tags
 *  HTTP/2, HTTPS, and pbkdf2 ([RFC2898](https://www.ietf.org/rfc/rfc2898.txt)) hashed passwords

## Planned Features

 * Offline-first dashboard for authors to manage blog
 * Freemarker Theme Templates

## Usage

This project is still in development. It is not easily modifiable for "new" blogs, but stay tuned. A self-installing version will be made available once the main features are complete.

<!--
### One-Click Deploy
[![Bluemix](https://bluemix.net/deploy/button.png)](https://bluemix.net/deploy?repository=https://github.com/oddoxorg/oddox)
[![Heroku](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy?template=https://github.com/oddoxorg/oddox)
[![Azure](https://azuredeploy.net/deploybutton.png)](https://azuredeploy.net/?repository=https://github.com/oddoxorg/oddox)
[![Docker Cloud](https://files.cloud.docker.com/images/deploy-to-dockercloud.svg)](https://cloud.docker.com/stack/deploy/?repo=https://github.com/oddoxorg/oddox)
 -->

### Docker Deploy

 1. Pull [CouchDB 2.1.1](https://hub.docker.com/r/oddoxorg/couchdb/) `docker pull oddoxorg/couchdb:2.1.1`
 1. Pull [Oddox 1.0](https://hub.docker.com/r/oddoxorg/oddox/) `docker pull oddoxorg/oddox:1.0.0`
 1. Run CouchDB `docker run -d -e COUCHDB_USER=admin -e COUCHDB_PASSWORD=admin -p 6984:6984 oddoxorg/couchdb:2.1.1`
     - Choose your default password. Never share it!
     - Save the `<container-ip>` for the next step.
 1. Run Oddox `docker run -e DB_URL=https://<container-ip>:6984/ -e DB_USER=admin -e DB_PASS=admin -p 8080:8080 -p 8443:8443  oddoxorg/oddox:1.0.0`
     - Enter the `<container-ip>` of your couchdb container.
     - Paste the same password again.
 1. Visit `https://<container-ip>:8443/`

 <!--
  docker pull oddoxorg/couchdb:2.1.1
  docker pull oddoxorg/oddox:1.0.0
  docker build -f deploy/docker/1.0.0/Dockerfile --no-cache --rm -t oddoxorg/oddox:1.0.0 -t oddoxorg/oddox .
  docker run -e DB_URL=https://<container-ip>:6984/ -e DB_USER=admin -e DB_PASS=admin -p 8080:8080 -p 8443:8443 oddoxorg/oddox
  docker push oddoxorg/oddox
  docker push oddoxorg/oddox:1.0.0
 -->

### Manually Deploy

 1. Clone: `git clone https://github.com/oddoxorg/oddox`
 1. Install [CouchDB 2.1.1](https://couchdb.apache.org/) or signup for [Cloudant](https://cloudant.com/)
     - For Production, Paste the couchdb credentials in `src/main/resources/db.properties` file.
     - If using on locahost, you don't need to edit the credentials, unless you want to.
 1. `cd oddox`
 1. Build: `./gradlew clean build`. WAR file created: `/build/libs/oddox-1.0.0.war`
 1. Install [Tomcat 9.0](https://tomcat.apache.org/)
     - Copy `deploy/tomcat/server.xml` into `<tomcat-dir>/conf/`.
     - Copy WAR file into `<tomcat-dir>/webapps/` or for devs you can use Eclipse Servers UI (Window > Show View > Servers).
 1. Visit `https://localhost:8443/` (Devs: Double-check server.xml that context root is `/` and not `/oddox`).

## Tech Stack

 *  [Java 1.8](https://www.java.com/) Programming Language
 *  [Apache CouchDB 2.1.0](https://couchdb.apache.org/) NoSQL Database
 *  [Apache Tomcat 9.0](https://tomcat.apache.org/) Web Server
 *  [Apache Struts 2.5](https://struts.apache.org/) Framework
 *  [Apache FreeMarker 2.3](https://freemarker.apache.org/) Templates

## License

[Apache 2.0](https://github.com/oddoxorg/oddox/blob/master/LICENSE)
