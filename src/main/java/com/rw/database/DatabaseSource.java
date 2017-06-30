package com.rw.database;

import java.util.ArrayList;

import com.rw.model.Author;
import com.rw.model.Database;
import com.rw.model.Post;

/**
 * A blueprint to communicate to a Database Service.
 * 
 * @author Austin Delamar
 * @date 3/8/2017
 */
public abstract class DatabaseSource {

    protected Database database;

    public DatabaseSource(Database database) {
        this.database = database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public Database getDatabase() {
        return database;
    }

    public abstract Post getPost(String uri, boolean includeHidden);

    public abstract Post newPost(Post post);

    public abstract boolean editPost(Post post);

    public abstract boolean deletePost(Post post);

    public abstract Author getAuthor(String uri, boolean includeHidden);

    public abstract boolean editAuthor(Author author);

    public abstract boolean deleteAuthor(Author author);

    public abstract ArrayList<Post> getArchiveFeatured();

    public abstract ArrayList<String> getArchiveYears();

    public abstract ArrayList<String> getArchiveCategories();

    public abstract ArrayList<String> getArchiveTags();

    public abstract ArrayList<String> getPostUris();

    public abstract ArrayList<Post> getPosts(int page, int limit, boolean includeHidden);

    public abstract ArrayList<Post> getPostsByCategory(int page, int limit, String category,
            boolean includeHidden);

    public abstract ArrayList<Post> getPostsByTag(int page, int limit, String tag,
            boolean includeHidden);

    public abstract ArrayList<Post> getPostsByYear(int page, int limit, int year,
            boolean includeHidden);

    public abstract ArrayList<Author> getAuthors(int page, int limit, boolean includeAdmins);

    public abstract Author getUser(String username);

    public abstract Author newUser(Author user);

    public abstract boolean editUser(Author user);

    public abstract boolean loginUser(Author user);

    public abstract boolean incrementPageViews(Post post, boolean sessionView);
    
}
