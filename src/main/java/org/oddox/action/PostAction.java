package org.oddox.action;

import org.oddox.OddoxVerticle;
import org.oddox.config.Application;
import org.oddox.config.Utils;
import org.oddox.objects.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.templ.FreeMarkerTemplateEngine;
import io.vertx.reactivex.ext.web.templ.TemplateEngine;

/**
 * View Post action class
 * 
 * @author amdelamar
 * @date 11/9/2015
 */
public class PostAction implements Handler<RoutingContext> {

    private final static Logger logger = LoggerFactory.getLogger(PostAction.class);
    private final static TemplateEngine ENGINE = FreeMarkerTemplateEngine.create();
    private Post post;

    /**
     * Returns blog post details.
     */
    @Override
    public void handle(RoutingContext context) {
        
        // Don't handle if response ended
        if(context.response().ended()) {
            context.next();
            return;
        }

        // /blog/post-name
        String templateFile = "blog/post.ftl";
        String uri = context.request().getParam("post");

        if (uri != null && !uri.isEmpty()) {
            // lower-case no matter what
            uri = uri.toLowerCase();
            uri = Utils.removeBadChars(uri);

            // search in db for post by uri
            try {
                post = Application.getDatabaseService()
                        .getPost(uri, false);

                // was post found?
                if (post != null) {

                    // check against previously viewed posts
                    /*
                    boolean newViewFromSession = false;
                    if (servletRequest.getSession(false) != null) {
                        Session session = (Session) context.session();
                        HashSet<String> viewedPages = (HashSet<String>) session.getAttribute("viewedPages");
                    
                        if (viewedPages == null) {
                            viewedPages = new HashSet<String>();
                        }
                    
                        newViewFromSession = viewedPages.add(post.getUri());
                        session.setAttribute("viewedPages", viewedPages);
                    } */

                    // update page views
                    post.getView()
                            .setCount(post.getView()
                                    .getCount() + 1);
                    post.getView()
                            .setSession(post.getView()
                                    .getSession() + 1);

                    Application.getDatabaseService()
                            .editView(post.getView());
                } else {
                    logger.error("Post '" + uri + "' not found. Please try again.");
                    templateFile = "blog/post.ftl";
                }

            } catch (Exception e) {
                logger.error("Error: " + e.getClass()
                        .getName() + ". Please try again later.", e);
                templateFile = "/error/error.ftl";
            }
        } else {
            logger.error("Post '" + uri + "' not found. Please try again.");
            templateFile = "blog/post.ftl";
        }
        
        // Bind Context
        context.put("post", post);

        // Render template response
        ENGINE.render(context, OddoxVerticle.TEMPLATES_DIR, templateFile, res -> {
            context.response().putHeader("content-type", "text/html;charset=UTF-8");
            if (res.succeeded()) {
                context.response()
                        .end(res.result());
            } else {
                context.fail(res.cause());
            }
        });
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
