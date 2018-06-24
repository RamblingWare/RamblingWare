# Oddox Server

[![Build](https://img.shields.io/travis/oddoxorg/oddox.svg)](https://travis-ci.org/oddoxorg/oddox)
[![Codacy grade](https://img.shields.io/codacy/grade/ae13ca0369824fda9b4d32d43398495c.svg)](https://www.codacy.com/app/amdelamar/oddox)
[![Codecov](https://img.shields.io/codecov/c/github/oddoxorg/oddox.svg)](https://codecov.io/gh/oddoxorg/oddox)

Oddox is a blog with an offline-first editor. Write, save, and publish your posts without an internet connection, then sync later when you're connected.

## Features

 * Multi-Author blog system with roles. Author, Admin, Co-Authors, and Editors
 * Organize blog posts by Tags, Categories, Featured, Authors, and Dates
 * Offline-first [dashboard](https://github.com/oddoxorg/dashboard/) for authors to manage their blog
 * SEO & Social Media ready with Facebook (OpenGraph), Twitter, Google+ tags
 * HTTP/2, HTTPS, and pbkdf2 ([RFC2898](https://www.ietf.org/rfc/rfc2898.txt)) hashed passwords
 * Fast, asynchronous, caches content by default

## Usage

This project is still in development but stay tuned. The first version will be made available once the main features are complete.

## Deployment

> Under construction...

<!--
### One-Click Deploy
[![Bluemix](https://bluemix.net/deploy/button.png)](https://bluemix.net/deploy?repository=https://github.com/oddoxorg/oddox)
[![Heroku](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy?template=https://github.com/oddoxorg/oddox)
[![Azure](https://azuredeploy.net/deploybutton.png)](https://azuredeploy.net/?repository=https://github.com/oddoxorg/oddox)
[![Docker Cloud](https://files.cloud.docker.com/images/deploy-to-dockercloud.svg)](https://cloud.docker.com/stack/deploy/?repo=https://github.com/oddoxorg/oddox)

### Docker Deploy

 1. Pull [CouchDB 2.1.1](https://hub.docker.com/r/oddoxorg/couchdb/)
    ```
    docker pull oddoxorg/couchdb:2.1.1
    ```
 1. Pull [Oddox 0.1.0](https://hub.docker.com/r/oddoxorg/oddox/)
    ```
    docker pull oddoxorg/oddox:0.1.0
    ```
 1. Run CouchDB container
    ```
    docker run -d -e COUCHDB_USER=oddox -e COUCHDB_PASSWORD=<STRONG-PW> -p 6984:6984 oddoxorg/couchdb:2.1.1
    ```
     - Set your password for `<STRONG-PW>`. Write it down. Never share it!
     - Get the `<CONTAINER-IP>` for the next step.
 1. Run Oddox container
    ```
    docker run -e DB_URL=https://<CONTAINER-IP>:6984/ -e DB_USER=oddox -e DB_PASS=<STRONG-PW> -p 8080:8080 -p 8443:8443  oddoxorg/oddox:0.1.0
    ```
     - Enter the `<CONTAINER-IP>` and `<STRONG-PW>` from your CouchDB container.
 1. Visit `https://<container-ip>:8443/`

  docker pull oddoxorg/couchdb:2.1.1
  docker pull oddoxorg/oddox:0.1.0
  docker build --no-cache --rm -t oddoxorg/oddox:0.1.0 -t oddoxorg/oddox .
  docker run -e DB_URL=https://<container-ip>:6984/ -e DB_USER=admin -e DB_PASS=admin -p 8080:8080 -p 8443:8443 oddoxorg/oddox
  docker push oddoxorg/oddox
  docker push oddoxorg/oddox:0.1.0

### Cloud Foundry Deploy

> Under construction...

### Manually Deploy

> Under construction...

-->

## Tech Stack

 *  [Java 1.9](https://www.java.com/) Language
 *  [Vert.x 3.5](https://vertx.io/) Framework
 *  [CouchDB 2.1.1](https://couchdb.apache.org/) NoSQL Database
 *  [FreeMarker 2.3](https://freemarker.apache.org/) Templates

[Apache 2.0](https://github.com/oddoxorg/oddox/blob/master/LICENSE) License
