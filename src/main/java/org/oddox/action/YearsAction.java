package org.oddox.action;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.oddox.config.Application;
import org.oddox.objects.Year;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Years action class
 * 
 * @author Austin Delamar
 * @date 4/20/2017
 */
public class YearsAction extends ActionSupport implements ServletResponseAware, ServletRequestAware {

    private static final long serialVersionUID = 1L;
    protected HttpServletResponse servletResponse;
    protected HttpServletRequest servletRequest;
    private List<Year> years = null;

    /**
     * Returns list of all years.
     * 
     * @return Action String
     */
    public String execute() {

        // /year
        try {
            // gather posts
            years = Application.getDatabaseService()
                    .getYears();

            // already sorted chronologically
            // so reverse so newest on top
            if (years != null && !years.isEmpty()) {
                Collections.reverse(years);
            } else {
                years = null;
                throw new NullPointerException("No years found");
            }

            // set attributes
            servletRequest.setAttribute("years", years);

            return SUCCESS;

        } catch (NullPointerException nfe) {
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

    public List<Year> getYears() {
        return years;
    }

    public void setYears(List<Year> years) {
        this.years = years;
    }
}
