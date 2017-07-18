package com.rant.action.manage.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.rant.config.Application;
import com.rant.config.Utils;
import com.rant.model.Author;

/**
 * View/Edit Users action class
 * 
 * @author Austin Delamar
 * @date 10/23/2016
 */
public class UsersAction extends ActionSupport
        implements
            ServletResponseAware,
            ServletRequestAware {

    private static final long serialVersionUID = 1L;

    // results
    private List<Author> authors;
    private int page;
    private int limit = Application.getInt("manageLimit");
    private boolean nextPage;
    private boolean prevPage;

    public String execute() {

        // /manage/users

        // this shows the authors
        try {
            // jump to page if provided
            String pageTemp = servletRequest.getRequestURI().toLowerCase();
            if (pageTemp.startsWith("/manage/users/page/")) {
                pageTemp = Utils.removeBadChars(pageTemp.substring(19, pageTemp.length()));
                page = Integer.parseInt(pageTemp);
            } else {
                page = 1;
            }

            // gather authors
            authors = Application.getDatabaseSource().getAuthors(page, limit, true);

            // determine pagination
            if (authors != null) {
                nextPage = authors.size() >= limit;
                prevPage = page > 1;
            }

            // set attributes
            servletRequest.setAttribute("authors", authors);
            servletRequest.setAttribute("page", page);
            servletRequest.setAttribute("nextPage", nextPage);
            servletRequest.setAttribute("prevPage", prevPage);

            return SUCCESS;

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

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
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