# Rant

[![Build](https://img.shields.io/travis/RamblingWare/Rant.svg)](https://travis-ci.org/RamblingWare/Rant)
[![Code Climate](https://img.shields.io/codeclimate/github/RamblingWare/Rant.svg)](https://codeclimate.com/github/RamblingWare/Rant)
[![Codecov](https://img.shields.io/codecov/c/github/RamblingWare/Rant.svg)](https://codecov.io/gh/RamblingWare/Rant)
[![License](https://img.shields.io/:license-apache-blue.svg)](https://github.com/RamblingWare/Rant/blob/master/LICENSE)


Blog management system on CouchDB.

Live Demo: https://www.ramblingware.com

## Features

 *  Multi-Author blog system with editable roles (Author, Editor, Owner, Admin)
 *  Social Media ready with Facebook (OpenGraph), Twitter, Google+ tags
 *  Tags, Categories, Featured, Authors, and Dates
 *  2FA security and properly hashed passwords
 
## Planned Features

 * Containerized with Docker
 * Create Vuejs dashboard
 * Theme Templates

## Usage

Feel free to copy any or all of this code. Be sure to include the license.

Right now this code has lots of 'ramblingware' in it. I plan to make a self-installing version that is easily modifiable for "new" blogs. Stay tuned. But if you'd like to try this out now, feel free to do so.

### One-Click Deploy 

Load this app onto on a Cloud Platform of your choice:

[![Bluemix](https://bluemix.net/deploy/button.png)](https://bluemix.net/deploy?repository=https://github.com/RamblingWare/Rant)
[![Heroku](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy?template=https://github.com/RamblingWare/Rant)
[![Azure](https://azuredeploy.net/deploybutton.png)](https://azuredeploy.net/?repository=https://github.com/RamblingWare/Rant)
[![Docker Cloud](https://files.cloud.docker.com/images/deploy-to-dockercloud.svg)](https://cloud.docker.com/stack/deploy/?repo=https://github.com/RamblingWare/Rant)

Sorry, Some of these might not work at this time.

### Docker Deploy

 1. Coming soon...

### Manually Deploy

 1. Install [CouchDB 2.0](https://couchdb.apache.org/)
 1. Download Rant: `rant-1.0.war`
 1. Deploy on your Server of choice (Tomcat, Liberty, WildFly).
 1. Visit `https://localhost:8443/` (depending on server).
 
### Manually Build

 1. Install [CouchDB 2.0](https://couchdb.apache.org/)
 1. Clone: `git clone https://github.com/RamblingWare/Rant`
 1. `cd Rant`
 1. Build: `gradle build`
 1. WAR file is located at: `/build/libs/rant-1.0.war`
 1. Deploy on your Server of choice (Tomcat, Liberty, JBoss, WildFly).
 1. Visit `https://localhost:8443/` (depending on server).

## Technologies Used

 *  [Java 1.8](https://www.java.com/) Programming Language
 *  [Apache Struts 2.5](https://struts.apache.org/) Framework
 *  [Apache CouchDB 2.0](https://couchdb.apache.org/) NoSQL Database
 *  [Apache Tomcat 9.0](https://tomcat.apache.org/) Web Server
 *  Secure Password Hashing with [amdelamar/jhash](https://github.com/amdelamar/jhash)
 *  Two Factor Authentication with [amdelamar/jotp](https://github.com/amdelamar/jotp) and [Google QR Code generator](https://chart.googleapis.com/chart?chs=200x200&cht=qr&chl=200x200&chld=M|0&cht=qr&chl=otpauth://totp/Company:user@test.com?secret=6ZT3L2TKZ3WYBDS7FEY65TOQZRSRUY7M&issuer=Company&algorithm=SHA1&digits=6&period=30)

## License

[Apache 2.0](https://github.com/RamblingWare/Rant/blob/master/LICENSE)
