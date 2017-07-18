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
import com.rant.model.Author;
import com.rant.model.Post;
import com.rant.model.Role;

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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public Post newPost(Post post) {
        // Auto-generated method stub
        return null;
    }

    @Override
    public boolean editPost(Post post) {
        // Auto-generated method stub
        return false;
    }

    @Override
    public boolean deletePost(Post post) {
        // Auto-generated method stub
        return false;
    }

    @Override
    public Author getAuthor(String uri, boolean includeHidden) {
        // Auto-generated method stub
        return null;
    }

    @Override
    public boolean editAuthor(Author author) {
        // Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteAuthor(Author author) {
        // Auto-generated method stub
        return false;
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
        // Auto-generated method stub
        return null;
    }

    @Override
    public Author getUser(String username) {
        // Auto-generated method stub
        return null;
    }

    @Override
    public Author newUser(Author user) {
        // Auto-generated method stub
        return null;
    }

    @Override
    public boolean editUser(Author user) {
        // Auto-generated method stub
        return false;
    }

    @Override
    public boolean loginUser(Author user) {
        // Auto-generated method stub
        return false;
    }

    @Override
    public boolean incrementPageViews(Post post, boolean sessionView) {
        // Auto-generated method stub
        return false;
    }

    @Override
    public List<Role> getRoles() {
        // TODO Auto-generated method stub
        return null;
    }
}