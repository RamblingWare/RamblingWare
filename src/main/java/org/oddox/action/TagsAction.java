package org.oddox.action;

import java.util.List;

import org.oddox.action.interceptor.ArchiveInterceptor;
import org.oddox.objects.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudant.client.org.lightcouch.NoDocumentException;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;

/**
 * Tags action class
 * 
 * @author Austin Delamar
 * @date 4/20/2017
 */
public class TagsAction implements Handler<RoutingContext> {

    private static Logger logger = LoggerFactory.getLogger(TagsAction.class);
    private List<Tag> tags = null;

    /**
     * Returns list of tags.
     */
    @Override
    public void handle(RoutingContext context) {

        // /tag
        try {
            // gather tags
            tags = ArchiveInterceptor.getArchiveTags();

            if (tags == null || tags.isEmpty()) {
                tags = null;
                throw new NoDocumentException("No tags found");
            }
            return SUCCESS;

        } catch (NoDocumentException nfe) {
            return NONE;
        } catch (Exception e) {
            addActionError("Error: " + e.getClass()
                    .getName() + ". Please try again later.");
            e.printStackTrace();
            return ERROR;
        }
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
