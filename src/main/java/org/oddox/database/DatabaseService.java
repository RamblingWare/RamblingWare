package org.oddox.database;

import java.util.List;

import org.oddox.config.AppConfig;
import org.oddox.config.AppFirewall;
import org.oddox.objects.Author;
import org.oddox.objects.Category;
import org.oddox.objects.Post;
import org.oddox.objects.Role;
import org.oddox.objects.Tag;
import org.oddox.objects.View;
import org.oddox.objects.Year;

/**
 * A blueprint to communicate to a Database Service.
 * 
 * @author Austin Delamar
 * @date 3/8/2017
 */
public abstract class DatabaseService {

    protected org.oddox.database.Database database;

    public DatabaseService(org.oddox.database.Database database) {
        this.database = database;
    }

    public void setDatabase(org.oddox.database.Database database) {
        this.database = database;
    }

    public org.oddox.database.Database getDatabase() {
        return database;
    }

    public abstract AppConfig getAppConfig();

    public abstract boolean editAppConfig(AppConfig appConfig);

    public abstract AppFirewall getAppFirewall();

    public abstract boolean editAppFirewall(AppFirewall appFirewall);

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

    public abstract DatabaseUser getDatabaseUser(String username);

    public abstract boolean loginUser(String username, String password);

    public abstract boolean editView(View view);

}
