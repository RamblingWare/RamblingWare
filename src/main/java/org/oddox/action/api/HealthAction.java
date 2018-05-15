package org.oddox.action.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.oddox.config.Application;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Health action class
 * 
 * @author Austin Delamar
 * @date 5/14/2018
 */
public class HealthAction extends ActionSupport implements ServletResponseAware, ServletRequestAware {

    private static final long serialVersionUID = 1L;
    protected HttpServletResponse servletResponse;
    protected HttpServletRequest servletRequest;

    // JSON response
    private String error;
    private String message;
    private Map<String, String> status;

    /**
     * Returns app status information.
     * 
     * @return Action String
     */
    public String execute() {
        try {
            status = new HashMap<String, String>();
            status.put("app", Application.getString("name")!=null?"ok":"bad");
            status.put("db", Application.getDatabaseService().getInfo()!=null?"ok":"bad");
        } catch (Exception e) {
            error = "error";
            message = e.getMessage();
        }

        // return response
        return NONE;
    }

    @Override
    public void setServletResponse(HttpServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }

    @Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getStatus() {
        return status;
    }

    public void setStatus(Map<String, String> status) {
        this.status = status;
    }

}
