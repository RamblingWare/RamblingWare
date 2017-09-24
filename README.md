# Rant

[![Build](https://img.shields.io/travis/RamblingWare/Rant.svg)](https://travis-ci.org/RamblingWare/Rant)
[![Code Climate](https://img.shields.io/codeclimate/github/RamblingWare/Rant.svg)](https://codeclimate.com/github/RamblingWare/Rant)
[![Codecov](https://img.shields.io/codecov/c/github/RamblingWare/Rant.svg)](https://codecov.io/gh/RamblingWare/Rant)
[![License](https://img.shields.io/:license-apache-blue.svg)](https://github.com/RamblingWare/Rant/blob/master/LICENSE)

Blog management system on CouchDB.

> Live Example: coming soon...

## Features

 *  Multi-Author blog system with editable roles (Author, Editor, Owner, Admin)
 *  Organize blog posts by Tags, Categories, Featured, Authors, and Dates
 *  SEO & Social Media ready with Facebook (OpenGraph), Twitter, Google+ tags
 *  2FA security and pbkdf2 hashed passwords
 
## Planned Features

 * Containerize with Docker
 * Offline-first Vuejs + PouchDB dashboard
 * Freemarker Theme Templates

## Usage

This project is still in development. It is not easily modifiable for "new" blogs, but stay tuned. I plan to make a self-installing version once the main features are complete. Essentially, this is a web server for the [Rant](https://github.com/RamblingWare/Rant-Dashboard) dashboard. 

<!--
### One-Click Deploy 

Load this app onto on a Cloud Platform of your choice:

[![Bluemix](https://bluemix.net/deploy/button.png)](https://bluemix.net/deploy?repository=https://github.com/RamblingWare/Rant)
[![Heroku](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy?template=https://github.com/RamblingWare/Rant)
[![Azure](https://azuredeploy.net/deploybutton.png)](https://azuredeploy.net/?repository=https://github.com/RamblingWare/Rant)
[![Docker Cloud](https://files.cloud.docker.com/images/deploy-to-dockercloud.svg)](https://cloud.docker.com/stack/deploy/?repo=https://github.com/RamblingWare/Rant)

Sorry, Some of these might not work at this time.
 -->

### Docker Deploy

 1. Pull [CouchDB 2.0](https://hub.docker.com/r/klaemo/couchdb/) `docker pull klaemo/couchdb`
 1. Pull [Rant 1.0](https://hub.docker.com/r/rant/rant/) `docker pull rant/rant`
 1. Run CouchDB `docker run -d -p 5984:5984 --name rantdb klaemo/couchdb`
 1. Run Rant `docker run -p 8080:8080 --name rant rant/rant`
 1. Visit `http://localhost:8080/`
 
### Manually Build

 1. Install [CouchDB 2.0](https://couchdb.apache.org/) or signup for [Cloudant](https://cloudant.com/)
 1. Clone: `git clone https://github.com/RamblingWare/Rant`
 1. `cd Rant`
 1. Build: `gradlew clean build`
 1. WAR file is located at: `/build/libs/rant-1.0.war`
 1. Deploy on your Server of choice (Tomcat, Liberty, WildFly).
 1. Visit `https://localhost:8443/` (depending on server).

## Technologies Used

 *  [Java 1.8](https://www.java.com/) Programming Language
 *  [Apache Struts 2.5](https://struts.apache.org/) Framework
 *  [Apache CouchDB 2.0](https://couchdb.apache.org/) NoSQL Database
 *  [Apache Tomcat 8.0](https://tomcat.apache.org/) Web Server
 *  Two Factor Authentication with [amdelamar/jotp](https://github.com/amdelamar/jotp) and [Google QR Code generator](https://chart.googleapis.com/chart?chs=200x200&cht=qr&chl=200x200&chld=M|0&cht=qr&chl=otpauth://totp/Company:user@test.com?secret=6ZT3L2TKZ3WYBDS7FEY65TOQZRSRUY7M&issuer=Company&algorithm=SHA1&digits=6&period=30)

## License

[Apache 2.0](https://github.com/RamblingWare/Rant/blob/master/LICENSE)
