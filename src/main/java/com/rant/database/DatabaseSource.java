package com.rant.database;

import java.util.List;

import com.rant.model.Author;
import com.rant.model.Post;
import com.rant.model.Role;

/**
 * A blueprint to communicate to a Database Service.
 * 
 * @author Austin Delamar
 * @date 3/8/2017
 */
public abstract class DatabaseSource {

    protected com.rant.model.Database database;

    public DatabaseSource(com.rant.model.Database database) {
        this.database = database;
    }

    public void setDatabase(com.rant.model.Database database) {
        this.database = database;
    }

    public com.rant.model.Database getDatabase() {
        return database;
    }

    public abstract boolean test();

    public abstract Post getPost(String uri, boolean includeHidden);

    public abstract Post newPost(Post post);

    public abstract boolean editPost(Post post);

    public abstract boolean deletePost(Post post);

    public abstract Author getAuthor(String uri, boolean includeHidden);

    public abstract boolean editAuthor(Author author);

    public abstract boolean deleteAuthor(Author author);

    public abstract List<Post> getArchiveFeatured();

    public abstract List<String> getArchiveYears();

    public abstract List<String> getArchiveCategories();

    public abstract List<String> getArchiveTags();

    public abstract List<String> getPostUris();

    public abstract List<Post> getPosts(int page, int limit, boolean includeHidden);

    public abstract List<Post> getPostsByCategory(int page, int limit, String category,
            boolean includeHidden);

    public abstract List<Post> getPostsByTag(int page, int limit, String tag,
            boolean includeHidden);

    public abstract List<Post> getPostsByYear(int page, int limit, int year, boolean includeHidden);

    public abstract List<Author> getAuthors(int page, int limit, boolean includeAdmins);

    public abstract Author getUser(String username);

    public abstract Author newUser(Author user);

    public abstract boolean editUser(Author user);

    public abstract boolean loginUser(Author user);

    public abstract boolean incrementPageViews(Post post, boolean sessionView);

    public abstract List<Role> getRoles();

}
