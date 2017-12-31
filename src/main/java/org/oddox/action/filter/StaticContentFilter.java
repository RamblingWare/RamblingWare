package org.oddox.action.filter;

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
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // Return a 304 "Not Modified" if the file hasn't been updated.
        long ifModifiedSince = 0;
        try {
            // only do this if browser asks "If-Modified-Since"
            ifModifiedSince = request.getDateHeader("If-Modified-Since");
        } catch (Exception e) {
            System.err.println("Invalid If-Modified-Since header value: '" + request.getHeader("If-Modified-Since")
                    + "', ignoring");
        }

        if (request.getRequestURI()
                .endsWith(".ico")) {
            // favicon content-type
            response.setContentType("image/x-icon");
            response.setHeader("Content-Type", "image/x-icon");
        }

        long now = System.currentTimeMillis();
        long lastModifiedMillis = now;

        // Replace information that might reveal too much to help potential attackers to exploit the
        // server. Alternatively, you could put bogus info here, like .NET or other irrelevant tech.
        //response.setHeader("X-Powered-By", "");
        //response.setHeader("Server", "");

        // 1 month seems to be the minimum recommended period for static resources acc to
        // https://developers.google.com/speed/docs/best-practices/caching#LeverageBrowserCaching
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(now);
        cal.add(Calendar.MONTH, 1);

        if (ifModifiedSince > 0 && ifModifiedSince <= lastModifiedMillis) {
            // 304 "Not Modified" content is not sent
            response.setDateHeader("Expires", cal.getTimeInMillis());
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return;
        }

        // set heading information for caching static content
        response.setDateHeader("Date", now);
        response.setDateHeader("Expires", cal.getTimeInMillis());
        response.setDateHeader("Retry-After", cal.getTimeInMillis());
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
