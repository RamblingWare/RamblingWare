package org.oddox.database;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.oddox.config.Application;
import org.oddox.config.Utils;
import org.oddox.objects.Author;
import org.oddox.objects.Post;
import org.oddox.objects.Role;

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

public class CouchDbSetup extends DatabaseSetup {

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
     * Verify the App Design documents and databases needed.
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
     * Install the App Design documents and databases needed.
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
            client.createDB("crudtest");
            Database db = client.database("crudtest", false);

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
        author.setRoleId("admin");
        author.setDescription("The website administrator.");
        author.setContent(
                "<p>This is a quick bio about the author.<br><br>But for now its simply a placeholder.<br><br>Enjoy your new blog!</p>");
        author.setEmail(Application.getString("default.email"));
        author.setThumbnail("/img/placeholder-200.png");
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
        post.setAuthorId(Application.getString("default.username"));

        post.setTitle("Welcome to Oddox!");
        post.setDescription("Here is a sample post demonstrating this blog platform.");
        post.setContent(
                "<p>Here is a welcome post that will eventually be outlining all the features of Oddox.<br><br>But for now its simply a placeholder.<br><br>Enjoy your new blog!</p>");
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("test");
        tags.add("sample");
        post.setTags(tags);
        post.setCategory("Welcome");
        post.setBanner("/img/placeholder-640.png");
        post.setThumbnail("/img/placeholder-640.png");
        post.setBannerCaption("Time to Relax!");
        post.setPublished(true);
        post.setFeatured(true);
        String now = Utils.getDateIso8601();
        post.setPublishDate(now);
        post.setCreateDate(now);
        post.setModifyDate(now);
        return post;
    }

    /**
     * Create default roles to insert in the database.
     * 
     * @return List
     */
    protected List<Role> getDefaultRoles() {
        ArrayList<Role> roles = new ArrayList<Role>();

        String now = Utils.getDateIso8601();

        Role admin = new Role("admin");
        admin.setName("Admin");
        admin.setPublic(false);
        admin.setPostsCreate(false);
        admin.setPostsEdit(true);
        admin.setPostsEditOthers(true);
        admin.setPostsSeeHidden(true);
        admin.setPostsDelete(true);
        admin.setUsersCreate(true);
        admin.setUsersEdit(true);
        admin.setUsersEditOthers(true);
        admin.setUsersDelete(true);
        admin.setRolesCreate(true);
        admin.setRolesEdit(true);
        admin.setRolesDelete(true);
        admin.setPagesCreate(true);
        admin.setPagesEdit(true);
        admin.setPagesDelete(true);
        admin.setCommentsCreate(false);
        admin.setCommentsEdit(false);
        admin.setCommentsEditOthers(false);
        admin.setCommentsDelete(false);
        admin.setSettingsCreate(true);
        admin.setSettingsEdit(true);
        admin.setSettingsDelete(true);
        admin.setCreateDate(now);
        admin.setModifyDate(now);

        Role owner = new Role("owner");
        owner.setName("Owner");
        owner.setPublic(true);
        owner.setPostsCreate(true);
        owner.setPostsEdit(true);
        owner.setPostsEditOthers(true);
        owner.setPostsSeeHidden(false);
        owner.setPostsDelete(true);
        owner.setUsersCreate(true);
        owner.setUsersEdit(false);
        owner.setUsersEditOthers(false);
        owner.setUsersDelete(true);
        owner.setRolesCreate(true);
        owner.setRolesEdit(true);
        owner.setRolesDelete(true);
        owner.setPagesCreate(true);
        owner.setPagesEdit(true);
        owner.setPagesDelete(true);
        owner.setCommentsCreate(true);
        owner.setCommentsEdit(true);
        owner.setCommentsEditOthers(false);
        owner.setCommentsDelete(true);
        owner.setSettingsCreate(true);
        owner.setSettingsEdit(true);
        owner.setSettingsDelete(true);
        owner.setCreateDate(now);
        owner.setModifyDate(now);

        Role author = new Role("author");
        author.setName("author");
        author.setPublic(true);
        author.setPostsCreate(true);
        author.setPostsEdit(true);
        author.setPostsEditOthers(false);
        author.setPostsSeeHidden(false);
        author.setPostsDelete(true);
        author.setUsersCreate(false);
        author.setUsersEdit(true);
        author.setUsersEditOthers(false);
        author.setUsersDelete(false);
        author.setRolesCreate(false);
        author.setRolesEdit(false);
        author.setRolesDelete(false);
        author.setPagesCreate(false);
        author.setPagesEdit(false);
        author.setPagesDelete(false);
        author.setCommentsCreate(true);
        author.setCommentsEdit(true);
        author.setCommentsEditOthers(true);
        author.setCommentsDelete(true);
        author.setSettingsCreate(false);
        author.setSettingsEdit(false);
        author.setSettingsDelete(false);
        author.setCreateDate(now);
        author.setModifyDate(now);

        Role editor = new Role("editor");
        editor.setName("Editor");
        editor.setPublic(true);
        editor.setPostsCreate(true);
        editor.setPostsEdit(true);
        editor.setPostsEditOthers(true);
        editor.setPostsSeeHidden(true);
        editor.setPostsDelete(true);
        editor.setUsersCreate(false);
        editor.setUsersEdit(true);
        editor.setUsersEditOthers(true);
        editor.setUsersDelete(false);
        editor.setRolesCreate(false);
        editor.setRolesEdit(false);
        editor.setRolesDelete(false);
        editor.setPagesCreate(true);
        editor.setPagesEdit(true);
        editor.setPagesDelete(true);
        editor.setCommentsCreate(true);
        editor.setCommentsEdit(true);
        editor.setCommentsEditOthers(true);
        editor.setCommentsDelete(true);
        editor.setSettingsCreate(false);
        editor.setSettingsEdit(false);
        editor.setSettingsDelete(false);
        editor.setCreateDate(now);
        editor.setModifyDate(now);

        roles.add(admin);
        roles.add(owner);
        roles.add(editor);
        roles.add(author);
        return roles;
    }
}
