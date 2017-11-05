package com.rant.action;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.rant.config.Application;
import com.rant.config.Utils;
import com.rant.objects.Post;

/**
 * View Post action class
 * 
 * @author Austin Delamar
 * @date 11/9/2015
 */
public class PostAction extends ActionSupport implements ServletResponseAware, ServletRequestAware {

    private static final long serialVersionUID = 1L;

    // post parameters
    private Post post;
    private String uri;

    public String execute() {

        // /blog/file-name-goes-here

        // this allows blog posts to be shown without parameter arguments (i.e.
        // ?uri_name=foobar&test=123 )
        String uriTemp = servletRequest.getRequestURI().toLowerCase();
        if (uri == null && uriTemp.startsWith("/blog/post/")) {
            // /blog/post/post-name-goes-here
            uri = Utils.removeBadChars(uriTemp.substring(11, uriTemp.length()));
        } else if (uri == null && uriTemp.startsWith("/blog/")) {
            // /blog/post-name-goes-here
            uri = Utils.removeBadChars(uriTemp.substring(6, uriTemp.length()));
        }

        if (uri != null && uri.length() > 0) {
            // search in db for post by title
            try {
                post = Application.getDatabaseService().getPost(uri, false);

                // was post found?
                if (post != null) {
                    // set attributes
                    servletRequest.setAttribute("post", post);

                    // check against previously viewed posts
                    boolean newViewFromSession = false;
                    if (servletRequest.getSession(false) != null) {
                        HttpSession session = servletRequest.getSession();
                        @SuppressWarnings("unchecked")
                        HashSet<String> viewedPages = (HashSet<String>) session
                                .getAttribute("viewedPages");

                        if (viewedPages == null) {
                            viewedPages = new HashSet<String>();
                        }

                        newViewFromSession = viewedPages.add(post.getUri());
                        session.setAttribute("viewedPages", viewedPages);
                    }

                    // update page views
                    post.getView().setCount(post.getView().getCount() + 1);
                    if (newViewFromSession) {
                        post.getView().setSession(post.getView().getSession() + 1);
                    }
                    Application.getDatabaseService().editView(post.getView());

                    return Action.SUCCESS;
                } else {
                    System.err.println("Post '" + uri + "' not found. Please try again.");
                    return Action.NONE;
                }

            } catch (Exception e) {
                addActionError("Error: " + e.getClass().getName() + ". Please try again later.");
                e.printStackTrace();
                return ERROR;
            }
        } else {
            System.err.println("Post '" + uri + "' not found. Please try again.");
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
        return uri;
    }

    public void setUriName(String uriName) {
        this.uri = uriName;
    }
}