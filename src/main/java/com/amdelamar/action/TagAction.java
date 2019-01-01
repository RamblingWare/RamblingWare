package com.amdelamar.action;

import java.util.List;

import com.amdelamar.config.Application;
import com.amdelamar.config.Utils;
import com.amdelamar.objects.Post;
import com.amdelamar.objects.Tag;
import com.amdelamar.OddoxVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudant.client.org.lightcouch.NoDocumentException;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.templ.FreeMarkerTemplateEngine;
import io.vertx.reactivex.ext.web.templ.TemplateEngine;

/**
 * Tag action class
 * 
 * @author amdelamar
 * @date 3/19/2017
 */
public class TagAction implements Handler<RoutingContext> {

    private final static Logger logger = LoggerFactory.getLogger(TagAction.class);
    private final static TemplateEngine ENGINE = FreeMarkerTemplateEngine.create();
    private List<Post> posts = null;
    private String tag;
    private int page;
    private int nextPage;
    private int prevPage;
    private int totalPages;
    private int totalPosts;

    /**
     * Returns list of posts by tag
     */
    @Override
    public void handle(RoutingContext context) {
        
        // Don't handle if response ended
        if (context.response().ended() || context.response().closed()) {
            return;
        }

        // /tag
        String templateFile = "tag/tag.ftl";
        tag = context.request()
                .getParam("tag");
        try {
            // jump to page if provided
            page = Integer.parseInt(context.request()
                    .getParam("page"));
        } catch (Exception e) {
            page = 1;
        }

        try {
            if (tag != null && !tag.isEmpty()) {
                // dont lowercase
                tag = Utils.removeBadChars(tag);

                // gather posts
                posts = Application.getDatabaseService()
                        .getPostsByTag(page, Application.getInt("resultsPerPage"), tag, false);

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
                    List<Tag> archiveTags = (List<Tag>) context.get("archiveTags");
                    for (Tag tags : archiveTags) {
                        if (tag.equals(tags.getName())) {
                            totalPosts = tags.getCount();
                            break;
                        }
                    }
                    totalPages = (int) Math.ceil(((double) totalPosts / Application.getDouble("resultsPerPage")));
                } else {
                    posts = null;
                    throw new NoDocumentException("No posts found");
                }

            } else {
                logger.error("Tag '" + tag + "' not found. Please try again.");
                templateFile = "tag/tag.ftl";
            }

        } catch (NoDocumentException | NumberFormatException nfe) {
            logger.error("Tag '" + tag + "' not found. Please try again.");
            templateFile = "tag/tag.ftl";
        } catch (Exception e) {
            logger.error("Error: " + e.getClass()
                    .getName() + ". Please try again later.", e);
            templateFile = "/error/error.ftl";
        }

        // Bind Context
        context.put("tag", tag);
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = Utils.removeBadChars(tag);
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
