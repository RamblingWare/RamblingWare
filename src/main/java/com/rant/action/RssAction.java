package com.rant.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.rant.config.Application;
import com.rant.config.Utils;
import com.rant.objects.Post;

/**
 * View RSS action class
 * 
 * @author Austin Delamar
 * @date 12/9/2015
 */
public class RssAction extends ActionSupport implements ServletResponseAware, ServletRequestAware {

    private static final long serialVersionUID = 1L;

    // search results
    private List<Post> posts = null;

    public String execute() {

        // /rss
        System.out.println("RSS Feed Requested.");

        // this page shows the RSS feed
        String response = "<?xml version=\"1.0\"?>" + "<rss version=\"2.0\">\n" + "<channel>\n"
                + "<title>RamblingWare Blog</title>\n"
                + "<description>This is my blog about computers, programming, tech, and things that bother me. I hope it bothers you too.</description>\n"
                + "<link>https://www.ramblingware.com</link>" + "<image>\n"
                + "<url>/img/logo-medium.png</url>" + "<title>RamblingWare</title>\n"
                + "<link>https://www.ramblingware.com/img/logo-medium.png</link>" + "</image>\n"
                + "<language>en-us</language>\n"
                + "<webMaster>webmaster@ramblingware.com</webMaster>\n" + "<ttl>1440</ttl>\n"
                + "<skipDays><day>Saturday</day><day>Sunday</day></skipDays>\n"
                + "<skipHours><hour>0</hour><hour>1</hour><hour>2</hour><hour>3</hour><hour>4</hour><hour>5</hour><hour>6</hour><hour>7</hour>"
                + "<hour>17</hour><hour>18</hour><hour>19</hour><hour>20</hour><hour>21</hour><hour>22</hour><hour>23</hour></skipHours>\n"
                + "<copyright>RamblingWare 2017.</copyright>\n";
        try {

            // gather posts
            posts = Application.getDatabaseService().getPosts(1, Application.getInt("limit"), false);
            for (Post post : posts) {
                response += "<item><title>" + post.getTitle() + "</title>\n" + "<description>"
                        + post.getDescription() + "</description>\n" + "<pubDate>"
                        + post.getPublishDateReadable() + "</pubDate>\n"

                        + "<link>https://www.ramblingware.com/blog/" + post.getUri() + "</link>"
                        + "</item>\n";
            }

            // add publish date from latest blog post
            if (posts != null && !posts.isEmpty()) {
                response += "<pubDate>" + posts.get(0).getPublishDateReadable() + "</pubDate>\n";
            }

            response += "<lastBuildDate>" + Utils.getDate() + "</lastBuildDate>\n";
            response += "</channel>\n</rss>";

            try {
                // return message to user
                PrintWriter out = ServletActionContext.getResponse().getWriter();
                ServletActionContext.getResponse().setContentType("text/xml");
                out.write(response);
            } catch (Exception e) {
                e.printStackTrace();
                addActionError("Error: " + e.getClass().getName() + ". " + e.getMessage());
            }
            // no action return
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
}