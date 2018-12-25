package com.amdelamar.database;

import java.util.List;

import com.amdelamar.config.AppConfig;
import com.amdelamar.config.AppFirewall;
import com.amdelamar.config.AppHeaders;
import com.amdelamar.objects.Author;
import com.amdelamar.objects.Category;
import com.amdelamar.objects.Post;
import com.amdelamar.objects.Tag;
import com.amdelamar.objects.View;
import com.amdelamar.objects.Year;

import com.cloudant.client.api.model.DbInfo;

/**
 * A blueprint to communicate to a Database Service.
 * 
 * @author amdelamar
 * @date 3/8/2017
 */
public abstract class DatabaseService {

    protected Database database;

    public DatabaseService(Database database) {
        this.database = database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public Database getDatabase() {
        return database;
    }
    
    public abstract DbInfo getInfo();
    
    public abstract String getVersion();

    public abstract AppConfig getAppConfig();

    public abstract boolean editAppConfig(AppConfig appConfig);

    public abstract AppFirewall getAppFirewall();

    public abstract boolean editAppFirewall(AppFirewall appFirewall);
    
    public abstract AppHeaders getAppHeaders();

    public abstract boolean editAppHeaders(AppHeaders appHeaders);

    public abstract Post getPost(String uri, boolean includeHidden);

    public abstract Author getAuthor(String uri, boolean includeHidden);

    public abstract List<Author> getAuthors(int page, int limit, boolean includeAdmins);

    public abstract List<Post> getFeatured();

    public abstract List<Year> getYears();

    public abstract List<Category> getCategories();

    public abstract List<Tag> getTags();

    public abstract List<Post> getPosts(int page, int limit, boolean includeHidden);

    public abstract List<Post> getPostsByCategory(int page, int limit, String category, boolean includeHidden);

    public abstract List<Post> getPostsByTag(int page, int limit, String tag, boolean includeHidden);

    public abstract List<Post> getPostsByYear(int page, int limit, int year, boolean includeHidden);

    public abstract boolean editView(View view);

}
