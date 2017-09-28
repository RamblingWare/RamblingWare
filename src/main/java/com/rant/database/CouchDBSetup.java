package com.rant.database;

import java.io.InvalidClassException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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

public class CouchDBSetup extends DatabaseSetup {

    public CouchDBSetup(com.rant.objects.Database database) {
        super(database);
    }

    /**
     * Obtains a connection to the CouchDB if possible.
     * 
     * @return CloudantClient connection
     * @throws MalformedURLException
     *             if invalid url
     */
    private CloudantClient getConnection() throws MalformedURLException {
        return ClientBuilder.url(new URL(database.getUrl())).username(database.getUsername())
                .password(database.getPassword()).build();
    }

    /**
     * Quick CRUD test on the Database.
     * 
     * @returns boolean true if successful
     */
    public boolean test() {
        try {
            CloudantClient client = getConnection();

            if (!client.serverVersion().startsWith("2.0.0")) {
                throw new InvalidClassException("Failed Database Test: Unexpected CouchDB Version ("
                        + client.serverVersion() + ") was expecting (2.0.0).");
            }

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

        } catch (InvalidClassException e) {
            System.err.println(e.getMessage());
        } catch (NoDocumentException e) {
            System.err.println(e.getMessage());
            System.err.println(
                    "Failed Database Test: Please check permissions for ID given to create/edit/delete documents.");
        } catch (DocumentConflictException e) {
            System.err.println(e.getMessage());
            System.err.println(
                    "Failed Database Test: Please check permissions for ID given to create/edit/delete documents.");
        } catch (MalformedURLException | CouchDbException e) {
            System.err.println(e.getMessage());
            System.err
                    .println("Failed Database Test: Please check the Database URL and try again.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed Database Test: Exception occured during test.");
        }
        return false;
    }

    /**
     * Verify the Rant Design documents and databases needed.
     * 
     * @return true if verified
     */
    public boolean verify() {
        try {
            CloudantClient client = getConnection();

            client.database("_users", false);
            client.database("_replicator", false);

            Database blog = client.database("blog", false);
            blog.getDesignDocumentManager().get("_design/blogdesign");

            Database roles = client.database("roles", false);
            roles.getDesignDocumentManager().get("_design/rolesdesign");

            Database authors = client.database("authors", false);
            authors.getDesignDocumentManager().get("_design/authorsdesign");

            Database views = client.database("views", false);
            views.getDesignDocumentManager().get("_design/viewsdesign");

            Database security = client.database("security", false);
            security.find("APPCONFIG");

            return true;

        } catch (Exception e) {
            System.err.println("Failed Database Verification: Exception occured during verify: "
                    + e.getMessage());
        }
        return false;
    }

    /**
     * Install the Rant Design documents and databases needed.
     * 
     * @return true if install successful
     */
    public boolean install() {
        try {
            CloudantClient client = getConnection();

            client.createDB("_users");
            client.createDB("_replicator");

            // TODO create CouchDB permissions
            // - role = author
            // - role = admin

            // create default roles
            Database roles = client.database("roles", true);
            DesignDocument rolesdesign = DesignDocumentManager
                    .fromFile(Utils.getResourceAsFile("/design/rolesdesign.json"));
            roles.getDesignDocumentManager().put(rolesdesign);
            List<Role> defaultRoles = getDefaultRoles();
            roles.bulk(defaultRoles);

            // create default _user credentials
            HttpConnection request = Http.PUT(
                    new URL(client.getBaseUri() + "/_users/org.couchdb.user:admin"),
                    "application/json");
            request.setRequestBody(
                    "{\"_id\":\"org.couchdb.user:admin\",\"name\":\"admin\",\"password\":\"admin\",\"roles\":[\"admin\",\"author\"],\"type\":\"user\"}");
            HttpConnection response = client.executeRequest(request);
            if (response.getConnection().getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                // failed to create admin
                throw new Exception(
                        "Failed to create default user 'admin'. Exception occured during install.");
            }
            response.disconnect();

            // create default user profile
            Database authors = client.database("authors", true);
            DesignDocument authorsdesign = DesignDocumentManager
                    .fromFile(Utils.getResourceAsFile("/design/authorsdesign.json"));
            authors.getDesignDocumentManager().put(authorsdesign);

            // create default author
            authors.save(getDefaultAuthor());

            // create blog
            Database blog = client.database("blog", true);
            DesignDocument blogdesign = DesignDocumentManager
                    .fromFile(Utils.getResourceAsFile("/design/blogdesign.json"));
            blog.getDesignDocumentManager().put(blogdesign);
            
            // create default post
            blog.save(getDefaultPost());

            // create views
            Database views = client.database("views", true);
            DesignDocument viewsdesign = DesignDocumentManager
                    .fromFile(Utils.getResourceAsFile("/design/viewsdesign.json"));
            views.getDesignDocumentManager().put(viewsdesign);

            // default security config
            Database security = client.database("security", true);
            security.save(Application.getConfig());

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed Database Installation: Exception occured during install.");
        }
        return false;
    }
    
    /**
     * Create default author to insert in the database.
     * 
     * @return Author
     */
    protected Author getDefaultAuthor() {
        Author user = new Author("admin");
        user.setName("Admin");
        user.setRole(new Role("admin"));
        user.setDescription("The website administrator.");
        user.setContent("");
        user.setEmail("");
        user.setThumbnail("/img/placeholder-200.png");
        return user;
    }
    
    /**
     * Create default post to insert in the database.
     * 
     * @return Post
     */
    protected Post getDefaultPost() {
        Post post = new Post("welcome-to-rant");
        post.setAuthor_id("admin");
        
        post.setTitle("Welcome to Rant!");
        post.setDescription("Here is a sample post demonstrating this blog system.");
        post.setContent("<p>Here is a welcome post that will eventually be outlining all the features of Rant.<br><br>But for now its simply a placeholder.<br><br>Enjoy your new blog!</p>");
        
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
