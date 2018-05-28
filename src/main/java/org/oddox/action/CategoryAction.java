package org.oddox.action;

import java.util.List;

import org.oddox.config.Application;
import org.oddox.config.Utils;
import org.oddox.objects.Category;
import org.oddox.objects.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudant.client.org.lightcouch.NoDocumentException;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;

/**
 * Category action class
 * 
 * @author Austin Delamar
 * @date 4/30/2017
 */
public class CategoryAction implements Handler<RoutingContext> {

    private static Logger logger = LoggerFactory.getLogger(CategoryAction.class);
    private List<Post> posts = null;
    private String category;
    private int page;
    private int nextPage;
    private int prevPage;
    private int totalPages;
    private int totalPosts;

    /**
     * Returns list of posts for category.
     */
    @Override
    public void handle(RoutingContext context) {

        // /category
        try {
            // jump to page if provided
            String pageTemp = context.normalisedPath();
            if (pageTemp.startsWith("/category/") && pageTemp.contains("/page/")) {
                category = Utils.removeBadChars(pageTemp.substring(10, pageTemp.indexOf("/page")));
                pageTemp = Utils.removeBadChars(pageTemp.substring(pageTemp.indexOf("/page/") + 6, pageTemp.length()));
                page = Integer.parseInt(pageTemp);
            } else if (pageTemp.startsWith("/category/")) {
                category = Utils.removeBadChars(pageTemp.substring(10, pageTemp.length()));
                page = 1;
            }

            // gather posts
            posts = Application.getDatabaseService()
                    .getPostsByCategory(page, Application.getInt("resultsPerPage"), category, false);

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
                List<Category> archiveCategories = (List<Category>) servletRequest
                        .getAttribute("archiveCategories");
                for (Category cat : archiveCategories) {
                    if (category.equals(cat.getName())) {
                        totalPosts = cat.getCount();
                        break;
                    }
                }
                totalPages = (int) Math.ceil(((double) totalPosts / Application.getDouble("resultsPerPage")));
            } else {
                posts = null;
                throw new NoDocumentException("No posts found");
            }

            return SUCCESS;

        } catch (NoDocumentException | NumberFormatException nfe) {
            System.err.println("Category '" + category + "' not found. Please try again.");
            return NONE;
        } catch (Exception e) {
            addActionError("Error: " + e.getClass()
                    .getName() + ". Please try again later.");
            e.printStackTrace();
            return ERROR;
        }
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = Utils.removeBadChars(category);
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
