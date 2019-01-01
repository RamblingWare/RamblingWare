package com.amdelamar;

import com.amdelamar.action.api.ForgotAction;
import com.amdelamar.action.api.HealthAction;
import com.amdelamar.action.api.RootAction;
import com.amdelamar.action.interceptor.AppInterceptor;
import com.amdelamar.action.interceptor.ArchiveInterceptor;
import com.amdelamar.action.interceptor.DynamicContentInterceptor;
import com.amdelamar.action.interceptor.StaticContentInterceptor;
import com.amdelamar.action.AuthorAction;
import com.amdelamar.action.AuthorsAction;
import com.amdelamar.action.BlogAction;
import com.amdelamar.action.CategoriesAction;
import com.amdelamar.action.CategoryAction;
import com.amdelamar.action.PostAction;
import com.amdelamar.action.RssAction;
import com.amdelamar.action.SearchAction;
import com.amdelamar.action.TagAction;
import com.amdelamar.action.TagsAction;
import com.amdelamar.action.YearAction;
import com.amdelamar.action.YearsAction;

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

    private static final String ROOT = "/blog";

    private WebRoutes() {
        // prevent instantiation
    }

    /**
     * Creates and maps all web router access points.
     *
     * @param vertx Vertx
     * @return Router
     */
    public static Router newRouter(Vertx vertx) {
        // Map All Routes
        Router main = Router.router(vertx);

        // Static Resources
        main.routeWithRegex("(" + ROOT + "){1}(.+)[.](css|js|htm|html|txt|md|csv|jpg|jpeg|png|ico|ttf|woff|eot|svg)$")
                .handler(cr -> cr.reroute(cr.normalisedPath().replaceFirst(ROOT,"")));
        main.routeWithRegex("(.+)[.](css|js|htm|html|txt|md|csv|jpg|jpeg|png|ico|ttf|woff|eot|svg)$")
                .handler(StaticHandler.create()
                        .setAlwaysAsyncFS(true)
                        .setFilesReadOnly(true)
                        .setMaxAgeSeconds(31536000l)
                        .setCachingEnabled(true)
                        .setCacheEntryTimeout(31536000l));

        // Interceptors for HTTP headers and  Context attributes
        main.route("/*")
                .handler(new DynamicContentInterceptor());
        main.route("/*")
                .handler(new AppInterceptor());
        main.route("/*")
                .handler(new ArchiveInterceptor());

        // Template Actions

        // Author
        main.route("/author/:author")
                .handler(new AuthorAction());
        main.route(ROOT + "/author/:author")
                .handler(new AuthorAction());
        main.route("/author")
                .handler(new AuthorsAction());
        main.route(ROOT + "/author")
                .handler(new AuthorsAction());

        // Category
        main.route("/category/:category")
                .handler(new CategoryAction());
        main.route(ROOT + "/category/:category")
                .handler(new CategoryAction());
        main.route("/category/:category/page/:page")
                .handler(new CategoryAction());
        main.route(ROOT + "/category/:category/page/:page")
                .handler(new CategoryAction());
        main.route("/category")
                .handler(new CategoriesAction());
        main.route(ROOT + "/category")
                .handler(new CategoriesAction());

        // Tag
        main.route("/tag/:tag")
                .handler(new TagAction());
        main.route(ROOT + "/tag/:tag")
                .handler(new TagAction());
        main.route("/tag/:tag/page/:page")
                .handler(new TagAction());
        main.route(ROOT + "/tag/:tag/page/:page")
                .handler(new TagAction());
        main.route("/tag")
                .handler(new TagsAction());
        main.route(ROOT + "/tag")
                .handler(new TagsAction());

        // Year
        main.route("/year/:year")
                .handler(new YearAction());
        main.route(ROOT + "/year/:year")
                .handler(new YearAction());
        main.route("/year/:year/page/:page")
                .handler(new YearAction());
        main.route(ROOT + "/year/:year/page/:page")
                .handler(new YearAction());
        main.route("/year")
                .handler(new YearsAction());
        main.route(ROOT + "/year")
                .handler(new YearsAction());

        // Other
        main.route("/rss")
                .handler(new RssAction());
        main.route(ROOT + "/rss")
                .handler(new RssAction());
        main.route("/search")
                .handler(new SearchAction());
        main.route(ROOT + "/search")
                .handler(new SearchAction());

        // APIs
        main.route("/api")
                .handler(new RootAction());
        main.route(ROOT + "/api")
                .handler(new RootAction());
        main.route("/api/forgot")
                .handler(new ForgotAction());
        main.route(ROOT + "/api/forgot")
                .handler(new ForgotAction());
        main.route("/api/health")
                .handler(new HealthAction());
        main.route(ROOT + "/api/health")
                .handler(new HealthAction());

        // Blog
        main.route("/")
                .handler(new BlogAction());
        main.route(ROOT)
                .handler(new BlogAction());
        main.route("/page/:page")
                .handler(new BlogAction());
        main.route(ROOT + "/page/:page")
                .handler(new BlogAction());
        main.route(ROOT + "/:post")
                .handler(new PostAction());
        main.route("/:post")
                .handler(new PostAction());

        return main;
    }

}
