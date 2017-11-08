package com.rant.action;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.rant.config.Application;
import com.rant.objects.Author;

/**
 * Authors action class
 * 
 * @author Austin Delamar
 * @date 4/23/2017
 */
public class AuthorsAction extends ActionSupport
        implements
            ServletResponseAware,
            ServletRequestAware {

    private static final long serialVersionUID = 1L;

    private List<Author> authors = null;

    /**
     * Returns list of authors.
     * 
     * @return Action String
     */
    public String execute() {

        // /author/
        try {
            authors = Application.getDatabaseService().getAuthors(1,
                    Application.getInt("default.limit"), false);

            // sort alphabetically
            if (authors != null) {
                Collections.sort(authors, new java.util.Comparator<Author>() {
                    @Override
                    public int compare(Author a1, Author a2) {
                        return a1.getName().compareToIgnoreCase(a2.getName());
                    }
                });
            }

            // set attributes
            servletRequest.setAttribute("authors", authors);

            return Action.SUCCESS;

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

}