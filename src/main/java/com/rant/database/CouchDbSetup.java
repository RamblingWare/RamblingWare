package com.rant.database;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import com.rant.config.Application;
import com.rant.config.Utils;
import com.rant.objects.Author;
import com.rant.objects.Post;
import com.rant.objects.Role;

public class CouchDbSetup extends DatabaseSetup {

    public CouchDbSetup(com.rant.objects.Database database) {
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
            return ClientBuilder.url(new URL(database.getUrl())).build();
        } else {
            return ClientBuilder.url(new URL(database.getUrl())).username(database.getUsername())
                    .password(database.getPassword()).build();
        }
    }

    /**
     * Verify the Database is setup properly. Installs the necessary design docs, configs, and
     * default admin.
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
            System.out.println("Database is not configured properly. Installing for first-time...");
            // perform first-time install
            setup = installDesign();
            if (setup) {
                // installed
                System.out.println("Install completed.");
            }
        }

        return setup;
    }

    /**
     * Verify the Rant Design documents and databases needed.
     * 
     * @return true if verified
     */
    protected boolean verifyDesign() {
        try {
            CloudantClient client = getConnection();

            Database posts = client.database("posts", false);
            posts.getDesignDocumentManager().get("_design/posts");

            Database roles = client.database("roles", false);
            roles.getDesignDocumentManager().get("_design/roles");

            Database authors = client.database("authors", false);
            authors.getDesignDocumentManager().get("_design/authors");

            Database views = client.database("views", false);
            views.getDesignDocumentManager().get("_design/views");

            Database app = client.database("application", false);
            app.find("APPCONFIG");
            app.find("APPFIREWALL");

            return true;

        } catch (Exception e) {
            // failed verification
            return false;
        }
    }

    /**
     * Install the Rant Design documents and databases needed.
     * 
     * @return true if install successful
     */
    protected boolean installDesign() {
        try {
            CloudantClient client = getConnection();

            // create default roles
            Database roles = client.database("roles", true);
            DesignDocument rolesdesign = DesignDocumentManager
                    .fromFile(Utils.getResourceAsFile("/design/roles.json"));
            roles.getDesignDocumentManager().put(rolesdesign);
            List<Role> defaultRoles = getDefaultRoles();
            roles.bulk(defaultRoles);

            // create authors
            Database authors = client.database("authors", true);
            DesignDocument authorsdesign = DesignDocumentManager
                    .fromFile(Utils.getResourceAsFile("/design/authors.json"));
            authors.getDesignDocumentManager().put(authorsdesign);

            // create default author
            authors.save(getDefaultAuthor());

            // create posts
            Database posts = client.database("posts", true);
            DesignDocument postsdesign = DesignDocumentManager
                    .fromFile(Utils.getResourceAsFile("/design/posts.json"));
            posts.getDesignDocumentManager().put(postsdesign);

            // create default post
            posts.save(getDefaultPost());

            // create views
            Database views = client.database("views", true);
            DesignDocument viewsdesign = DesignDocumentManager
                    .fromFile(Utils.getResourceAsFile("/design/views.json"));
            views.getDesignDocumentManager().put(viewsdesign);

            // default app config
            Database app = client.database("application", true);
            app.save(Application.getAppConfig());
            app.save(Application.getAppFirewall());

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed Database Installation: Exception occured during install: "
                    + e.getMessage());
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
            client.createDB("ranttest");
            Database db = client.database("ranttest", false);

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
            client.deleteDB("ranttest");

            return true;

        } catch (NoDocumentException e) {
            e.printStackTrace();
            System.err.println(
                    "Failed Database Test: Please check permissions for ID given to create/edit/delete documents.");
        } catch (DocumentConflictException e) {
            e.printStackTrace();
            System.err.println(
                    "Failed Database Test: Please check permissions for ID given to create/edit/delete documents.");
        } catch (MalformedURLException | CouchDbException e) {
            e.printStackTrace();
            System.err
                    .println("Failed Database Test: Please check the Database URL and try again.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(
                    "Failed Database Test: Exception occured during test: " + e.getMessage());
        }
        return false;
    }

    /**
     * Check if Admin Party mode is enabled. This means there are no server administrators.
     * 
     * @return true if enabled
     */
    protected boolean isAdminParty() {
        boolean adminParty = true;
        HttpConnection request1 = null;
        HttpConnection response1 = null;
        CloudantClient client = null;
        try {
            // check if admin party mode is enabled.
            client = getConnection();

            // get node name
            String node = "nonode@nohost";
            Iterator<String> cnodes = client.getMembership().getClusterNodes();
            if (cnodes.hasNext()) {
                node = cnodes.next();
            }

            // get admins list
            request1 = Http
                    .GET(new URL(client.getBaseUri() + "/_node/" + node + "/_config/admins"));
            response1 = client.executeRequest(request1);
            String admins = Utils.removeBadChars(response1.responseAsString());

            // if no admins, then it means admin party mode is still partying.
            System.out.println("Admins: '" + admins + "'");
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
            if (client != null) {
                client.shutdown();
            }
        }
        if (adminParty) {
            System.out.println(
                    "WARNING: Admin Party mode is still enabled. Please create a Database administrator as soon as possible to secure it.");
        }
        return adminParty;
    }

    /**
     * Attempts to disable admin party mode, by creating a regular _user and a system user (also
     * known as couchdb admin). Uses the default user+pass provided in the properties file.
     * 
     * @return true if successful
     */
    protected boolean disableAdminParty() {
        System.out.println("Attempting to disable admin party mode...");
        HttpConnection request1 = null;
        HttpConnection response1 = null;
        CloudantClient client = null;
        try {
            client = getConnection();

            client.database("_users", true);
            client.database("_replicator", true);

            // TODO create CouchDB permissions
            // - role = author
            // - role = admin

            // create default user
            String user = Application.getString("default.username");
            String pass = Application.getString("default.password");

            request1 = Http.PUT(new URL(client.getBaseUri() + "/_users/org.couchdb.user:" + user),
                    "application/json");
            request1.setRequestBody("{\"_id\":\"org.couchdb.user:" + user + "\",\"name\":\"" + user
                    + "\",\"password\":\"" + pass
                    + "\",\"roles\":[\"admin\",\"author\"],\"type\":\"user\"}");
            response1 = client.executeRequest(request1);
            if (response1.getConnection().getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                // failed to create default user
                throw new Exception("Failed to create default user '" + user
                        + "'. Exception occured during install.");
            }
            request1.disconnect();
            response1.disconnect();

            // get node name
            String node = "nonode@nohost";
            Iterator<String> cnodes = client.getMembership().getClusterNodes();
            if (cnodes.hasNext()) {
                node = cnodes.next();
            }

            // create default administrator
            user = database.getUsername();
            pass = database.getPassword();

            request1 = Http.PUT(
                    new URL(client.getBaseUri() + "/_node/" + node + "/_config/admins/" + user),
                    "application/json");
            request1.setRequestBody("\"" + pass + "\"");
            response1 = client.executeRequest(request1);
            if (response1.getConnection().getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                // failed to create default admin
                throw new Exception("ERROR: Failed to create default admin '" + user
                        + "'. Exception occured during install.");
            } else {
                // stopped the party
                System.out.println("Created default admin: " + user);
            }
            request1.disconnect();
            response1.disconnect();

            // disabled admin party mode
            System.out.println("Disabled Admin Party mode.");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            // failed
            System.out.println(
                    "WARNING: Admin Party mode is still enabled. Please create a Database administrator as soon as possible to secure it.");
            return false;
        } finally {
            // close streams
            if (response1 != null) {
                response1.disconnect();
            }
            if (request1 != null) {
                request1.disconnect();
            }
            if (client != null) {
                client.shutdown();
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
        https = database.getUrl().startsWith("https");

        if (!https) {
            System.out.println(
                    "WARNING: Database connection is not using Https. Please get a SSL certificate as soon as possible to secure it.");
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
            version = false;
        }

        if (!currVersion.startsWith("2.")) {
            version = false;
            System.out.println("ERROR: Incompatible CouchDB Version (" + currVersion
                    + ") was expecting 2.0.0 or 2.1.0");;
        }
        return version;
    }

    /**
     * Create default author to insert in the database.
     * 
     * @return Author
     */
    protected Author getDefaultAuthor() {
        String user = Application.getString("default.username");
        String name = user.substring(0, 1).toUpperCase() + user.substring(1).toLowerCase();
        Author author = new Author(user);
        author.setName(name);
        author.setRole(new Role("admin"));
        author.setDescription("The website administrator.");
        author.setContent("");
        author.setEmail(Application.getString("default.email"));
        author.setThumbnail("/img/placeholder-200.png");
        return author;
    }

    /**
     * Create default post to insert in the database.
     * 
     * @return Post
     */
    protected Post getDefaultPost() {
        Post post = new Post("welcome-to-rant");
        post.setAuthorId(Application.getString("default.username"));

        post.setTitle("Welcome to Rant!");
        post.setDescription("Here is a sample post demonstrating this blog system.");
        post.setContent(
                "<p>Here is a welcome post that will eventually be outlining all the features of Rant.<br><br>But for now its simply a placeholder.<br><br>Enjoy your new blog!</p>");
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("test");
        tags.add("sample");
        post.setTags(tags);
        post.setCategory("Welcome");
        post.setBanner("/img/placeholder-640.png");
        post.setThumbnail("/img/placeholder-640.png");
        post.setBannerCaption("Time to Rant!");
        post.setPublished(true);
        post.setFeatured(true);
        Date date = new Date(System.currentTimeMillis());
        post.setPublishDate(date);
        post.setCreateDate(date);
        post.setModifyDate(date);

        return post;
    }

    /**
     * Create default roles to insert in the database.
     * 
     * @return List
     */
    protected List<Role> getDefaultRoles() {
        ArrayList<Role> roles = new ArrayList<Role>();

        Role adm = new Role("admin");
        adm.setName("Admin");
        adm.setPublic(false);
        adm.setPostsCreate(false);
        adm.setPostsEdit(true);
        adm.setPostsEditOthers(true);
        adm.setPostsSeeHidden(true);
        adm.setPostsDelete(true);
        adm.setUsersCreate(true);
        adm.setUsersEdit(true);
        adm.setUsersEditOthers(true);
        adm.setUsersDelete(true);
        adm.setRolesCreate(true);
        adm.setRolesEdit(true);
        adm.setRolesDelete(true);
        adm.setPagesCreate(true);
        adm.setPagesEdit(true);
        adm.setPagesDelete(true);
        adm.setCommentsCreate(false);
        adm.setCommentsEdit(false);
        adm.setCommentsEditOthers(false);
        adm.setCommentsDelete(false);
        adm.setSettingsCreate(true);
        adm.setSettingsEdit(true);
        adm.setSettingsDelete(true);

        Role own = new Role("owner");
        own.setName("Owner");
        own.setPublic(true);
        own.setPostsCreate(true);
        own.setPostsEdit(true);
        own.setPostsEditOthers(true);
        own.setPostsSeeHidden(false);
        own.setPostsDelete(true);
        own.setUsersCreate(true);
        own.setUsersEdit(false);
        own.setUsersEditOthers(false);
        own.setUsersDelete(true);
        own.setRolesCreate(true);
        own.setRolesEdit(true);
        own.setRolesDelete(true);
        own.setPagesCreate(true);
        own.setPagesEdit(true);
        own.setPagesDelete(true);
        own.setCommentsCreate(true);
        own.setCommentsEdit(true);
        own.setCommentsEditOthers(false);
        own.setCommentsDelete(true);
        own.setSettingsCreate(true);
        own.setSettingsEdit(true);
        own.setSettingsDelete(true);

        Role ath = new Role("author");
        ath.setName("author");
        ath.setPublic(true);
        ath.setPostsCreate(true);
        ath.setPostsEdit(true);
        ath.setPostsEditOthers(false);
        ath.setPostsSeeHidden(false);
        ath.setPostsDelete(true);
        ath.setUsersCreate(false);
        ath.setUsersEdit(true);
        ath.setUsersEditOthers(false);
        ath.setUsersDelete(false);
        ath.setRolesCreate(false);
        ath.setRolesEdit(false);
        ath.setRolesDelete(false);
        ath.setPagesCreate(false);
        ath.setPagesEdit(false);
        ath.setPagesDelete(false);
        ath.setCommentsCreate(true);
        ath.setCommentsEdit(true);
        ath.setCommentsEditOthers(true);
        ath.setCommentsDelete(true);
        ath.setSettingsCreate(false);
        ath.setSettingsEdit(false);
        ath.setSettingsDelete(false);

        Role edt = new Role("editor");
        edt.setName("Editor");
        edt.setPublic(true);
        edt.setPostsCreate(true);
        edt.setPostsEdit(true);
        edt.setPostsEditOthers(true);
        edt.setPostsSeeHidden(true);
        edt.setPostsDelete(true);
        edt.setUsersCreate(false);
        edt.setUsersEdit(true);
        edt.setUsersEditOthers(true);
        edt.setUsersDelete(false);
        edt.setRolesCreate(false);
        edt.setRolesEdit(false);
        edt.setRolesDelete(false);
        edt.setPagesCreate(true);
        edt.setPagesEdit(true);
        edt.setPagesDelete(true);
        edt.setCommentsCreate(true);
        edt.setCommentsEdit(true);
        edt.setCommentsEditOthers(true);
        edt.setCommentsDelete(true);
        edt.setSettingsCreate(false);
        edt.setSettingsEdit(false);
        edt.setSettingsDelete(false);

        roles.add(adm);
        roles.add(own);
        roles.add(edt);
        roles.add(ath);
        return roles;
    }
}
