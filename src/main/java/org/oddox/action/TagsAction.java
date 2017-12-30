package org.oddox.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.oddox.objects.Tag;

import com.cloudant.client.org.lightcouch.NoDocumentException;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Tags action class
 * 
 * @author Austin Delamar
 * @date 4/20/2017
 */
public class TagsAction extends ActionSupport implements ServletResponseAware, ServletRequestAware {

    private static final long serialVersionUID = 1L;
    protected HttpServletResponse servletResponse;
    protected HttpServletRequest servletRequest;
    private List<Tag> tags = null;

    /**
     * Returns list of tags.
     * 
     * @return Action String
     */
    public String execute() {

        // /tag
        try {
            // gather tags
            @SuppressWarnings("unchecked")
            List<Tag> archiveTags = (List<Tag>) servletRequest.getSession()
                    .getAttribute("archiveTags");
            tags = archiveTags;

            // sort alphabetically
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

    @Override
    public void setServletResponse(HttpServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }

    @Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
