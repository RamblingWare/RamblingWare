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
import com.rant.objects.Author;
import com.rant.objects.Category;
import com.rant.objects.Config;
import com.rant.objects.Post;
import com.rant.objects.Role;
import com.rant.objects.Tag;
import com.rant.objects.User;
import com.rant.objects.View;
import com.rant.objects.Year;

public class CouchDB extends DatabaseService {

    public CouchDB(com.rant.objects.Database database) {
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
        return ClientBuilder.url(new URL(database.getUrl())).username(database.getUsername())
                .password(database.getPassword()).build();
    }

    @Override
    public Config getConfig() {
        try {
            CloudantClient client = getConnection();
            Database db = client.database("application", false);
            return db.find(Config.class, "APPCONFIG");

        } catch (Exception e) {
            // TODO this should be thrown up
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean editConfig(Config config) {
        try {
            CloudantClient client = getConnection();
            Database db = client.database("application", false);
            db.update(config);
            return true;
        } catch (Exception e) {
            // TODO this should be thrown up
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Post getPost(String uri, boolean includeHidden) {
        Post post = null;
        try {
            CloudantClient client = getConnection();
            Database db = client.database("posts", false);

            post = db.find(Post.class, uri);

            if (!includeHidden && !post.isPublished()) {
                post = null;
            } else {
                // get author for each post
                try {
                    db = client.database("authors", false);
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
                    db = client.database("views", false);
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
            // TODO this should be thrown up
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public Author getAuthor(String uri, boolean includeHidden) {
        Author author = null;
        try {
            CloudantClient client = getConnection();
            Database db = client.database("authors", false);

            author = db.find(Author.class, uri);

        } catch (NoDocumentException e) {
            author = null;
        } catch (Exception e) {
            // TODO this should be thrown up
            e.printStackTrace();
        }
        return author;
    }

    @Override
    public List<Author> getAuthors(int page, int limit, boolean includeAdmins) {
        List<Author> authors = new ArrayList<Author>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("authors", false);

            ViewResponse<String, Object> pg = db.getViewRequestBuilder("authors", "authors")
                    .newPaginatedRequest(Key.Type.STRING, Object.class).rowsPerPage(limit)
                    .includeDocs(true).build().getResponse();

            for (int i = 1; i < page; i++) {
                if (pg.getNextPageToken() != null) {
                    // next page
                    pg = db.getViewRequestBuilder("authors", "authors")
                            .newPaginatedRequest(Key.Type.STRING, Object.class).rowsPerPage(limit)
                            .includeDocs(true).build().getResponse(pg.getNextPageToken());
                } else {
                    // page does not exist
                    return authors;
                }
            }

            authors = pg.getDocsAs(Author.class);

        } catch (Exception e) {
            // TODO this should be thrown up
            e.printStackTrace();
        }
        return authors;
    }

    @Override
    public List<Post> getFeatured() {
        List<Post> posts = new ArrayList<Post>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("posts", false);

            ViewResponse<String, Object> pg = db.getViewRequestBuilder("posts", "featured")
                    .newRequest(Key.Type.STRING, Object.class).includeDocs(true).build()
                    .getResponse();

            posts = pg.getDocsAs(Post.class);

        } catch (Exception e) {
            // TODO this should be thrown up
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public List<Year> getYears() {
        List<Year> years = new ArrayList<Year>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("posts", false);

            ViewResponse<String, Integer> pg = db.getViewRequestBuilder("posts", "year-count")
                    .newPaginatedRequest(Key.Type.STRING, Integer.class).group(true).build()
                    .getResponse();

            for (int i = 0; i < pg.getTotalRowCount(); i++) {
                Year yr = new Year();
                yr.setName(pg.getKeys().get(i));
                yr.setCount(pg.getValues().get(i));
                years.add(yr);
            }

        } catch (Exception e) {
            // TODO this should be thrown up
            e.printStackTrace();
        }
        return years;
    }

    @Override
    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<Category>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("posts", false);

            ViewResponse<String, Integer> pg = db
                    .getViewRequestBuilder("posts", "category-count")
                    .newPaginatedRequest(Key.Type.STRING, Integer.class).group(true).build()
                    .getResponse();

            for (int i = 0; i < pg.getTotalRowCount(); i++) {
                Category cat = new Category();
                cat.setName(pg.getKeys().get(i));
                cat.setCount(pg.getValues().get(i));
                categories.add(cat);
            }

        } catch (Exception e) {
            // TODO this should be thrown up
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public List<Tag> getTags() {
        List<Tag> tags = new ArrayList<Tag>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("posts", false);

            ViewResponse<String, Integer> pg = db.getViewRequestBuilder("posts", "tag-count")
                    .newPaginatedRequest(Key.Type.STRING, Integer.class).group(true).build()
                    .getResponse();

            for (int i = 0; i < pg.getTotalRowCount(); i++) {
                Tag tag = new Tag();
                tag.setName(pg.getKeys().get(i));
                tag.setCount(pg.getValues().get(i));
                tags.add(tag);
            }

        } catch (Exception e) {
            // TODO this should be thrown up
            e.printStackTrace();
        }
        return tags;
    }

    @Override
    public List<String> getPostUris() {
        List<String> uris = new ArrayList<String>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("posts", false);

            uris = db.getViewRequestBuilder("posts", "posts-all")
                    .newPaginatedRequest(Key.Type.STRING, String.class).build().getResponse()
                    .getKeys();

        } catch (Exception e) {
            // TODO this should be thrown up
            e.printStackTrace();
        }
        return uris;
    }

    @Override
    public List<Post> getPosts(int page, int limit, boolean includeHidden) {
        List<Post> posts = new ArrayList<Post>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("posts", false);
            String view = includeHidden ? "posts-all" : "posts-published";
            ViewResponse<String, Object> pg = db.getViewRequestBuilder("posts", view)
                    .newPaginatedRequest(Key.Type.STRING, Object.class).rowsPerPage(limit)
                    .descending(true).includeDocs(true).build().getResponse();

            for (int i = 1; i < page; i++) {
                if (pg.getNextPageToken() != null) {
                    // next page
                    pg = db.getViewRequestBuilder("posts", view)
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
                    db = client.database("authors", false);
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
                        db = client.database("views", false);
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
            // TODO this should be thrown up
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
            Database db = client.database("posts", false);
            String view = includeHidden ? "category-all" : "category-published";
            ViewResponse<String, String> pg = db.getViewRequestBuilder("posts", view)
                    .newPaginatedRequest(Key.Type.STRING, String.class).rowsPerPage(limit)
                    .reduce(false).descending(true).keys(category).includeDocs(true).build()
                    .getResponse();

            for (int i = 1; i < page; i++) {
                if (pg.getNextPageToken() != null) {
                    // next page
                    pg = db.getViewRequestBuilder("posts", view)
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
                    db = client.database("authors", false);
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
                        db = client.database("views", false);
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
            // TODO this should be thrown up
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public List<Post> getPostsByTag(int page, int limit, String tag, boolean includeHidden) {
        List<Post> posts = new ArrayList<Post>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("posts", false);
            String view = includeHidden ? "tag-all" : "tag-published";
            ViewResponse<String, String> pg = db.getViewRequestBuilder("posts", view)
                    .newPaginatedRequest(Key.Type.STRING, String.class).rowsPerPage(limit)
                    .reduce(false).descending(true).keys(tag).includeDocs(true).build()
                    .getResponse();

            for (int i = 1; i < page; i++) {
                if (pg.getNextPageToken() != null) {
                    // next page
                    pg = db.getViewRequestBuilder("posts", view)
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
                    db = client.database("", false);
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
                        db = client.database("views", false);
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
            // TODO this should be thrown up
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public List<Post> getPostsByYear(int page, int limit, int year, boolean includeHidden) {
        List<Post> posts = new ArrayList<Post>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("posts", false);
            String view = includeHidden ? "year-all" : "year-published";
            String endKey = year + "-01-01T00:00:00.000Z";
            String startKey = year + "-12-31T23:59:59.999Z";

            ViewResponse<String, String> pg = db.getViewRequestBuilder("posts", view)
                    .newPaginatedRequest(Key.Type.STRING, String.class).rowsPerPage(limit)
                    .reduce(false).descending(true).startKey(startKey).endKey(endKey)
                    .includeDocs(true).build().getResponse();

            for (int i = 1; i < page; i++) {
                if (pg.getNextPageToken() != null) {
                    // next page
                    pg = db.getViewRequestBuilder("posts", view)
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
                    db = client.database("authors", false);
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
                        db = client.database("views", false);
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
            // TODO this should be thrown up
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public List<Role> getRoles() {
        List<Role> roles = new ArrayList<Role>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("roles", false);

            ViewResponse<String, Object> pg = db.getViewRequestBuilder("roles", "roles")
                    .newRequest(Key.Type.STRING, Object.class).includeDocs(true).build()
                    .getResponse();

            roles = pg.getDocsAs(Role.class);

        } catch (Exception e) {
            // TODO this should be thrown up
            e.printStackTrace();
        }
        return roles;
    }

    @Override
    public User getUser(String username) {
        User user = null;
        try {
            CloudantClient client = getConnection();
            Database db = client.database("_users", false);

            user = db.find(User.class, "org.couchdb.user:" + username.toLowerCase());

        } catch (NoDocumentException e) {
            user = null;
        } catch (Exception e) {
            // TODO this should be thrown up
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean loginUser(String username, String password) {
        try {
            CloudantClient client = ClientBuilder.url(new URL(database.getUrl())).username(username)
                    .password(password).build();

            Database db = client.database("_users", false);
            db.find(User.class, "org.couchdb.user:" + username.toLowerCase());

            return true;
        } catch (Exception e) {
            // quietly ignore.
            return false;
        }
    }

    @Override
    public boolean incrementPageViews(Post post, boolean sessionView) {
        try {
            CloudantClient client = getConnection();
            Database db = client.database("views", false);

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
            // TODO this should be thrown up
            e.printStackTrace();
            return false;
        }
    }
}