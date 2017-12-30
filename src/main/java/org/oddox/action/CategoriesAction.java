package org.oddox.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.oddox.objects.Category;

import com.cloudant.client.org.lightcouch.NoDocumentException;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Categories action class
 * 
 * @author Austin Delamar
 * @date 4/30/2017
 */
public class CategoriesAction extends ActionSupport implements ServletResponseAware, ServletRequestAware {

    private static final long serialVersionUID = 1L;
    protected HttpServletResponse servletResponse;
    protected HttpServletRequest servletRequest;
    private List<Category> categories = null;

    /**
     * Returns list of categories.
     * 
     * @return Action String
     */
    public String execute() {

        // /category
        try {
            // gather categories
            @SuppressWarnings("unchecked")
            List<Category> archiveCategories = (List<Category>) servletRequest.getSession()
                    .getAttribute("archiveCategories");
            categories = archiveCategories;

            // sort alphabetically
            if (categories == null || categories.isEmpty()) {
                categories = null;
                throw new NoDocumentException("No categories found");
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
