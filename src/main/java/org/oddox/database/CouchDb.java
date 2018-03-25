package org.oddox.database;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.oddox.config.AppConfig;
import org.oddox.config.AppFirewall;
import org.oddox.config.AppHeaders;
import org.oddox.objects.Author;
import org.oddox.objects.Category;
import org.oddox.objects.Post;
import org.oddox.objects.Tag;
import org.oddox.objects.View;
import org.oddox.objects.Year;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.views.Key;
import com.cloudant.client.api.views.ViewResponse;
import com.cloudant.client.org.lightcouch.DocumentConflictException;
import com.cloudant.client.org.lightcouch.NoDocumentException;

public class CouchDb extends DatabaseService {

    public CouchDb(org.oddox.database.Database database) {
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

    @Override
    public AppConfig getAppConfig() {
        try {
            CloudantClient client = getConnection();
            Database db = client.database("application", false);
            return db.find(AppConfig.class, "APPCONFIG");

        } catch (Exception e) {
            // this should be thrown up
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean editAppConfig(AppConfig appConfig) {
        try {
            CloudantClient client = getConnection();
            Database db = client.database("application", false);
            db.update(appConfig);
            return true;
        } catch (Exception e) {
            // this should be thrown up
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public AppFirewall getAppFirewall() {
        try {
            CloudantClient client = getConnection();
            Database db = client.database("application", false);
            return db.find(AppFirewall.class, "APPFIREWALL");

        } catch (Exception e) {
            // this should be thrown up
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean editAppFirewall(AppFirewall appFirewall) {
        try {
            CloudantClient client = getConnection();
            Database db = client.database("application", false);
            db.update(appFirewall);
            return true;
        } catch (Exception e) {
            // this should be thrown up
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public AppHeaders getAppHeaders() {
        try {
            CloudantClient client = getConnection();
            Database db = client.database("application", false);
            return db.find(AppHeaders.class, "APPHEADERS");

        } catch (Exception e) {
            // this should be thrown up
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean editAppHeaders(AppHeaders appHeaders) {
        try {
            CloudantClient client = getConnection();
            Database db = client.database("application", false);
            db.update(appHeaders);
            return true;
        } catch (Exception e) {
            // this should be thrown up
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

            if (!includeHidden && (!post.isPublished() || post.isDeleted())) {
                // post is not visible yet
                post = null;
            } else {
                // get author for this post
                try {
                    db = client.database("authors", false);
                    post.setAuthor(db.find(Author.class, post.getAuthorId()));
                } catch (Exception e) {
                    // ignore.
                    Author author = new Author(post.getAuthorId());
                    author.setName(post.getAuthorId());
                    post.setAuthor(author);
                }

                if (post.getCoauthorIds() != null && !post.getCoauthorIds()
                        .isEmpty()) {
                    // get each coauthor
                    ArrayList<Author> coauthors = new ArrayList<Author>();
                    db = client.database("authors", false);
                    for (String coa : post.getCoauthorIds()) {
                        try {
                            Author coauthor = db.find(Author.class, coa);
                            coauthors.add(coauthor);
                        } catch (Exception e) {
                            // ignore and skip.
                        }
                    }
                    post.setCoauthors(coauthors);
                }

                if (post.getEditorIds() != null && !post.getEditorIds()
                        .isEmpty()) {
                    // get each editor
                    ArrayList<Author> editors = new ArrayList<Author>();
                    db = client.database("authors", false);
                    for (String edt : post.getEditorIds()) {
                        try {
                            Author editor = db.find(Author.class, edt);
                            editors.add(editor);
                        } catch (Exception e) {
                            // ignore and skip.
                        }
                    }
                    post.setEditors(editors);
                }
            }

            View view = new View();
            try {
                // get view count
                db = client.database("views", false);
                view = db.find(View.class, post.get_Id());
            } catch (NoDocumentException e) {
                // no view count yet
                // so start at 0
                view.set_Id(post.get_Id());
                view.setCount(0l);
                view.setSession(0l);
            }
            post.setView(view);

        } catch (NoDocumentException e) {
            post = null;
        } catch (Exception e) {
            // this should be thrown up
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
            // this should be thrown up
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
                    .newPaginatedRequest(Key.Type.STRING, Object.class)
                    .rowsPerPage(limit)
                    .includeDocs(true)
                    .build()
                    .getResponse();

            for (int i = 1; i < page; i++) {
                if (pg.getNextPageToken() != null) {
                    // next page
                    pg = db.getViewRequestBuilder("authors", "authors")
                            .newPaginatedRequest(Key.Type.STRING, Object.class)
                            .rowsPerPage(limit)
                            .includeDocs(true)
                            .build()
                            .getResponse(pg.getNextPageToken());
                } else {
                    // page does not exist
                    return authors;
                }
            }

            authors = pg.getDocsAs(Author.class);

        } catch (Exception e) {
            // this should be thrown up
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
                    .newRequest(Key.Type.STRING, Object.class)
                    .includeDocs(true)
                    .build()
                    .getResponse();

            boolean hasNextPage = true;
            while (hasNextPage) {
                posts.addAll(pg.getDocsAs(Post.class));

                if (pg.hasNextPage()) {
                    pg = pg.nextPage();
                } else {
                    hasNextPage = false;
                }
            }

        } catch (Exception e) {
            // this should be thrown up
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
                    .newPaginatedRequest(Key.Type.STRING, Integer.class)
                    .descending(true)
                    .group(true)
                    .build()
                    .getResponse();

            boolean hasNextPage = true;
            while (hasNextPage) {
                for (int i = 0; i < pg.getRows()
                        .size(); i++) {
                    Year yr = new Year();
                    yr.setName(pg.getKeys()
                            .get(i));
                    yr.setCount(pg.getValues()
                            .get(i));
                    years.add(yr);
                }

                if (pg.hasNextPage()) {
                    pg = pg.nextPage();
                } else {
                    hasNextPage = false;
                }
            }

        } catch (Exception e) {
            // this should be thrown up
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

            ViewResponse<String, Integer> pg = db.getViewRequestBuilder("posts", "category-count")
                    .newPaginatedRequest(Key.Type.STRING, Integer.class)
                    .group(true)
                    .build()
                    .getResponse();

            boolean hasNextPage = true;
            while (hasNextPage) {
                for (int i = 0; i < pg.getRows()
                        .size(); i++) {
                    Category cat = new Category();
                    cat.setName(pg.getKeys()
                            .get(i));
                    cat.setCount(pg.getValues()
                            .get(i));
                    categories.add(cat);
                }

                if (pg.hasNextPage()) {
                    pg = pg.nextPage();
                } else {
                    hasNextPage = false;
                }
            }

        } catch (Exception e) {
            // this should be thrown up
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
                    .newPaginatedRequest(Key.Type.STRING, Integer.class)
                    .group(true)
                    .build()
                    .getResponse();

            boolean hasNextPage = true;
            while (hasNextPage) {
                for (int i = 0; i < pg.getRows()
                        .size(); i++) {
                    Tag tag = new Tag();
                    tag.setName(pg.getKeys()
                            .get(i));
                    tag.setCount(pg.getValues()
                            .get(i));
                    tags.add(tag);
                }

                if (pg.hasNextPage()) {
                    pg = pg.nextPage();
                } else {
                    hasNextPage = false;
                }
            }

        } catch (Exception e) {
            // this should be thrown up
            e.printStackTrace();
        }
        return tags;
    }

    @Override
    public List<Post> getPosts(int page, int limit, boolean includeHidden) {
        List<Post> posts = new ArrayList<Post>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("posts", false);
            String view = includeHidden ? "posts-all" : "posts-published";
            ViewResponse<String, Object> pg = db.getViewRequestBuilder("posts", view)
                    .newPaginatedRequest(Key.Type.STRING, Object.class)
                    .rowsPerPage(limit)
                    .descending(true)
                    .includeDocs(true)
                    .build()
                    .getResponse();

            for (int i = 1; i < page; i++) {
                if (pg.getNextPageToken() != null) {
                    // next page
                    pg = db.getViewRequestBuilder("posts", view)
                            .newPaginatedRequest(Key.Type.STRING, Object.class)
                            .rowsPerPage(limit)
                            .descending(true)
                            .includeDocs(true)
                            .build()
                            .getResponse(pg.getNextPageToken());
                } else {
                    // page does not exist
                    return posts;
                }
            }
            posts = pg.getDocsAs(Post.class);

            // cache map of authors
            HashMap<String, Author> authorCacheMap = new HashMap<String, Author>();

            for (Post post : posts) {
                // get author for each post

                // check cache first
                if (authorCacheMap.containsKey(post.getAuthorId())) {
                    post.setAuthor(authorCacheMap.get(post.getAuthorId()));
                } else {
                    try {
                        db = client.database("authors", false);
                        post.setAuthor(db.find(Author.class, post.getAuthorId()));
                        authorCacheMap.put(post.getAuthorId(), post.getAuthor());
                    } catch (Exception e) {
                        // ignore.
                        Author author = new Author(post.getAuthorId());
                        author.setName(post.getAuthorId());
                        post.setAuthor(author);
                    }
                }

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
                    views.setCount(0l);
                    views.setSession(0l);
                }
                post.setView(views);
            }

        } catch (Exception e) {
            // this should be thrown up
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public List<Post> getPostsByCategory(int page, int limit, String category, boolean includeHidden) {
        List<Post> posts = new ArrayList<Post>();
        try {
            CloudantClient client = getConnection();
            Database db = client.database("posts", false);
            String view = includeHidden ? "category-all" : "category-published";
            ViewResponse<String, String> pg = db.getViewRequestBuilder("posts", view)
                    .newPaginatedRequest(Key.Type.STRING, String.class)
                    .rowsPerPage(limit)
                    .reduce(false)
                    .descending(true)
                    .keys(category)
                    .includeDocs(true)
                    .build()
                    .getResponse();

            for (int i = 1; i < page; i++) {
                if (pg.getNextPageToken() != null) {
                    // next page
                    pg = db.getViewRequestBuilder("posts", view)
                            .newPaginatedRequest(Key.Type.STRING, String.class)
                            .rowsPerPage(limit)
                            .reduce(false)
                            .descending(true)
                            .keys(category)
                            .includeDocs(true)
                            .build()
                            .getResponse(pg.getNextPageToken());
                } else {
                    // page does not exist
                    return posts;
                }
            }
            posts = pg.getDocsAs(Post.class);

            // cache map of authors
            HashMap<String, Author> authorCacheMap = new HashMap<String, Author>();

            for (Post post : posts) {
                // get author for each post

                // check cache first
                if (authorCacheMap.containsKey(post.getAuthorId())) {
                    post.setAuthor(authorCacheMap.get(post.getAuthorId()));
                } else {
                    try {
                        db = client.database("authors", false);
                        post.setAuthor(db.find(Author.class, post.getAuthorId()));
                        authorCacheMap.put(post.getAuthorId(), post.getAuthor());
                    } catch (Exception e) {
                        // ignore.
                        Author author = new Author(post.getAuthorId());
                        author.setName(post.getAuthorId());
                        post.setAuthor(author);
                    }
                }

                // get views for each post
                View views = new View();
                try {
                    // get view count
                    db = client.database("views", false);
                    views = db.find(View.class, post.get_Id());
                } catch (NoDocumentException e) {
                    // no view count yet
                    // so start at 0
                    views.setCount(0l);
                    views.setSession(0l);
                }
                views.set_Id(post.get_Id());
                post.setView(views);
            }

        } catch (Exception e) {
            // this should be thrown up
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
                    .newPaginatedRequest(Key.Type.STRING, String.class)
                    .rowsPerPage(limit)
                    .reduce(false)
                    .descending(true)
                    .keys(tag)
                    .includeDocs(true)
                    .build()
                    .getResponse();

            for (int i = 1; i < page; i++) {
                if (pg.getNextPageToken() != null) {
                    // next page
                    pg = db.getViewRequestBuilder("posts", view)
                            .newPaginatedRequest(Key.Type.STRING, String.class)
                            .rowsPerPage(limit)
                            .reduce(false)
                            .descending(true)
                            .keys(tag)
                            .includeDocs(true)
                            .build()
                            .getResponse(pg.getNextPageToken());
                } else {
                    // page does not exist
                    return posts;
                }
            }
            posts = pg.getDocsAs(Post.class);

            // cache map of authors
            HashMap<String, Author> authorCacheMap = new HashMap<String, Author>();

            for (Post post : posts) {
                // get author for each post

                // check cache first
                if (authorCacheMap.containsKey(post.getAuthorId())) {
                    post.setAuthor(authorCacheMap.get(post.getAuthorId()));
                } else {
                    try {
                        db = client.database("authors", false);
                        post.setAuthor(db.find(Author.class, post.getAuthorId()));
                        authorCacheMap.put(post.getAuthorId(), post.getAuthor());
                    } catch (Exception e) {
                        // ignore.
                        Author author = new Author(post.getAuthorId());
                        author.setName(post.getAuthorId());
                        post.setAuthor(author);
                    }
                }

                // get views for each post
                View views = new View();
                try {
                    // get view count
                    db = client.database("views", false);
                    views = db.find(View.class, post.get_Id());
                } catch (NoDocumentException e) {
                    // no view count yet
                    // so start at 0
                    views.setCount(0l);
                    views.setSession(0l);
                }
                views.set_Id(post.get_Id());
                post.setView(views);
            }

        } catch (Exception e) {
            // this should be thrown up
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
                    .newPaginatedRequest(Key.Type.STRING, String.class)
                    .rowsPerPage(limit)
                    .reduce(false)
                    .descending(true)
                    .startKey(startKey)
                    .endKey(endKey)
                    .includeDocs(true)
                    .build()
                    .getResponse();

            for (int i = 1; i < page; i++) {
                if (pg.getNextPageToken() != null) {
                    // next page
                    pg = db.getViewRequestBuilder("posts", view)
                            .newPaginatedRequest(Key.Type.STRING, String.class)
                            .rowsPerPage(limit)
                            .reduce(false)
                            .descending(true)
                            .startKey(startKey)
                            .endKey(endKey)
                            .includeDocs(true)
                            .build()
                            .getResponse(pg.getNextPageToken());
                } else {
                    // page does not exist
                    return posts;
                }
            }
            posts = pg.getDocsAs(Post.class);

            // cache map of authors
            HashMap<String, Author> authorCacheMap = new HashMap<String, Author>();

            for (Post post : posts) {
                // get author for each post

                // check cache first
                if (authorCacheMap.containsKey(post.getAuthorId())) {
                    post.setAuthor(authorCacheMap.get(post.getAuthorId()));
                } else {
                    try {
                        db = client.database("authors", false);
                        post.setAuthor(db.find(Author.class, post.getAuthorId()));
                        authorCacheMap.put(post.getAuthorId(), post.getAuthor());
                    } catch (Exception e) {
                        // ignore.
                        Author author = new Author(post.getAuthorId());
                        author.setName(post.getAuthorId());
                        post.setAuthor(author);
                    }
                }

                // get views for each post
                View views = new View();
                try {
                    // get view count
                    db = client.database("views", false);
                    views = db.find(View.class, post.get_Id());
                } catch (NoDocumentException e) {
                    // no view count yet
                    // so start at 0
                    views.setCount(0l);
                    views.setSession(0l);
                }
                views.set_Id(post.get_Id());
                post.setView(views);
            }

        } catch (Exception e) {
            // this should be thrown up
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public boolean editView(View view) {
        try {
            CloudantClient client = getConnection();
            Database db = client.database("views", false);

            if (view.get_Rev() != null) {
                db.update(view);
            } else {
                db.save(view);
            }
            return true;
        } catch (IllegalArgumentException | DocumentConflictException e) {
            // quietly ignore.
            return true;
        } catch (Exception e) {
            // this should be thrown up
            e.printStackTrace();
            return false;
        }
    }
}
