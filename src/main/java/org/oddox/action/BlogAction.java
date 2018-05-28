package org.oddox.action;

import java.util.List;

import org.oddox.MainVerticle;
import org.oddox.action.interceptor.ArchiveInterceptor;
import org.oddox.config.Application;
import org.oddox.config.Utils;
import org.oddox.objects.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudant.client.org.lightcouch.NoDocumentException;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.templ.FreeMarkerTemplateEngine;
import io.vertx.reactivex.ext.web.templ.TemplateEngine;

/**
 * Blog list action class
 * 
 * @author Austin Delamar
 * @date 11/9/2015
 */
public class BlogAction implements Handler<RoutingContext> {

    private static Logger logger = LoggerFactory.getLogger(BlogAction.class);
    private final TemplateEngine ENGINE = FreeMarkerTemplateEngine.create();
    private List<Post> posts = null;
    private int page;
    private int nextPage;
    private int prevPage;
    private int totalPages;
    private int totalPosts;

    /**
     * Returns list of blog posts.
     */
    @Override
    public void handle(RoutingContext context) {
        
        // this shows the most recent blog posts
        String templateFile = "index.ftl";
        try {
            // jump to page if provided
            String pageTemp = context.normalisedPath().toLowerCase();
            if (pageTemp.startsWith("/blog/page/")) {
                pageTemp = Utils.removeBadChars(pageTemp.substring(11, pageTemp.length()));
                page = Integer.parseInt(pageTemp);
            } else if (pageTemp.startsWith("/page/")) {
                pageTemp = Utils.removeBadChars(pageTemp.substring(6, pageTemp.length()));
                page = Integer.parseInt(pageTemp);
            } else {
                page = 1;
            }

            // gather posts
            posts = Application.getDatabaseService()
                    .getPosts(page, Application.getInt("resultsPerPage"), false);

            if (posts != null && !posts.isEmpty()) {

                // determine pagination
                if (posts.size() >= Application.getInt("resultsPerPage")) {
                    nextPage = page + 1;
                } else {
                    nextPage = page;
                }
                if (page > 1) {
                    prevPage = page - 1;
                } else {
                    prevPage = page;
                }

                // get totals
                totalPosts = ArchiveInterceptor.archiveTotal;
                totalPages = (int) Math.ceil(((double) totalPosts / Application.getDouble("resultsPerPage")));
            } else {
                posts = null;
                throw new NoDocumentException("No posts found");
            }

        } catch (NoDocumentException | NumberFormatException nfe) {
            templateFile = "index.ftl";
        } catch (Exception e) {
            logger.error("Error: " + e.getClass()
                    .getName() + ". Please try again later.", e);
            templateFile = "/error/error.ftl";
        }

        // Render template response
        ENGINE.render(context, MainVerticle.TEMPLATES_DIR, templateFile, res -> {
            context.response().putHeader("content-type", "text/html;charset=UTF-8");
            if (res.succeeded()) {
                context.response()
                        .end(res.result());
            } else {
                context.fail(res.cause());
            }
        });
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(int prevPage) {
        this.prevPage = prevPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalPosts() {
        return totalPosts;
    }

    public void setTotalPosts(int totalPosts) {
        this.totalPosts = totalPosts;
    }
}
