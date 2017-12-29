package org.oddox.action;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.oddox.config.Application;
import org.oddox.objects.Tag;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Tags action class
 * 
 * @author Austin Delamar
 * @date 4/20/2017
 */
public class TagsAction extends ActionSupport implements ServletResponseAware, ServletRequestAware {

    private static final long serialVersionUID = 1L;

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
            tags = Application.getDatabaseService()
                    .getTags();

            // sort alphabetically
            if (tags != null) {
                Collections.sort(tags);
            }

            // set attributes
            servletRequest.setAttribute("tags", tags);

            return SUCCESS;

        } catch (Exception e) {
            addActionError("Error: " + e.getClass()
                    .getName() + ". Please try again later.");
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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
