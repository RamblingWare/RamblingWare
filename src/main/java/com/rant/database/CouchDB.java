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
import com.cloudant.client.org.lightcouch.DocumentConflictException;
import com.cloudant.client.org.lightcouch.NoDocumentException;
import com.rant.model.Author;
import com.rant.model.Category;
import com.rant.model.Post;
import com.rant.model.Role;
import com.rant.model.Tag;
import com.rant.model.User;
import com.rant.model.View;
import com.rant.model.Year;

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
                post = null;
            } else {
                // get author for each post
                try {
                    db = client.database("rantusers", false);
                    post.setAuthor(db.find(Author.class, post.getAuthor_id()));
                } catch (Exception e) {
                    // ignore.
                    Author author = new Author(post.getAuthor_id());
                    author.setName(post.getAuthor_id());
                    post.setAuthor(author);
                }
            }

            View view = new View();
            if (includeHidden) {
                try {
                    // get view count
                    db = client.database("rantviews", true);
                    view = db.find(View.class, uri);
                } catch (NoDocumentException e) {
                    // no view count yet
                    // so start at 0
                    view.set_Id(uri);
                }
            }
            post.setView(view);

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
            
            // nullify author
            post.setAuthor_id(post.getAuthor().get_Id());
            post.setAuthor(null);
            
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
            
            // nullify author
            post.setAuthor_id(post.getAuthor().get_Id());
            post.setAuthor(null);
            
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
            db = client.database("rantviews", false);
            db.remove(post.getView());
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
            Database db = client.database("rantusers", false);

            author = db.find(Author.class, uri);

        } catch (NoDocumentException e) {
            author = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return author;
    }

    @Override
    public List<Author> getAuthors(int page, int limit, boolean includeAdmins) {
        List<Author> authors = new ArrayList<Author>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantusers", false);

            ViewResponse<String, Object> pg = db.getViewRequestBuilder("rantdesign", "users")
                    .newPaginatedRequest(Key.Type.STRING, Object.class).rowsPerPage(limit)
                    .includeDocs(true).build().getResponse();

            for (int i = 1; i < page; i++) {
                if (pg.getNextPageToken() != null) {
                    // next page
                    pg = db.getViewRequestBuilder("rantdesign", "users")
                            .newPaginatedRequest(Key.Type.STRING, Object.class).rowsPerPage(limit)
                            .includeDocs(true).build().getResponse(pg.getNextPageToken());
                } else {
                    // page does not exist
                    return authors;
                }
            }

            authors = pg.getDocsAs(Author.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return authors;
    }

    @Override
    public List<Post> getFeatured() {
        List<Post> posts = new ArrayList<Post>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantdb", false);

            ViewResponse<String, Object> pg = db.getViewRequestBuilder("rantdesign", "featured")
                    .newRequest(Key.Type.STRING, Object.class).includeDocs(true).build()
                    .getResponse();

            posts = pg.getDocsAs(Post.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public List<Year> getYears() {
        List<Year> years = new ArrayList<Year>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantdb", false);

            ViewResponse<String, Integer> pg = db.getViewRequestBuilder("rantdesign", "year-count")
                    .newPaginatedRequest(Key.Type.STRING, Integer.class).group(true).build()
                    .getResponse();

            for (int i = 0; i < pg.getTotalRowCount(); i++) {
                Year yr = new Year();
                yr.setName(pg.getKeys().get(i));
                yr.setCount(pg.getValues().get(i));
                years.add(yr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return years;
    }

    @Override
    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<Category>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantdb", false);

            ViewResponse<String, Integer> pg = db
                    .getViewRequestBuilder("rantdesign", "category-count")
                    .newPaginatedRequest(Key.Type.STRING, Integer.class).group(true).build()
                    .getResponse();

            for (int i = 0; i < pg.getTotalRowCount(); i++) {
                Category cat = new Category();
                cat.setName(pg.getKeys().get(i));
                cat.setCount(pg.getValues().get(i));
                categories.add(cat);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public List<Tag> getTags() {
        List<Tag> tags = new ArrayList<Tag>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantdb", false);

            ViewResponse<String, Integer> pg = db.getViewRequestBuilder("rantdesign", "tag-count")
                    .newPaginatedRequest(Key.Type.STRING, Integer.class).group(true).build()
                    .getResponse();

            for (int i = 0; i < pg.getTotalRowCount(); i++) {
                Tag tag = new Tag();
                tag.setName(pg.getKeys().get(i));
                tag.setCount(pg.getValues().get(i));
                tags.add(tag);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tags;
    }

    @Override
    public List<String> getPostUris() {
        List<String> uris = new ArrayList<String>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantdb", false);

            uris = db.getViewRequestBuilder("rantdesign", "posts-all")
                    .newPaginatedRequest(Key.Type.STRING, String.class).build().getResponse()
                    .getKeys();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return uris;
    }

    @Override
    public List<Post> getPosts(int page, int limit, boolean includeHidden) {
        List<Post> posts = new ArrayList<Post>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantdb", false);
            String view = includeHidden ? "posts-all" : "posts-published";
            ViewResponse<String, Object> pg = db.getViewRequestBuilder("rantdesign", view)
                    .newPaginatedRequest(Key.Type.STRING, Object.class).rowsPerPage(limit)
                    .descending(true).includeDocs(true).build().getResponse();

            for (int i = 1; i < page; i++) {
                if (pg.getNextPageToken() != null) {
                    // next page
                    pg = db.getViewRequestBuilder("rantdesign", view)
                            .newPaginatedRequest(Key.Type.STRING, Object.class).rowsPerPage(limit)
                            .descending(true).includeDocs(true).build()
                            .getResponse(pg.getNextPageToken());
                } else {
                    // page does not exist
                    return posts;
                }
            }
            posts = pg.getDocsAs(Post.class);

            for (Post post : posts) {
                // get author for each post
                try {
                    db = client.database("rantusers", false);
                    post.setAuthor(db.find(Author.class, post.getAuthor_id()));
                } catch (Exception e) {
                    // ignore.
                    Author author = new Author(post.getAuthor_id());
                    author.setName(post.getAuthor_id());
                    post.setAuthor(author);
                }

                if (includeHidden) {
                    // get views for each post
                    View views = new View();
                    try {
                        // get view count
                        db = client.database("rantviews", true);
                        views = db.find(View.class, post.get_Id());
                    } catch (NoDocumentException e) {
                        // no view count yet
                        // so start at 0
                        views.set_Id(post.get_Id());
                    }
                    post.setView(views);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public List<Post> getPostsByCategory(int page, int limit, String category,
            boolean includeHidden) {
        List<Post> posts = new ArrayList<Post>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantdb", false);
            String view = includeHidden ? "category-all" : "category-published";
            ViewResponse<String, String> pg = db.getViewRequestBuilder("rantdesign", view)
                    .newPaginatedRequest(Key.Type.STRING, String.class).rowsPerPage(limit)
                    .reduce(false).descending(true).keys(category).includeDocs(true).build()
                    .getResponse();

            for (int i = 1; i < page; i++) {
                if (pg.getNextPageToken() != null) {
                    // next page
                    pg = db.getViewRequestBuilder("rantdesign", view)
                            .newPaginatedRequest(Key.Type.STRING, String.class).rowsPerPage(limit)
                            .reduce(false).descending(true).keys(category).includeDocs(true).build()
                            .getResponse(pg.getNextPageToken());
                } else {
                    // page does not exist
                    return posts;
                }
            }
            posts = pg.getDocsAs(Post.class);
            
            for (Post post : posts) {
                // get author for each post
                try {
                    db = client.database("rantusers", false);
                    post.setAuthor(db.find(Author.class, post.getAuthor_id()));
                } catch (Exception e) {
                    // ignore.
                    Author author = new Author(post.getAuthor_id());
                    author.setName(post.getAuthor_id());
                    post.setAuthor(author);
                }

                if (includeHidden) {
                    // get views for each post
                    View views = new View();
                    try {
                        // get view count
                        db = client.database("rantviews", true);
                        views = db.find(View.class, post.get_Id());
                    } catch (NoDocumentException e) {
                        // no view count yet
                        // so start at 0
                        views.set_Id(post.get_Id());
                    }
                    post.setView(views);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public List<Post> getPostsByTag(int page, int limit, String tag, boolean includeHidden) {
        List<Post> posts = new ArrayList<Post>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantdb", false);
            String view = includeHidden ? "tag-all" : "tag-published";
            ViewResponse<String, String> pg = db.getViewRequestBuilder("rantdesign", view)
                    .newPaginatedRequest(Key.Type.STRING, String.class).rowsPerPage(limit)
                    .reduce(false).descending(true).keys(tag).includeDocs(true).build()
                    .getResponse();

            for (int i = 1; i < page; i++) {
                if (pg.getNextPageToken() != null) {
                    // next page
                    pg = db.getViewRequestBuilder("rantdesign", view)
                            .newPaginatedRequest(Key.Type.STRING, String.class).rowsPerPage(limit)
                            .reduce(false).descending(true).keys(tag).includeDocs(true).build()
                            .getResponse(pg.getNextPageToken());
                } else {
                    // page does not exist
                    return posts;
                }
            }
            posts = pg.getDocsAs(Post.class);
            
            for (Post post : posts) {
                // get author for each post
                try {
                    db = client.database("rantusers", false);
                    post.setAuthor(db.find(Author.class, post.getAuthor_id()));
                } catch (Exception e) {
                    // ignore.
                    Author author = new Author(post.getAuthor_id());
                    author.setName(post.getAuthor_id());
                    post.setAuthor(author);
                }

                if (includeHidden) {
                    // get views for each post
                    View views = new View();
                    try {
                        // get view count
                        db = client.database("rantviews", true);
                        views = db.find(View.class, post.get_Id());
                    } catch (NoDocumentException e) {
                        // no view count yet
                        // so start at 0
                        views.set_Id(post.get_Id());
                    }
                    post.setView(views);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public List<Post> getPostsByYear(int page, int limit, int year, boolean includeHidden) {
        List<Post> posts = new ArrayList<Post>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantdb", false);
            String view = includeHidden ? "year-all" : "year-published";
            String endKey = year + "-01-01T00:00:00.000Z";
            String startKey = year + "-12-31T23:59:59.999Z";

            ViewResponse<String, String> pg = db.getViewRequestBuilder("rantdesign", view)
                    .newPaginatedRequest(Key.Type.STRING, String.class).rowsPerPage(limit)
                    .reduce(false).descending(true).startKey(startKey).endKey(endKey)
                    .includeDocs(true).build().getResponse();

            for (int i = 1; i < page; i++) {
                if (pg.getNextPageToken() != null) {
                    // next page
                    pg = db.getViewRequestBuilder("rantdesign", view)
                            .newPaginatedRequest(Key.Type.STRING, String.class).rowsPerPage(limit)
                            .reduce(false).descending(true).startKey(startKey).endKey(endKey)
                            .includeDocs(true).build().getResponse(pg.getNextPageToken());
                } else {
                    // page does not exist
                    return posts;
                }
            }
            posts = pg.getDocsAs(Post.class);
            
            for (Post post : posts) {
                // get author for each post
                try {
                    db = client.database("rantusers", false);
                    post.setAuthor(db.find(Author.class, post.getAuthor_id()));
                } catch (Exception e) {
                    // ignore.
                    Author author = new Author(post.getAuthor_id());
                    author.setName(post.getAuthor_id());
                    post.setAuthor(author);
                }

                if (includeHidden) {
                    // get views for each post
                    View views = new View();
                    try {
                        // get view count
                        db = client.database("rantviews", true);
                        views = db.find(View.class, post.get_Id());
                    } catch (NoDocumentException e) {
                        // no view count yet
                        // so start at 0
                        views.set_Id(post.get_Id());
                    }
                    post.setView(views);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
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

    @Override
    public User getUser(String uri) {
        User user = null;
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantusers", false);

            user = db.find(User.class, uri);

            Role role = db.find(Role.class, user.getRole().get_Id());
            user.setRole(role);

        } catch (NoDocumentException e) {
            user = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> getUsers(int page, int limit, boolean includeAdmins) {
        List<User> users = new ArrayList<User>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantusers", false);

            ViewResponse<String, Object> pg = db.getViewRequestBuilder("rantdesign", "users")
                    .newPaginatedRequest(Key.Type.STRING, Object.class).rowsPerPage(limit)
                    .includeDocs(true).build().getResponse();

            for (int i = 1; i < page; i++) {
                if (pg.getNextPageToken() != null) {
                    // next page
                    pg = db.getViewRequestBuilder("rantdesign", "users")
                            .newPaginatedRequest(Key.Type.STRING, Object.class).rowsPerPage(limit)
                            .includeDocs(true).build().getResponse(pg.getNextPageToken());
                } else {
                    // page does not exist
                    return users;
                }
            }

            users = pg.getDocsAs(User.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean newUser(User user) {
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantusers", false);
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
            Database db = client.database("rantusers", false);
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
            Database db = client.database("rantusers", false);
            db.remove(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean incrementPageViews(Post post, boolean sessionView) {
        try {
            CloudantClient client = getConnection();
            Database db = client.database("rantviews", true);

            post.getView().setCount(post.getView().getCount() + 1);
            if (sessionView) {
                post.getView().setSession(post.getView().getSession() + 1);
            }

            if (db.contains(post.getView().get_Id())) {
                db.update(post.getView());
            } else {
                db.save(post.getView());
            }
            return true;
        } catch (IllegalArgumentException | DocumentConflictException e) {
            // quietly ignore.
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}