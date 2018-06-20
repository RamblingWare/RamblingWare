package org.oddox.database;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.oddox.config.AppFirewall;
import org.oddox.config.AppHeaders;
import org.oddox.config.Application;
import org.oddox.config.Utils;
import org.oddox.objects.Author;
import org.oddox.objects.Header;
import org.oddox.objects.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.DesignDocumentManager;
import com.cloudant.client.api.model.DesignDocument;
import com.cloudant.client.org.lightcouch.CouchDbException;
import com.cloudant.client.org.lightcouch.DocumentConflictException;
import com.cloudant.client.org.lightcouch.NoDocumentException;
import com.cloudant.http.Http;
import com.cloudant.http.HttpConnection;
import com.google.gson.JsonObject;

/**
 * CouchDB Setup class
 * 
 * @author amdelamar
 */
public class CouchDbSetup extends DatabaseSetup {
    
    private static Logger logger = LoggerFactory.getLogger(CouchDbSetup.class);

    public CouchDbSetup(org.oddox.database.Database database) {
        super(database);
    }

    /**
     * Obtains a connection to the CouchDB if possible.
     * 
     * @return CloudantClient connection
     * @throws MalformedURLException
     *             if invalid url
     */
    protected CloudantClient getConnection() throws MalformedURLException {
        if (database.isAdminParty()) {
            return ClientBuilder.url(new URL(database.getUrl()))
                    .disableSSLAuthentication()
                    .build();
        } else {
            return ClientBuilder.url(new URL(database.getUrl()))
                    .username(database.getUsername())
                    .password(database.getPassword())
                    .disableSSLAuthentication()
                    .build();
        }
    }

    /**
     * Verify the Database is setup properly. Installs the necessary design docs,
     * configs, and default admin.
     * 
     * @return true if database is ready for app to use it
     */
    public boolean setup() {
        boolean setup = true;

        // only 2.+ version allowed
        setup = isVersionOk();

        // Https should be required.
        isHttps();
        // But for now, this won't fail the setup step.
        // Because it helps localhost, testing, etc

        // admin party should be disabled
        if (setup && isAdminParty()) {
            database.setAdminParty(!disableAdminParty());
            // setup = disableAdminParty();
            // We still continue because it helps localhost, testing, etc
        } else {
            database.setAdminParty(false);
        }

        // check dbuser permissions
        if (setup) {
            setup = hasAdminPermissions();
        }

        // verify install
        if (setup && !verifyDesign()) {
            logger.trace("Database is not configured properly. Installing for first-time...");
            // perform first-time install
            setup = installDesign();
            if (setup) {
                // installed
                logger.trace("Install completed.");
            }
        }

        return setup;
    }

    /**
     * Verify the App Design documents and databases needed.
     * 
     * @return true if verified
     */
    protected boolean verifyDesign() {
        try {
            CloudantClient client = getConnection();

            Database posts = client.database("posts", false);
            posts.getDesignDocumentManager()
                    .get("_design/posts");

            Database authors = client.database("authors", false);
            authors.getDesignDocumentManager()
                    .get("_design/authors");

            Database views = client.database("views", false);
            views.getDesignDocumentManager()
                    .get("_design/views");

            Database app = client.database("application", false);
            app.find("APPCONFIG");
            app.find("APPFIREWALL");

            return true;

        } catch (Exception e) {
            // failed verification
            logger.trace("Verify db design failed. Missing dbs or APPCONFIG doc.");
            return false;
        }
    }

    /**
     * Install the App Design documents and databases needed.
     * 
     * @return true if install successful
     */
    protected boolean installDesign() {
        HttpConnection request = null;
        HttpConnection response = null;
        try {
            CloudantClient client = getConnection();

            // create authors
            Database authors = client.database("authors", true);
            DesignDocument authorsdesign = DesignDocumentManager
                    .fromFile(Utils.getResourceAsFile("/design/authors.json"));
            authors.getDesignDocumentManager()
                    .put(authorsdesign);

            // create default author
            authors.save(getDefaultAuthor());

            // set permissions
            request = Http.PUT(new URL(client.getBaseUri() + "/authors/_security"), "application/json");
            request.setRequestBody(
                    "{\r\n" + 
                    "    \"admins\": {\r\n" + 
                    "        \"roles\": [\r\n" + 
                    "            \"admin\"\r\n" + 
                    "        ]\r\n" + 
                    "    },\r\n" +
                    "    \"members\": {\r\n" + 
                    "        \"roles\": [\r\n" + 
                    "            \"author\"\r\n" + 
                    "        ]\r\n" + 
                    "    }\r\n" +
                    "}");
            response = client.executeRequest(request);
            if (response.getConnection()
                    .getResponseCode() != HttpURLConnection.HTTP_CREATED
                    && response.getConnection()
                            .getResponseCode() != HttpURLConnection.HTTP_OK) {
                // failed to set permissions
                throw new IOException("Failed to set default db permissions for 'authors'.");
            }

            // create posts
            Database posts = client.database("posts", true);
            DesignDocument postsdesign = DesignDocumentManager.fromFile(Utils.getResourceAsFile("/design/posts.json"));
            posts.getDesignDocumentManager()
                    .put(postsdesign);

            // create default post
            posts.save(getDefaultPost());

            // set permissions
            request = Http.PUT(new URL(client.getBaseUri() + "/posts/_security"), "application/json");
            request.setRequestBody(
                    "{\r\n" + 
                    "    \"admins\": {\r\n" + 
                    "        \"roles\": [\r\n" + 
                    "            \"admin\"\r\n" + 
                    "        ]\r\n" + 
                    "    },\r\n" +
                    "    \"members\": {\r\n" + 
                    "        \"roles\": [\r\n" + 
                    "            \"author\"\r\n" + 
                    "        ]\r\n" + 
                    "    }\r\n" +
                    "}");
            response = client.executeRequest(request);
            if (response.getConnection()
                    .getResponseCode() != HttpURLConnection.HTTP_CREATED
                    && response.getConnection()
                            .getResponseCode() != HttpURLConnection.HTTP_OK) {
                // failed to set permissions
                throw new IOException("Failed to set default db permissions for 'posts'.");
            }

            // create views
            Database views = client.database("views", true);
            DesignDocument viewsdesign = DesignDocumentManager.fromFile(Utils.getResourceAsFile("/design/views.json"));
            views.getDesignDocumentManager()
                    .put(viewsdesign);

            // set permissions
            request = Http.PUT(new URL(client.getBaseUri() + "/views/_security"), "application/json");
            request.setRequestBody(
                    "{\r\n" + 
                    "    \"admins\": {\r\n" + 
                    "        \"roles\": [\r\n" + 
                    "            \"admin\"\r\n" +
                    "        ]\r\n" + 
                    "    },\r\n" +
                    "    \"members\": {\r\n" + 
                    "        \"roles\": [\r\n" + 
                    "            \"author\"\r\n" + 
                    "        ]\r\n" + 
                    "    }\r\n" +
                    "}");
            response = client.executeRequest(request);
            if (response.getConnection()
                    .getResponseCode() != HttpURLConnection.HTTP_CREATED
                    && response.getConnection()
                            .getResponseCode() != HttpURLConnection.HTTP_OK) {
                // failed to set permissions
                throw new IOException("Failed to set default db permissions for 'views'.");
            }

            // default app config
            Database app = client.database("application", true);
            app.save(Application.getAppConfig());
            app.save(getDefaultAppFirewall());
            app.save(getDefaultAppHeaders());

            // set permissions
            request = Http.PUT(new URL(client.getBaseUri() + "/application/_security"), "application/json");
            request.setRequestBody(
                    "{\r\n" + 
                    "    \"admins\": {\r\n" + 
                    "        \"roles\": [\r\n" + 
                    "            \"admin\"\r\n" + 
                    "        ]\r\n" + 
                    "    },\r\n" +
                    "    \"members\": {\r\n" + 
                    "        \"roles\": [\r\n" + 
                    "            \"author\"\r\n" + 
                    "        ]\r\n" + 
                    "    }\r\n" +
                    "}");
            response = client.executeRequest(request);
            if (response.getConnection()
                    .getResponseCode() != HttpURLConnection.HTTP_CREATED
                    && response.getConnection()
                            .getResponseCode() != HttpURLConnection.HTTP_OK) {
                // failed to set permissions
                throw new IOException("Failed to set default db permissions for 'application'.");
            }

            return true;

        } catch (Exception e) {
            logger.error("Failed Database Installation: Exception occured during install: ", e);
        } finally {
            // close streams
            if (response != null) {
                response.disconnect();
            }
            if (request != null) {
                request.disconnect();
            }
        }
        return false;
    }

    /**
     * Check if the current dbuser can perform all CRUD actions on the Database.
     * 
     * @returns boolean true if successful
     */
    protected boolean hasAdminPermissions() {
        try {
            CloudantClient client = getConnection();

            // create database
            Database db = client.database("crudtest", true);

            // create document
            JsonObject json = new JsonObject();
            json.addProperty("_id", "TEST");
            db.save(json);

            // get document
            json = db.find(JsonObject.class, "TEST");

            // update document
            json.addProperty("title", "quick test edit");
            db.update(json);

            // delete document
            json = db.find(JsonObject.class, "TEST");
            db.remove(json);

            // delete database
            client.deleteDB("crudtest");

            return true;

        } catch (NoDocumentException e) {
            logger.error(
                    "Failed Database Test: Please check permissions for ID given to create/edit/delete documents.", e);
        } catch (DocumentConflictException e) {
            logger.error(
                    "Failed Database Test: Please check permissions for ID given to create/edit/delete documents.", e);
        } catch (MalformedURLException | CouchDbException e) {
            logger.error("Failed Database Test: Please check the Database URL and try again.", e);
        } catch (Exception e) {
            logger.error("Failed Database Test: Exception occured during test: ", e);
        }
        return false;
    }

    /**
     * Check if Admin Party mode is enabled. If true, it means there are no server
     * administrators, which leaves the database vulnerable.
     * 
     * @return true if enabled
     */
    protected boolean isAdminParty() {
        boolean adminParty = true;
        HttpConnection request1 = null;
        HttpConnection response1 = null;
        try {
            // check if admin party mode is enabled.
            CloudantClient client = getConnection();

            // get node name
            String node = "nonode@nohost";
            Iterator<String> cnodes = client.getMembership()
                    .getClusterNodes();
            if (cnodes.hasNext()) {
                node = cnodes.next();
            }

            // get admins list
            request1 = Http.GET(new URL(client.getBaseUri() + "/_node/" + node + "/_config/admins"));
            response1 = client.executeRequest(request1);
            String admins = Utils.removeBadChars(response1.responseAsString());

            // if no admins, then it means admin party mode is still partying.
            logger.trace("Admins: '" + admins + "'");
            adminParty = admins.length() < 3;

        } catch (Exception e) {
            adminParty = false;
        } finally {
            // close streams
            if (response1 != null) {
                response1.disconnect();
            }
            if (request1 != null) {
                request1.disconnect();
            }
        }
        if (adminParty) {
            logger.warn(
                    "Admin Party mode is still enabled. Please create a Database administrator as soon as possible to secure it.");
        }
        return adminParty;
    }

    /**
     * Attempts to disable admin party mode, by creating a regular _user and a
     * system user (also known as couchdb server admin). Uses the default user+pass
     * provided in the properties file.
     * 
     * @return true if successful
     */
    protected boolean disableAdminParty() {
        logger.trace("Attempting to disable admin party mode...");
        HttpConnection request1 = null;
        HttpConnection response1 = null;
        try {
            CloudantClient client = getConnection();

            client.database("_users", true);
            client.database("_replicator", true);

            // create default user
            String user = "admin";
            String pass = "admin";

            request1 = Http.PUT(new URL(client.getBaseUri() + "/_users/org.couchdb.user:" + user), "application/json");
            request1.setRequestBody("{\"_id\":\"org.couchdb.user:" + user + "\",\"name\":\"" + user
                    + "\",\"password\":\"" + pass + "\",\"roles\":[\"admin\"],\"type\":\"user\"}");
            response1 = client.executeRequest(request1);
            if (response1.getConnection()
                    .getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                // failed to create default user
                throw new IOException(
                        "Failed to create default user '" + user + "'. Exception occured during install.");
            }

            // get node name
            String node = "nonode@nohost";
            Iterator<String> cnodes = client.getMembership()
                    .getClusterNodes();
            if (cnodes.hasNext()) {
                node = cnodes.next();
            }

            // create default administrator
            user = database.getUsername();
            pass = database.getPassword();

            request1 = Http.PUT(new URL(client.getBaseUri() + "/_node/" + node + "/_config/admins/" + user),
                    "application/json");
            request1.setRequestBody("\"" + pass + "\"");
            response1 = client.executeRequest(request1);
            if (response1.getConnection()
                    .getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                // failed to create default admin
                throw new IOException(
                        "Failed to create default admin '" + user + "'. Exception occured during install.");
            } else {
                // stopped the party
                logger.trace("Created default admin: " + user);
            }

            // disabled admin party mode
            logger.info("Disabled Admin Party mode.");
            return true;

        } catch (Exception e) {
            // failed
            logger.warn(
                    "Admin Party mode is still enabled. Please create a Database administrator as soon as possible to secure it.", e);
            return false;
        } finally {
            // close streams
            if (response1 != null) {
                response1.disconnect();
            }
            if (request1 != null) {
                request1.disconnect();
            }
        }
    }

    /**
     * Check if Https is enabled. This means the connection is secure.
     * 
     * @return true if enabled
     */
    protected boolean isHttps() {
        boolean https = true;

        // check https
        https = database.getUrl()
                .startsWith("https");

        if (!https) {
            logger.warn(
                    "Database connection is not using Https. Please get a SSL certificate as soon as possible to secure it.");
        }
        return https;
    }

    /**
     * Check if CouchDB Version is compatible. (2.+)
     * 
     * @return true if compatible
     */
    protected boolean isVersionOk() {
        boolean version = true;
        String currVersion = "";
        try {
            // check CouchDB version
            CloudantClient client = getConnection();
            currVersion = client.serverVersion();
        } catch (Exception e) {
            e.printStackTrace();
            version = false;
        }

        if (!currVersion.startsWith("2.")) {
            version = false;
            logger.error("Incompatible CouchDB Version (" + currVersion + "). Required version 2.x.x");
        }
        return version;
    }

    /**
     * Create default author to insert in the database.
     * 
     * @return Author
     */
    protected Author getDefaultAuthor() {
        String user = "admin";
        String name = user.substring(0, 1)
                .toUpperCase()
                + user.substring(1)
                        .toLowerCase();
        Author author = new Author(user);
        author.setName(name);
        author.setRole("admin");
        author.setDescription("The website administrator.");
        author.setContent("<p>This user hasn't written a bio yet.</p>");
        author.setThumbnail("");
        String now = Utils.getDateIso8601();
        author.setCreateDate(now);
        author.setModifyDate(now);
        return author;
    }

    /**
     * Create default post to insert in the database.
     * 
     * @return Post
     */
    protected Post getDefaultPost() {
        Post post = new Post("welcome-to-oddox");
        post.setAuthorId("admin");

        post.setTitle("Welcome to Oddox!");
        post.setDescription("Here is a sample post demonstrating this blog platform.");
        post.setContent(
                "<p>Here is a welcome post that will eventually be outlining all the features of Oddox. But for now its simply a placeholder. Enjoy your new blog!</p>");
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("test");
        tags.add("sample");
        post.setTags(tags);
        post.setCategory("welcome");
        post.setBanner("");
        post.setThumbnail("");
        post.setBannerCaption("");
        post.setPublished(true);
        post.setFeatured(true);
        String now = Utils.getDateIso8601();
        post.setPublishDate(now);
        post.setCreateDate(now);
        post.setModifyDate(now);
        return post;
    }
    
    /**
     * Creates and returns a default application firewall list, that's empty.
     * @return AppFirewall
     */
    protected AppFirewall getDefaultAppFirewall() {
        return new AppFirewall();
    }

    /**
     * Creates and returns a default list of HTTP headers like HSTS and X-Powered-by.
     * @return AppHeaders
     */
    protected AppHeaders getDefaultAppHeaders() {
        AppHeaders hd = new AppHeaders();
        List<Header> headers = new ArrayList<Header>();

        // HSTS: Tell a browser that you always want a user to connect using HTTPS instead of HTTP for 1 year
        headers.add(new Header("Strict-Transport-Security", "max-age=15552000; includeSubDomains"));

        // The browser will always set the referrer header to the origin from which the request was made. 
        // This will strip any path information from the referrer information. But will not allow the 
        // secure origin to be sent on a HTTP request, only HTTPS.
        headers.add(new Header("Referrer-Policy", "strict-origin"));
        // @see https://scotthelme.co.uk/a-new-security-header-referrer-policy/

        // Allow or deny <iframe> from your site or other sites
        headers.add(new Header("X-Frame-Options", "SAMEORIGIN"));

        // Enable reflective XSS protection by blocking attacks rather than sanitizing scripts.
        headers.add(new Header("X-Xss-Protection", "1; mode=block"));

        // Prevents browsers from trying to mime-sniff the content-type of a response away from the
        // one being declared by the server.
        headers.add(new Header("X-Content-Type-Options", "nosniff"));

        // Set software identifier
        headers.add(new Header("X-Powered-By", "oddox.org"));
        
        hd.setHeaders(headers);
        return hd;
    }
}
