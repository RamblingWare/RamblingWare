# Rant

[![Build](https://img.shields.io/travis/RamblingWare/Rant.svg)](https://travis-ci.org/RamblingWare/Rant)
[![Code Climate](https://img.shields.io/codeclimate/github/RamblingWare/Rant.svg)](https://codeclimate.com/github/RamblingWare/Rant)
[![Codecov](https://img.shields.io/codecov/c/github/RamblingWare/Rant.svg)](https://codecov.io/gh/RamblingWare/Rant)
[![License](https://img.shields.io/:license-apache-blue.svg)](https://github.com/RamblingWare/Rant/blob/master/LICENSE)


Blog management system.

Live Demo: https://www.ramblingware.com

## Features

 *  Multi-Author blog system with roles (Author,Editor,Owner,Admin)
 *  Social Media ready with OpenGraph API and Twitter API
 *  Simple interface, responsive design, mobile friendly
 *  Tagging, categories, featured, and archives
 *  2FA security and encrypted passwords for authors
 
## Planned Features

 * Create Angularjs dashboard for authors.
 * Swappable themes.

## Usage

Feel free to copy any or all of this code. Be sure to include the license.

Right now this code has lots of 'ramblingware' in it. I plan to make a self-installing version that is easily modifiable for "new" blogs. Stay tuned. But if you'd like to try this out now, feel free to do so.

### Manually Deploy

 1. Install [CouchDB 2.0](https://couchdb.apache.org/)
 1. Download Rant: `git clone https://github.com/RamblingWare/Rant`
 1. `cd Rant`
 1. Compile code: `gradle build`
 1. WAR file is located at: `/build/libs/rant-1.0.war`
 1. Deploy on your Server of choice (Tomcat,Liberty,JBoss,WildFly).
 1. Visit `http://localhost:8443/rant/` (depending on server).

### One-Click Deploy 

Load this app onto on a Cloud Platform of your choice:

[![Bluemix](https://bluemix.net/deploy/button.png)](https://bluemix.net/deploy?repository=https://github.com/RamblingWare/Rant)
[![Heroku](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy?template=https://github.com/RamblingWare/Rant)
[![Azure](https://azuredeploy.net/deploybutton.png)](https://azuredeploy.net/?repository=https://github.com/RamblingWare/Rant)
[![Docker Cloud](https://files.cloud.docker.com/images/deploy-to-dockercloud.svg)](https://cloud.docker.com/stack/deploy/?repo=https://github.com/RamblingWare/Rant)

Sorry, Some of these might not work at this time.

## Technologies Used

 *  [Java EE 1.8](https://www.java.com/) Programming Language
 *  [Apache Struts 2.5](https://struts.apache.org/) Framework
 *  [CouchDB 2.0](https://couchdb.apache.org/) NoSQL Database
 *  [W3.CSS](http://www.w3schools.com/css/) Styling --> Planning to use theme templates.
 *  Search by [DuckDuckGo](https://duckduckgo.com) --> Changeable in properties.
 *  WYSIWYG editor by [CKEditor](http://ckeditor.com/download) --> Planning markdown editor instead of html.
 *  Code syntax highlighting by [Highlight.js](https://highlightjs.org/)
 *  Webfont Icons by [Icomoon](https://icomoon.io/)
 *  Secure Password Hashing with [amdelamar/jhash](https://github.com/amdelamar/jhash)
 *  Two Factor Authentication with [amdelamar/jotp](https://github.com/amdelamar/jotp) and [Google QR Code generator](https://chart.googleapis.com/chart?chs=200x200&cht=qr&chl=200x200&chld=M|0&cht=qr&chl=otpauth://totp/Company:user@test.com?secret=6ZT3L2TKZ3WYBDS7FEY65TOQZRSRUY7M&issuer=Company&algorithm=SHA1&digits=6&period=30)
 *  UTF-8 encoded and HTML5 / CSS3 verified

## License

[Apache 2.0](https://github.com/RamblingWare/Rant/blob/master/LICENSE)
