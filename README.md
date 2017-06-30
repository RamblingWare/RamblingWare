# Rant

[![Build](https://img.shields.io/travis/RamblingWare/Rant.svg)](https://travis-ci.org/RamblingWare/Rant)
[![Code Climate](https://img.shields.io/codeclimate/github/Rant/RamblingWare.svg)](https://codeclimate.com/github/RamblingWare/Rant)
[![Codecov](https://img.shields.io/codecov/c/github/RamblingWare/Rant.svg)](https://codecov.io/gh/RamblingWare/Rant)
[![License](https://img.shields.io/:license-apache-blue.svg)](https://github.com/RamblingWare/Rant/blob/master/LICENSE)


Blog management system written in Java.

Demo: https://www.ramblingware.com

---

### Goals

These goals are planned in the future. For now, solidifying the current features is first priority.

- Endless length posts for **ranting** on and on.
- Self sufficient features and management for one or multiple authors
- Swappable databases (MySQL, CouchDB, etc)
- Swappable comment systems (Disqus, Facebook, etc)

### Features

- Multi-Author Blog system with roles (Author,Editor,Owner,Admin)
- Social Media ready with OpenGraph API and Twitter API
- Responsive Design, Mobile Friendly, Simple Interface
- Easy WYSIWYG editor for creating posts
- Tagging, Categories and Archive Years
- JavaScript independent (Minimal usage, not required)
- 2FA security and encrypted passwords for logins

### Usage

Feel free to fork, clone, and reused any or all of this code. Be sure to include the license.

Right now this code has lots of 'ramblingware' in it. I plan to make a version that is easily modifiable for "new" blogs. Stay tuned.

You can quickly deploy a copy of this app by signing into Bluemix and clicking this button:
[![Deploy to Bluemix](https://bluemix.net/deploy/button.png)](https://bluemix.net/deploy?repository=https://github.com/RamblingWare/Rant)  

### Technologies Used

- [Java EE](https://www.java.com/) Programming Language
- [Apache Struts 2](https://struts.apache.org/) Framework
- [MySQL 5.5](https://www.mysql.com/) Database
- [W3.CSS](http://www.w3schools.com/css/) Styling
- Search by [DuckDuckGo](https://duckduckgo.com)
- WYSIWYG editor by [CKEditor](http://ckeditor.com/download)
- Code syntax highlighting by [Highlight.js](https://highlightjs.org/)
- Webfont Icons by [Icomoon](https://icomoon.io/)
- Secure Password Hashing with [amdelamar/jhash](https://github.com/amdelamar/jhash)
- TwoFactor Authentication with [amdelamar/jotp](https://github.com/amdelamar/jotp) and [Google QR Code generator](https://chart.googleapis.com/chart?chs=200x200&cht=qr&chl=200x200&chld=M|0&cht=qr&chl=otpauth://totp/Company:user@test.com?secret=6ZT3L2TKZ3WYBDS7FEY65TOQZRSRUY7M&issuer=Company&algorithm=SHA1&digits=6&period=30)
- UTF-8 encoded and HTML5 / CSS3 verified

### License

[Apache 2.0](https://github.com/RamblingWare/Rant/blob/master/LICENSE)
