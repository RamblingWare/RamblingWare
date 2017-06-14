package com.rw.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.rw.config.Application;
import com.rw.config.Utils;
import com.rw.model.Post;

/**
 * Category action class
 * 
 * @author Austin Delamar
 * @date 4/30/2017
 */
public class CategoryAction extends ActionSupport
        implements
            ServletResponseAware,
            ServletRequestAware {

    private static final long serialVersionUID = 1L;

    private ArrayList<Post> posts;
    private String category;
    private int page;
    private static final int LIMIT = 7;
    private boolean nextPage;
    private boolean prevPage;

    public String execute() {

        // /category

        // this shows the most recent blog posts by category
        try {
            // jump to page if provided
            String pageTemp = servletRequest.getRequestURI().toLowerCase();
            if (pageTemp.startsWith("/category/") && pageTemp.contains("/page/")) {
                category = Utils.removeBadChars(pageTemp.substring(10, pageTemp.indexOf("/page")));
                pageTemp = Utils.removeBadChars(
                        pageTemp.substring(pageTemp.indexOf("/page/") + 6, pageTemp.length()));
                page = Integer.parseInt(pageTemp);
            } else if (pageTemp.startsWith("/category/")) {
                category = Utils.removeBadChars(pageTemp.substring(10, pageTemp.length()));
                page = 1;
            }

            // gather posts
            posts = Application.getDatabaseSource().getPostsByCategory(page, LIMIT, category,
                    false);

            // determine pagination
            nextPage = posts.size() >= LIMIT;
            prevPage = page > 1;

            // set attributes
            servletRequest.setAttribute("posts", posts);
            servletRequest.setAttribute("page", page);
            servletRequest.setAttribute("nextPage", nextPage);
            servletRequest.setAttribute("prevPage", prevPage);

            return SUCCESS;

        } catch (NumberFormatException nfe) {
            return NONE;
        } catch (Exception e) {
            addActionError("Error: " + e.getClass().getName() + ". Please try again later.");
            e.printStackTrace();
            return ERROR;
        }
    }

    protected HttpServletResponse servletResponse;

    @Override
    public void setServletResponse(HttpServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }

    protected HttpServletRequest servletRequest;

    @Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
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

    public boolean isNextPage() {
        return nextPage;
    }

    public void setNextPage(boolean nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isPrevPage() {
        return prevPage;
    }

    public void setPrevPage(boolean prevPage) {
        this.prevPage = prevPage;
    }
}