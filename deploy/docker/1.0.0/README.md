### Available Tags

 * `1.0.0` - alpha build
 * `latest` - alpha build (Same as 1.0.0)


### About

Oddox is an Offline-first, Open-source, Blog platform on CouchDB. View the source code on [GitHub.](https://github.com/oddoxorg/)

This project is still in development. It is not easily modifiable for "new" blogs, but stay tuned. A self-installing version will be made available once the main features are complete. 

### Docker Deploy

 1. Pull [CouchDB 2.1.1](https://hub.docker.com/r/oddoxorg/couchdb/) `docker pull oddoxorg/couchdb:2.1.1`
 1. Pull [Oddox 1.0](https://hub.docker.com/r/oddoxorg/oddox/) `docker pull oddoxorg/oddox:1.0.0`
 1. Run CouchDB `docker run -d -e COUCHDB_USER=admin -e COUCHDB_PASSWORD=admin -p 6984:6984 oddoxorg/couchdb:2.1.1`
     - Choose your default password. Never share it!
     - Save the `<container-ip>` for the next step.
 1. Run Oddox `docker run -e DB_URL=http://<container-ip>:6984/ -e DB_USER=admin -e DB_PASS=admin -p 8080:8080 -p 8443:8443  oddoxorg/oddox:1.0.0`
     - Enter the `<container-ip>` of your couchdb container.
     - Paste the same password again.
 1. Visit `https://<container-ip>:8443/`
 
### License

[Apache 2.0](https://github.com/RamblingWare/Rant/blob/master/LICENSE)
