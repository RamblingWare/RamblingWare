package com.rant.action.filter;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * StaticContentFilter class modifies HTTP Headers before sending out a response from a static file.
 * 
 * @author Austin Delamar
 * @date 7/03/2017
 */
public class StaticContentFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        long ifModifiedSince = 0;
        try {
            ifModifiedSince = request.getDateHeader("If-Modified-Since");
        } catch (Exception e) {
            System.err.println("Invalid If-Modified-Since header value: '"
                    + request.getHeader("If-Modified-Since") + "', ignoring");
        }

        if (request.getRequestURI().endsWith(".ico")) {
            response.setContentType("image/x-icon");
            response.setHeader("Content-Type", "image/x-icon");
        }

        long now = System.currentTimeMillis();
        long lastModifiedMillis = now;

        // 1 month seems to be the minimum recommended period for static resources acc to
        // https://developers.google.com/speed/docs/best-practices/caching#LeverageBrowserCaching
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(now);
        c.add(Calendar.MONTH, 1);

        if (ifModifiedSince > 0 && ifModifiedSince <= lastModifiedMillis) {
            // not modified, content is not sent - only basic
            // headers and status SC_NOT_MODIFIED
            response.setDateHeader("Expires", c.getTimeInMillis());
            response.setHeader("X-Powered-By", "");
            response.setHeader("Server", "");
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return;
        }

        // set heading information for caching static content
        response.setDateHeader("Date", now);
        response.setDateHeader("Expires", c.getTimeInMillis());
        response.setDateHeader("Retry-After", c.getTimeInMillis());
        response.setHeader("Cache-Control", "max-age=2628000, public");
        response.setDateHeader("Last-Modified", lastModifiedMillis);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig fc) {
        // Auto-generated method stub
    }

    @Override
    public void destroy() {
        // Auto-generated method stub
    }

}
