package org.oddox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.oddox.config.Application;
import org.oddox.config.Utils;
import org.oddox.objects.Author;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Author action class
 * 
 * @author Austin Delamar
 * @date 10/23/2016
 */
public class AuthorAction extends ActionSupport implements ServletResponseAware, ServletRequestAware {

    private static final long serialVersionUID = 1L;
    protected HttpServletResponse servletResponse;
    protected HttpServletRequest servletRequest;
    private Author author;
    private String uriName;

    /**
     * Returns author details.
     * 
     * @return Action String
     */
    public String execute() {

        // /author/person-name-goes-here
        String uri = servletRequest.getRequestURI();
        if (uriName == null && uri.startsWith("/author/")) {
            uriName = Utils.removeBadChars(uri.substring(8, uri.length()));
        }

        if (uriName != null && uriName.length() > 0) {
            // search in db for author
            try {
                author = Application.getDatabaseService()
                        .getAuthor(uriName, false);

                if (author != null) {
                    // set attributes
                    servletRequest.setAttribute("author", author);

                    return Action.SUCCESS;
                } else {
                    System.err.println("Author '" + uriName + "' not found. Please try again.");
                    return Action.NONE;
                }

            } catch (Exception e) {
                addActionError("Error: " + e.getClass()
                        .getName() + ". Please try again later.");
                e.printStackTrace();
                return ERROR;
            }
        } else {
            System.err.println("Author '" + uriName + "' not found. Please try again.");
            return Action.NONE;
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

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Author getAuthor() {
        return author;
    }
}
