package org.oddox.action;

import java.util.List;

import org.oddox.MainVerticle;
import org.oddox.config.Application;
import org.oddox.config.Utils;
import org.oddox.objects.Post;
import org.oddox.objects.Year;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudant.client.org.lightcouch.NoDocumentException;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.templ.FreeMarkerTemplateEngine;
import io.vertx.reactivex.ext.web.templ.TemplateEngine;

/**
 * Year action class
 * 
 * @author Austin Delamar
 * @date 3/19/2017
 */
public class YearAction implements Handler<RoutingContext> {

    private static Logger logger = LoggerFactory.getLogger(YearAction.class);
    private final TemplateEngine ENGINE = FreeMarkerTemplateEngine.create();
    private List<Post> posts = null;
    private String year;
    private int page;
    private int nextPage;
    private int prevPage;
    private int totalPages;
    private int totalPosts;

    /**
     * Returns list of posts for year.
     */
    @Override
    public void handle(RoutingContext context) {

        // /year
        String templateFile = "year/year.ftl";
        try {
            // jump to page if provided
            String pageTemp = context.normalisedPath();
            if (pageTemp.startsWith("/year/") && pageTemp.contains("/page/")) {
                year = Utils.removeBadChars(pageTemp.substring(6, pageTemp.indexOf("/page")));
                pageTemp = Utils.removeBadChars(pageTemp.substring(pageTemp.indexOf("/page/") + 6, pageTemp.length()));
                page = Integer.parseInt(pageTemp);

            } else if (pageTemp.startsWith("/year/")) {
                year = Utils.removeBadChars(pageTemp.substring(6, pageTemp.length()));
                page = 1;
            }

            // lower-case no matter what
            year = year.toLowerCase();
            int yr = Integer.parseInt(year);

            // gather posts
            posts = Application.getDatabaseService()
                    .getPostsByYear(page, Application.getInt("resultsPerPage"), yr, false);

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
                totalPosts = page;
                @SuppressWarnings("unchecked")
                List<Year> archiveYears = (List<Year>) context.get("archiveYears");
                for (Year yrs : archiveYears) {
                    if (year.equals(yrs.getName())) {
                        totalPosts = yrs.getCount();
                        break;
                    }
                }
                totalPages = (int) Math.ceil(((double) totalPosts / Application.getDouble("resultsPerPage")));
            } else {
                posts = null;
                throw new NoDocumentException("No posts found");
            }

        } catch (NoDocumentException | NumberFormatException nfe) {
            logger.error("Year '" + year + "' not found. Please try again.");
            templateFile = "year/year.ftl";
        } catch (Exception e) {
            logger.error("Error: " + e.getClass()
                    .getName() + ". Please try again later.", e);
            templateFile = "/error/error.ftl";
        }

        // Render template response
        ENGINE.render(context, MainVerticle.TEMPLATES_DIR, templateFile, res -> {
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = Utils.removeBadChars(year);
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
