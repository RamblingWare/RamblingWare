package com.rant.config;

import java.io.InvalidClassException;
import java.net.MalformedURLException;
import java.net.URL;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.DesignDocumentManager;
import com.cloudant.client.api.model.DesignDocument;
import com.cloudant.client.org.lightcouch.CouchDbException;
import com.cloudant.client.org.lightcouch.DocumentConflictException;
import com.cloudant.client.org.lightcouch.NoDocumentException;
import com.google.gson.JsonObject;

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
            
            // Check user TODO

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
            
            // Create user TODO
            //Utils.downloadUrlFile(database.getUrl()+"/_config/admins/pickles -d '\"12345\"'");

            Database blog = client.database("blog", true);
            DesignDocument blogdesign = DesignDocumentManager
                    .fromFile(Utils.getResourceAsFile("/design/blogdesign.json"));
            blog.getDesignDocumentManager().put(blogdesign);

            Database access = client.database("access", true);
            DesignDocument accessdesign = DesignDocumentManager
                    .fromFile(Utils.getResourceAsFile("/design/accessdesign.json"));
            access.getDesignDocumentManager().put(accessdesign);

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
}
