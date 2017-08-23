package com.rant.config;

import java.io.InvalidClassException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
import com.rant.model.Role;
import com.rant.model.User;

public class Setup {

    protected com.rant.model.Database database;

    public Setup(com.rant.model.Database database) {
        this.database = database;
    }

    public void setDatabase(com.rant.model.Database database) {
        this.database = database;
    }

    public com.rant.model.Database getDatabase() {
        return database;
    }

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

            if (!client.serverVersion().startsWith("2.0")) {
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

            Database access = client.database("access", false);
            access.getDesignDocumentManager().get("_design/accessdesign");

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
            client.createDB("_global_changes");
            client.createDB("_metadata");
            
            // create CouchDB _user "admin"
            HttpConnection request = Http.PUT(new URL(client.getBaseUri() +
                    "/_users/org.couchdb.user:admin"), "application/json");
            request.setRequestBody("{\"_id\":\"org.couchdb.user:admin\",\"name\":\"admin\",\"password\":\"admin\",\"roles\":[\"admin\",\"author\"],\"type\":\"user\"}");
            HttpConnection response = client.executeRequest(request);
            if (response.getConnection().getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                // failed to create admin
                throw new Exception("Failed to create default user 'admin'. Exception occured during install.");
            }
            
            // TODO create CouchDB permissions
            // - role = author
            // - role = admin

            Database blog = client.database("blog", true);
            DesignDocument blogdesign = DesignDocumentManager
                    .fromFile(Utils.getResourceAsFile("/design/blogdesign.json"));
            blog.getDesignDocumentManager().put(blogdesign);

            Database access = client.database("access", true);
            DesignDocument accessdesign = DesignDocumentManager
                    .fromFile(Utils.getResourceAsFile("/design/accessdesign.json"));
            access.getDesignDocumentManager().put(accessdesign);

            // default roles
            List<Role> roles = getDefaultRoles();
            access.bulk(roles);
            
            // default user
            User user = new User("admin");
            user.setName("Admin");
            user.setRole(roles.get(0));
            user.setDescription("");
            user.setContent("");
            user.setEmail("");
            user.setPassword(
                    "pbkdf2sha256:64000:18:n:kZ8hGWvCrIv1IW6PwWrDuJ2E:y34ZAyu6Swxud5L+AlvR5NgS");
            user.setThumbnail("");
            access.save(user);

            Database views = client.database("views", true);
            DesignDocument viewsdesign = DesignDocumentManager
                    .fromFile(Utils.getResourceAsFile("/design/viewsdesign.json"));
            views.getDesignDocumentManager().put(viewsdesign);

            Database security = client.database("security", true);
            security.save(Application.getConfig());

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed Database Installation: Exception occured during install.");
        }
        return false;
    }

    private List<Role> getDefaultRoles() {
        ArrayList<Role> roles = new ArrayList<Role>();

        Role adm = new Role("Admin");
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

        Role own = new Role("Owner");
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

        Role ath = new Role("Author");
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

        Role edt = new Role("Editor");
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
