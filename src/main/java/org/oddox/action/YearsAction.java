package org.oddox.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.oddox.objects.Year;

import com.cloudant.client.org.lightcouch.NoDocumentException;
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
            // gather years
            @SuppressWarnings("unchecked")
            List<Year> archiveYears = (List<Year>) servletRequest.getSession()
                    .getAttribute("archiveYears");
            years = archiveYears;

            if (years == null || years.isEmpty()) {
                years = null;
                throw new NoDocumentException("No years found");
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

    public List<Year> getYears() {
        return years;
    }

    public void setYears(List<Year> years) {
        this.years = years;
    }
}
