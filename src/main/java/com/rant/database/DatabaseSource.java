package com.rant.database;

import java.util.List;

import com.rant.model.Author;
import com.rant.model.Post;
import com.rant.model.Role;
import com.rant.model.User;

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

    public abstract boolean newPost(Post post);

    public abstract boolean editPost(Post post);

    public abstract boolean deletePost(Post post);

    public abstract Author getAuthor(String uri, boolean includeHidden);

    public abstract List<Post> getFeatured();

    public abstract List<String> getYears();

    public abstract List<String> getCategories();

    public abstract List<String> getTags();

    public abstract List<String> getPostUris();

    public abstract List<Post> getPosts(int page, int limit, boolean includeHidden);

    public abstract List<Post> getPostsByCategory(int page, int limit, String category,
            boolean includeHidden);

    public abstract List<Post> getPostsByTag(int page, int limit, String tag,
            boolean includeHidden);

    public abstract List<Post> getPostsByYear(int page, int limit, int year, boolean includeHidden);

    public abstract List<Author> getAuthors(int page, int limit, boolean includeAdmins);

    public abstract List<Role> getRoles();

    public abstract User getUser(String uri);

    public abstract boolean newUser(User user);

    public abstract boolean editUser(User user);

    public abstract boolean deleteUser(User user);

    public abstract boolean incrementPageViews(Post post, boolean sessionView);

}
