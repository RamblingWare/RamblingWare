package org.oddox.action;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.oddox.config.Application;
import org.oddox.objects.Category;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Categories action class
 * 
 * @author Austin Delamar
 * @date 4/30/2017
 */
public class CategoriesAction extends ActionSupport implements ServletResponseAware, ServletRequestAware {

    private static final long serialVersionUID = 1L;

    private List<Category> categories = null;

    /**
     * Returns list of categories.
     * 
     * @return Action String
     */
    public String execute() {

        // /category

        // this shows all the categories of blog posts
        try {
            // gather posts
            categories = Application.getDatabaseService()
                    .getCategories();

            // sort alphabetically
            if (categories != null) {
                Collections.sort(categories);
            }

            // set attributes
            servletRequest.setAttribute("categories", categories);

            return SUCCESS;

        } catch (Exception e) {
            addActionError("Error: " + e.getClass()
                    .getName() + ". Please try again later.");
            e.printStackTrace();
            return ERROR;
        }
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
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
