package org.oddox.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.oddox.config.Application;
import org.oddox.config.Utils;
import org.oddox.objects.Category;
import org.oddox.objects.Post;

import com.cloudant.client.org.lightcouch.NoDocumentException;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Category action class
 * 
 * @author Austin Delamar
 * @date 4/30/2017
 */
public class CategoryAction extends ActionSupport implements ServletResponseAware, ServletRequestAware {

    private static final long serialVersionUID = 1L;
    protected HttpServletResponse servletResponse;
    protected HttpServletRequest servletRequest;
    private List<Post> posts = null;
    private String category;
    private int page;
    private int nextPage;
    private int prevPage;
    private int totalPages;
    private int totalPosts;

    /**
     * Returns list of posts for category.
     * 
     * @return Action String
     */
    public String execute() {

        // /category
        try {
            // jump to page if provided
            String pageTemp = servletRequest.getRequestURI();
            if (pageTemp.startsWith("/category/") && pageTemp.contains("/page/")) {
                category = Utils.removeBadChars(pageTemp.substring(10, pageTemp.indexOf("/page")));
                pageTemp = Utils.removeBadChars(pageTemp.substring(pageTemp.indexOf("/page/") + 6, pageTemp.length()));
                page = Integer.parseInt(pageTemp);
            } else if (pageTemp.startsWith("/category/")) {
                category = Utils.removeBadChars(pageTemp.substring(10, pageTemp.length()));
                page = 1;
            }

            // lower-case no matter what
            category = category.toLowerCase();

            // gather posts
            posts = Application.getDatabaseService()
                    .getPostsByCategory(page, Application.getInt("default.limit"), category, false);

            if (posts != null && !posts.isEmpty()) {

                // determine pagination
                if (posts.size() >= Application.getInt("default.limit")) {
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
                totalPages = (int) Math.ceil(((double) totalPosts / Application.getDouble("default.limit")));
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

    @Override
    public void setServletResponse(HttpServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }

    @Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
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
