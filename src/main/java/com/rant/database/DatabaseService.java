package com.rant.database;

import java.util.List;

import com.rant.objects.Author;
import com.rant.objects.Category;
import com.rant.objects.Config;
import com.rant.objects.Post;
import com.rant.objects.Role;
import com.rant.objects.Tag;
import com.rant.objects.User;
import com.rant.objects.Year;

/**
 * A blueprint to communicate to a Database Service.
 * 
 * @author Austin Delamar
 * @date 3/8/2017
 */
public abstract class DatabaseService {

    protected com.rant.objects.Database database;

    public DatabaseService(com.rant.objects.Database database) {
        this.database = database;
    }

    public void setDatabase(com.rant.objects.Database database) {
        this.database = database;
    }

    public com.rant.objects.Database getDatabase() {
        return database;
    }

    public abstract Config getConfig();

    public abstract boolean editConfig(Config config);

    public abstract Post getPost(String uri, boolean includeHidden);

    public abstract Author getAuthor(String uri, boolean includeHidden);

    public abstract List<Author> getAuthors(int page, int limit, boolean includeAdmins);

    public abstract List<Post> getFeatured();

    public abstract List<Year> getYears();

    public abstract List<Category> getCategories();

    public abstract List<Tag> getTags();

    public abstract List<String> getPostUris();

    public abstract List<Post> getPosts(int page, int limit, boolean includeHidden);

    public abstract List<Post> getPostsByCategory(int page, int limit, String category,
            boolean includeHidden);

    public abstract List<Post> getPostsByTag(int page, int limit, String tag,
            boolean includeHidden);

    public abstract List<Post> getPostsByYear(int page, int limit, int year, boolean includeHidden);

    public abstract List<Role> getRoles();
    
    public abstract User getUser(String username);

    public abstract boolean loginUser(String username, String password);
    
    public abstract boolean incrementPageViews(Post post, boolean sessionView);

}
