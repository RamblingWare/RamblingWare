package org.rw.action;

import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.rw.action.model.Author;
import org.rw.config.Application;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

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

    private ArrayList<Author> authors = new ArrayList<Author>();

    public String execute() {

        // /author/

        // this shows all the authors
        try {
            authors = Application.getDatabaseSource().getAuthors(1, 50, true);

            // sort alphabetically
            Collections.sort(authors, new java.util.Comparator<Author>() {
                @Override
                public int compare(Author a1, Author a2) {
                    return a1.getName().compareToIgnoreCase(a2.getName());
                }
            });

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

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

}