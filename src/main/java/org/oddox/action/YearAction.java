package org.oddox.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.oddox.config.Application;
import org.oddox.config.Utils;
import org.oddox.objects.Post;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Year action class
 * 
 * @author Austin Delamar
 * @date 3/19/2017
 */
public class YearAction extends ActionSupport implements ServletResponseAware, ServletRequestAware {

    private static final long serialVersionUID = 1L;

    private List<Post> posts = null;
    private String year;
    private int page;
    private int nextPage;
    private int prevPage;

    /**
     * Returns list of posts for year.
     * 
     * @return Action String
     */
    public String execute() {

        // /year
        try {
            // jump to page if provided
            String pageTemp = servletRequest.getRequestURI();
            if (pageTemp.startsWith("/year/") && pageTemp.contains("/page/")) {
                year = Utils.removeBadChars(pageTemp.substring(6, pageTemp.indexOf("/page")));
                pageTemp = Utils.removeBadChars(pageTemp.substring(pageTemp.indexOf("/page/") + 6, pageTemp.length()));
                page = Integer.parseInt(pageTemp);

            } else if (pageTemp.startsWith("/year/")) {
                year = Utils.removeBadChars(pageTemp.substring(6, pageTemp.length()));
                page = 1;
            }

            int yr = Integer.parseInt(year);

            // gather posts
            posts = Application.getDatabaseService()
                    .getPostsByYear(page, Application.getInt("default.limit"), yr, false);

            // determine pagination
            if (posts != null) {
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
            }

            // set attributes
            servletRequest.setAttribute("posts", posts);
            servletRequest.setAttribute("page", page);
            servletRequest.setAttribute("nextPage", nextPage);
            servletRequest.setAttribute("prevPage", prevPage);

            return SUCCESS;

        } catch (NumberFormatException nfe) {
            return NONE;
        } catch (Exception e) {
            addActionError("Error: " + e.getClass()
                    .getName() + ". Please try again later.");
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
}
