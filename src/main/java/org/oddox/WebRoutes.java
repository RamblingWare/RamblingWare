package org.oddox;

import org.oddox.action.AuthorAction;
import org.oddox.action.AuthorsAction;
import org.oddox.action.BlogAction;
import org.oddox.action.CategoriesAction;
import org.oddox.action.CategoryAction;
import org.oddox.action.PostAction;
import org.oddox.action.RssAction;
import org.oddox.action.SearchAction;
import org.oddox.action.TagAction;
import org.oddox.action.TagsAction;
import org.oddox.action.YearAction;
import org.oddox.action.YearsAction;
import org.oddox.action.api.ForgotAction;
import org.oddox.action.api.HealthAction;
import org.oddox.action.api.RootAction;
import org.oddox.action.filter.DynamicContentFilter;
import org.oddox.action.filter.StaticContentFilter;
import org.oddox.action.interceptor.AppInterceptor;
import org.oddox.action.interceptor.ArchiveInterceptor;

import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.StaticHandler;

/**
 * WebRoutes creates the http route paths for actions.
 * 
 * @author amdelamar
 * @date 05/28/2018
 */
public final class WebRoutes {

    private WebRoutes() {
        // prevent instantiation
    }

    /**
     * Creates and maps all web router access points.
     * @param vertx Vertx
     * @return Router
     */
    public static Router newRouter(Vertx vertx) {
        // Map All Routes
        Router main = Router.router(vertx);

        // Static Resources
        main.routeWithRegex("(.+)[.](css|js|htm|html|txt|md|csv|jpg|jpeg|png|ico|tff|woff|eot|svg)$")
                .handler(StaticHandler.create()
                        .setAlwaysAsyncFS(true)
                        .setFilesReadOnly(true)
                        .setMaxAgeSeconds(31536000l)
                        .setCachingEnabled(true)
                        .setCacheEntryTimeout(31536000l));

        // Filters for HTTP headers
        main.route("/**.*")
                .handler(new StaticContentFilter());
        main.route("/*")
                .handler(new DynamicContentFilter());

        // Interceptors for setting Context attributes
        main.route("/*")
                .handler(new AppInterceptor());
        main.route("/*")
                .handler(new ArchiveInterceptor());

        // Template Actions

        // Blog
        main.route("/")
                .handler(new BlogAction());
        main.route("/blog")
                .handler(new BlogAction());
        main.route("/page/:page")
                .handler(new BlogAction());
        main.route("/blog/:post")
                .handler(new PostAction());

        // Author
        main.route("/author/:author")
                .handler(new AuthorAction());
        main.route("/author")
                .handler(new AuthorsAction());

        // Category
        main.route("/category/:category")
                .handler(new CategoryAction());
        main.route("/category/:category/page/:page")
                .handler(new CategoryAction());
        main.route("/category")
                .handler(new CategoriesAction());

        // Tag
        main.route("/tag/:tag")
                .handler(new TagAction());
        main.route("/tag/:tag/page/:page")
                .handler(new TagAction());
        main.route("/tag")
                .handler(new TagsAction());

        // Year
        main.route("/year/:year")
                .handler(new YearAction());
        main.route("/year/:year/page/:page")
                .handler(new YearAction());
        main.route("/year")
                .handler(new YearsAction());

        // Other
        main.route("/rss")
                .handler(new RssAction());
        main.route("/search")
                .handler(new SearchAction());

        // APIs
        main.route()
                .path("/api")
                .handler(new RootAction());
        main.route()
                .path("/api/forgot")
                .handler(new ForgotAction());
        main.route()
                .path("/api/health")
                .handler(new HealthAction());

        return main;
    }

}
