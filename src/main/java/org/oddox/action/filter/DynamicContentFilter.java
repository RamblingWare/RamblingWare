package org.oddox.action.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.oddox.config.AppHeaders;
import org.oddox.config.Application;
import org.oddox.config.Header;

/**
 * DynamicContentFilter class modifies HTTP Headers before sending out a response from a template or action.
 * 
 * @author Austin Delamar
 * @date 7/03/2017
 */
public class DynamicContentFilter implements Filter {

    public static final long EXPIRETIME = 86400000l;
    public static long cacheTime = 0l;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // HttpServletRequest request = (HttpServletRequest) servletRequest;

        // Set UTF-8 encoding
        response.setCharacterEncoding("UTF-8");

        // Has it been 24 hours since fresh archive check?
        long diff = Math.abs(System.currentTimeMillis() - cacheTime);
        if (diff >= EXPIRETIME) {
            // cache expired.
            // get fresh app headers

            // get the http headers from db
            AppHeaders appHeaders = Application.getDatabaseService()
                    .getAppHeaders();
            Application.setAppHeaders(appHeaders);

            // set new cacheTime
            cacheTime = System.currentTimeMillis();
        }
        // else,
        // just use cache http headers,
        // which at this point is already set.

        // apply all headers to our response
        if (Application.getAppHeaders() != null && Application.getAppHeaders()
                .getHeaders() != null) {
            for (Header header : Application.getAppHeaders()
                    .getHeaders()) {
                response.addHeader(header.getKey(), header.getValue());
            }
        }

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
