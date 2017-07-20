package com.rant.database;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.views.Key;
import com.cloudant.client.api.views.ViewResponse;
import com.cloudant.client.org.lightcouch.NoDocumentException;
import com.rant.model.Author;
import com.rant.model.Post;
import com.rant.model.Role;
import com.rant.model.User;

public class CouchDB extends DatabaseSource {

    public CouchDB(com.rant.model.Database database) {
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
    @Override
    public boolean test() {
        try {
            CloudantClient client = getConnection();

            // Show the server version
            System.out.println("CouchDB Version: " + client.serverVersion());

            // Test Create, Insert, Delete
            Database couchdb = client.database("rantdb-test", true);
            couchdb.save(database);
            client.deleteDB("rantdb-test");

            return true;

        } catch (Exception e) {
            System.err.println("Failed Database Test!");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Post getPost(String uri, boolean includeHidden) {
        Post post = null;
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantdb", false);

            post = db.find(Post.class, uri);

            if (!includeHidden && !post.isPublished()) {
                // post = null;
            }

        } catch (NoDocumentException e) {
            post = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public boolean newPost(Post post) {
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantdb", false);
            db.save(post);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean editPost(Post post) {
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantdb", false);
            db.update(post);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deletePost(Post post) {
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantdb", false);
            db.remove(post);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Author getAuthor(String uri, boolean includeHidden) {
        Author author = null;
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantdb", false);

            author = db.find(Author.class, uri);

        } catch (NoDocumentException e) {
            author = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return author;
    }

    @Override
    public List<Post> getArchiveFeatured() {
        // Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getArchiveYears() {
        // Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getArchiveCategories() {
        // Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getArchiveTags() {
        // Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getPostUris() {
        // Auto-generated method stub
        return null;
    }

    @Override
    public List<Post> getPosts(int page, int limit, boolean includeHidden) {
        List<Post> posts = new ArrayList<Post>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantdb", false);

            ViewResponse<String, Object> pg = db.getViewRequestBuilder("rantdesign", "posts")
                    .newPaginatedRequest(Key.Type.STRING, Object.class).rowsPerPage(limit)
                    .includeDocs(true).build().getResponse();

            posts = pg.getDocsAs(Post.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public List<Post> getPostsByCategory(int page, int limit, String category,
            boolean includeHidden) {
        // Auto-generated method stub
        return null;
    }

    @Override
    public List<Post> getPostsByTag(int page, int limit, String tag, boolean includeHidden) {
        // Auto-generated method stub
        return null;
    }

    @Override
    public List<Post> getPostsByYear(int page, int limit, int year, boolean includeHidden) {
        // Auto-generated method stub
        return null;
    }

    @Override
    public List<Author> getAuthors(int page, int limit, boolean includeAdmins) {
        List<Author> authors = new ArrayList<Author>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantdb", false);

            ViewResponse<String, Object> pg = db.getViewRequestBuilder("rantdesign", "authors")
                    .newPaginatedRequest(Key.Type.STRING, Object.class).rowsPerPage(limit)
                    .includeDocs(true).build().getResponse();

            authors = pg.getDocsAs(Author.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return authors;
    }

    @Override
    public User getUser(String uri) {
        User user = null;
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantdb", false);

            user = db.find(User.class, uri);

        } catch (NoDocumentException e) {
            user = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean newUser(User user) {
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantdb", false);
            db.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean editUser(User user) {
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantdb", false);
            db.update(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUser(User user) {
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantdb", false);
            db.remove(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean incrementPageViews(Post post, boolean sessionView) {
        // Auto-generated method stub
        return true;
    }

    @Override
    public List<Role> getRoles() {
        List<Role> roles = new ArrayList<Role>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantdb", false);

            ViewResponse<String, Object> pg = db.getViewRequestBuilder("rantdesign", "roles")
                    .newRequest(Key.Type.STRING, Object.class).includeDocs(true).build()
                    .getResponse();

            roles = pg.getDocsAs(Role.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return roles;
    }
}