package com.amdelamar.action;

import java.util.List;

import com.amdelamar.config.Application;
import com.amdelamar.config.Utils;
import com.amdelamar.objects.Post;
import com.amdelamar.objects.Year;
import com.amdelamar.OddoxVerticle;
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
 * @author amdelamar
 * @date 3/19/2017
 */
public class YearAction implements Handler<RoutingContext> {

    private final static Logger logger = LoggerFactory.getLogger(YearAction.class);
    private final static TemplateEngine ENGINE = FreeMarkerTemplateEngine.create();
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
        
        // Don't handle if response ended
        if(context.response().ended()) {
            context.next();
            return;
        }

        // /year
        String templateFile = "year/year.ftl";
        year = context.request()
                .getParam("year");
        try {
            // jump to page if provided
            page = Integer.parseInt(context.request()
                    .getParam("page"));
        } catch (Exception e) {
            page = 1;
        }

        try {
            if (year != null && !year.isEmpty()) {
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

            } else {
                logger.error("Year '" + year + "' not found. Please try again.");
                templateFile = "year/year.ftl";
            }

        } catch (NoDocumentException | NumberFormatException nfe) {
            logger.error("Year '" + year + "' not found. Please try again.");
            templateFile = "year/year.ftl";
        } catch (Exception e) {
            logger.error("Error: " + e.getClass()
                    .getName() + ". Please try again later.", e);
            templateFile = "/error/error.ftl";
        }

        // Bind Context
        context.put("year", year);
        context.put("posts", posts);
        context.put("page", page);
        context.put("nextPage", nextPage);
        context.put("prevPage", prevPage);
        context.put("totalPages", totalPages);
        context.put("totalPosts", totalPosts);

        // Render template response
        ENGINE.render(context, OddoxVerticle.TEMPLATES_DIR, templateFile, res -> {
            context.response()
                    .putHeader("content-type", "text/html;charset=UTF-8");
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
