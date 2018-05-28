package org.oddox.action;

import java.io.PrintWriter;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.oddox.config.Application;
import org.oddox.config.Utils;
import org.oddox.objects.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;

/**
 * View RSS action class
 * 
 * @author Austin Delamar
 * @date 12/9/2015
 */
public class RssAction implements Handler<RoutingContext> {

    private static Logger logger = LoggerFactory.getLogger(RssAction.class);
    private static long cacheTime = 0l;
    private static List<Post> posts = null;

    /**
     * Returns RSS information.
     */
    @Override
    public void handle(RoutingContext context) {

        // /rss

        // this page shows the XML RSS feed
        String response = "<?xml version=\"1.0\"?>" + "<rss version=\"2.0\">\n" + "<channel>\n" + "<title>"
                + Application.getString("name") + "</title>\n" + "<description>" + Application.getString("description")
                + "</description>\n" + "<link>" + Application.getString("url") + "</link>" + "<webMaster>"
                + Application.getString("email") + "</webMaster>\n" + "<ttl>1440</ttl>\n"
                + "<skipDays><day>Saturday</day><day>Sunday</day></skipDays>\n"
                + "<skipHours><hour>0</hour><hour>1</hour><hour>2</hour><hour>3</hour><hour>4</hour><hour>5</hour><hour>6</hour><hour>7</hour>"
                + "<hour>17</hour><hour>18</hour><hour>19</hour><hour>20</hour><hour>21</hour><hour>22</hour><hour>23</hour></skipHours>\n";
        try {
            // Has it been 24 hours since fresh RSS check?
            long diff = Math.abs(System.currentTimeMillis() - cacheTime);
            if (diff >= 86400000) {
                // cache expired.
                // get fresh RSS data
                posts = Application.getDatabaseService()
                        .getPosts(1, Application.getInt("rssFeedLimit"), false);

                // set new cacheTime
                cacheTime = System.currentTimeMillis();
            }

            for (Post post : posts) {
                response += "<item><title>" + post.getTitle() + "</title>\n" + "<description>" + post.getDescription()
                        + "</description>\n" + "<pubDate>" + post.getPublishDateReadable() + "</pubDate>\n"

                        + "<link>" + Application.getString("url") + "/blog/" + post.getUri() + "</link>" + "</item>\n";
            }

            // add publish date from latest blog post
            if (posts != null && !posts.isEmpty()) {
                response += "<pubDate>" + posts.get(0)
                        .getPublishDateReadable() + "</pubDate>\n";
            }

            response += "<lastBuildDate>" + Utils.getDate() + "</lastBuildDate>\n";
            response += "</channel>\n</rss>";

            try {
                // return message to user
                PrintWriter out = ServletActionContext.getResponse()
                        .getWriter();
                ServletActionContext.getResponse()
                        .setContentType("text/xml");
                out.write(response);
            } catch (Exception e) {
                System.out.println("ERROR: Failed to build RSS feed. " + e.getMessage());
                addActionError("Error: " + e.getClass()
                        .getName() + ". " + e.getMessage());
            }
            // no action return
            return null;

        } catch (Exception e) {
            System.out.println("ERROR: Failed to build RSS feed. " + e.getMessage());
            addActionError("Error: " + e.getClass()
                    .getName() + ". Please try again later.");
            return ERROR;
        }
    }

    public static long getCacheTime() {
        return cacheTime;
    }

    public static void setCacheTime(long cacheTime) {
        RssAction.cacheTime = cacheTime;
    }
}
