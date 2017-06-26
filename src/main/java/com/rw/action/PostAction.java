package com.rw.action;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.rw.config.Application;
import com.rw.config.Utils;
import com.rw.model.Author;
import com.rw.model.Post;
import com.rw.model.AuthorAware;

/**
 * View Post action class
 * 
 * @author Austin Delamar
 * @date 11/9/2015
 */
public class PostAction extends ActionSupport
        implements
            AuthorAware,
            ServletResponseAware,
            ServletRequestAware {

    private static final long serialVersionUID = 1L;
    private Author user;
    private boolean canSeeHidden = false;

    // post parameters
    private Post post;
    private String uriName;

    public String execute() {

        // /blog/file-name-goes-here

        // this allows blog posts to be shown without parameter arguments (i.e.
        // ?uri_name=foobar&test=123 )
        String uriTemp = servletRequest.getRequestURI().toLowerCase();
        if (uriName == null && uriTemp.startsWith("/blog/post/")) {
            // /blog/post/post-name-goes-here
            uriName = Utils.removeBadChars(uriTemp.substring(11, uriTemp.length()));
        } else if (uriName == null && uriTemp.startsWith("/blog/")) {
            // /blog/post-name-goes-here
            uriName = Utils.removeBadChars(uriTemp.substring(6, uriTemp.length()));
        } else if (uriName == null && uriTemp.startsWith("/manage/viewpost/")) {
            // /manage/viewpost/post-name-goes-here
            uriName = Utils.removeBadChars(uriTemp.substring(17, uriTemp.length()));
            if (user != null) {
                canSeeHidden = true;
            }
        }

        if (uriName != null && uriName.length() > 0) {
            // search in db for post by title
            try {
                post = Application.getDatabaseSource().getPost(uriName, canSeeHidden);

                // was post found?
                if (post != null) {
                    // set attributes
                    servletRequest.setAttribute("post", post);                    

                    // check against previously viewed pages
                    boolean newViewFromSession = false;
                    if (servletRequest.getSession(false) != null) {
                        HttpSession session = servletRequest.getSession();
                        @SuppressWarnings("unchecked")
                        HashSet<String> viewedPages = (HashSet<String>) session
                                .getAttribute("viewedPages");

                        if (viewedPages == null) {
                            viewedPages = new HashSet<String>();
                        }

                        newViewFromSession = viewedPages.add(post.getUriName());
                        session.setAttribute("viewedPages", viewedPages);
                    }

                    // update page views
                    if(!canSeeHidden) {
                        Application.getDatabaseSource().incrementPageViews(post, newViewFromSession);
                    }

                    return Action.SUCCESS;
                } else {
                    System.err.println("Post '" + uriName + "' not found. Please try again.");
                    return Action.NONE;
                }

            } catch (Exception e) {
                addActionError("Error: " + e.getClass().getName() + ". Please try again later.");
                e.printStackTrace();
                return ERROR;
            }
        } else {
            System.err.println("Post '" + uriName + "' not found. Please try again.");
            return Action.NONE;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getUriName() {
        return uriName;
    }

    public void setUriName(String uriName) {
        this.uriName = uriName;
    }

    @Override
    public void setUser(Author user) {
        this.user = user;
    }
}