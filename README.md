# Rant

[![Build](https://img.shields.io/travis/RamblingWare/Rant.svg)](https://travis-ci.org/RamblingWare/Rant)
[![Code Climate](https://img.shields.io/codeclimate/github/RamblingWare/Rant.svg)](https://codeclimate.com/github/RamblingWare/Rant)
[![Codecov](https://img.shields.io/codecov/c/github/RamblingWare/Rant.svg)](https://codecov.io/gh/RamblingWare/Rant)
[![Docker Pulls](https://img.shields.io/docker/pulls/rant/rant.svg)](https://hub.docker.com/r/rant/rant/)
[![License](https://img.shields.io/:license-apache-blue.svg)](https://github.com/RamblingWare/Rant/blob/master/LICENSE)

Blog management system on CouchDB.

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

This project is still in development. It is not easily modifiable for "new" blogs, but stay tuned. I plan to make a self-installing version once the main features are complete. Essentially, this is a web server for the [Rant](https://github.com/RamblingWare/Rant-Dashboard) dashboard. 

<!--
### One-Click Deploy 
[![Bluemix](https://bluemix.net/deploy/button.png)](https://bluemix.net/deploy?repository=https://github.com/RamblingWare/Rant)
[![Heroku](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy?template=https://github.com/RamblingWare/Rant)
[![Azure](https://azuredeploy.net/deploybutton.png)](https://azuredeploy.net/?repository=https://github.com/RamblingWare/Rant)
[![Docker Cloud](https://files.cloud.docker.com/images/deploy-to-dockercloud.svg)](https://cloud.docker.com/stack/deploy/?repo=https://github.com/RamblingWare/Rant)
 -->

### Docker Deploy

 1. Pull [CouchDB 2.1.0](https://hub.docker.com/r/rant/couchdb/) `docker pull rant/couchdb:2.1.0`
 1. Pull [Rant 1.0](https://hub.docker.com/r/rant/rant/) `docker pull rant/rant:1.0.0`
 1. Run CouchDB `docker run -d -e COUCHDB_USER=admin -e COUCHDB_PASSWORD=admin -p 6984:6984 --name database rant/couchdb:2.1.0`
     - Choose your default password. Never share it!
     - Save the `<container-ip>` for the next step.
 1. Run Rant `docker run -e DB_URL=http://<container-ip>:6984/ -e DB_USER=admin -e DB_PASS=admin -p 8080:8080 -p 8443:8443 --name rant rant/rant:1.0.0`
     - Enter the `<container-ip>` of your couchdb container.
     - Paste the same password again.
 1. Visit `https://<container-ip>:8443/`
 
 <!-- 
  docker pull rant/rant:1.0.0
  docker build -f deploy/docker/1.0.0/Dockerfile --no-cache --rm -t rant/rant:1.0.0 -t rant/rant .
  docker run -e DB_URL=http://<container-ip>:6984/ -e DB_USER=admin -e DB_PASS=admin -p 8080:8080 -p 8443:8443 --name rant rant/rant
  docker push rant/rant
  docker push rant/rant:1.0.0
 -->
 
### Manually Deploy

 1. Clone: `git clone https://github.com/RamblingWare/Rant`
 1. Install [CouchDB 2.1.0](https://couchdb.apache.org/) or signup for [Cloudant](https://cloudant.com/)
     - If using Cloudant, Paste the db credentials in `src/main/resources/rant.properties` file.
     - If using CouchDB on locahost, you don't need to edit the credentials, unless you want to.
     - If using CouchDB on a public server, choose a stronger password in `src/main/resources/rant.properties` file.
 1. `cd Rant`
 1. Build: `./gradlew clean build`. WAR file at: `/build/libs/rant-1.0.0.war`
 1. Install [Tomcat 9.0](https://tomcat.apache.org/)
     - Copy `deploy/tomcat/server.xml` into `<tomcat-dir>/conf/`.
     - Copy WAR file into `<tomcat-dir>/webapps/` or for devs you can use Eclipse Servers UI (Window > Show View > Servers).
 1. Visit `https://localhost:8443/` (Devs: Double-check server.xml that context root is `/` and not `/rant`).

## Technologies Used

 *  [Java 1.8](https://www.java.com/) Programming Language
 *  [Apache Struts 2.5](https://struts.apache.org/) Framework
 *  [Apache CouchDB 2.1.0](https://couchdb.apache.org/) NoSQL Database
 *  [Apache Tomcat 9.0](https://tomcat.apache.org/) Web Server

## License

[Apache 2.0](https://github.com/RamblingWare/Rant/blob/master/LICENSE)
