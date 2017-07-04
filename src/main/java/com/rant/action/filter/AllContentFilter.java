package com.rant.action.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.rant.config.Application;

/**
 * AllContentFilter class modifies HTTP Headers before sending out a response from a file or action.
 * 
 * @author Austin Delamar
 * @date 7/03/2017
 */
public class AllContentFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // HttpServletRequest request = (HttpServletRequest) servletRequest;

        // Set UTF-8 encoding
        response.setCharacterEncoding("UTF-8");

        // Tell a browser that you always want a user to connect using HTTPS instead of HTTP for 1
        // year
        response.addHeader("Strict-Transport-Security", "max-age=15552000; includeSubDomains");

        // The browser will send the full URL to requests to the same origin but only the origin
        // when requests are cross-origin.
        response.addHeader("Referrer-Policy", "origin-when-cross-origin");

        // Set CSP to ensure content and sources are explicit.
        response.addHeader("Content-Security-Policy", "default-src 'self' "
                + Application.getSetting("cdn") + " 'unsafe-inline' 'unsafe-eval'");
        // "Content-Security-Policy-Report-Only" can also be used.

        // "default-src 'none'; img-src 'self' cdn.ramblingware.com chart.googleapis.com; style-src
        // 'self'
        // 'unsafe-inline'; script-src 'self' 'nonce-123456789'; form-action 'self'; font-src
        // 'self'

        // Enforce TLS on all assets like JS and CSS and prevent mixed secure content warnings.
        // 'unsafe-inline' is used for inline CSS
        // 'unsafe-eval' is used for inline JS and CKEditor
        // In a perfect scenario, we would remove these two attributes and fix any inline CSS/JS and
        // unsafe javascript code like CKEditor. But unfortunately these are critical to the
        // function of this blog.
        // @see https://scotthelme.co.uk/content-security-policy-an-introduction/

        // Public-Key-Pins
        // Public-Key-Pins-Report-Only
        // @see https://scotthelme.co.uk/hpkp-http-public-key-pinning/

        // The HPKP header specifies fingerprints for the SSL certificate to trust, and if a SSL
        // cert issued does not match the fingerprint, then the user's browser will fail to connect.
        // Currently, this requires a bit of work and access to the certificate files which I do not
        // have over CloudFlare. Other configurations might be possible.

        // Allow or deny <iframe> from your site or other sites
        response.addHeader("X-Frame-Options", "SAMEORIGIN");

        // Enable reflective XSS protection by blocking attacks rather than sanitizing scripts.
        response.addHeader("X-Xss-Protection", "1; mode=block");

        // Prevents browsers from trying to mime-sniff the content-type of a response away from the
        // one being declared by the server.
        response.addHeader("X-Content-Type-Options", "nosniff");

        // Replace information that might reveal too much to help potential attackers to exploit the
        // server.
        response.setHeader("X-Powered-By", "");
        response.setHeader("Server", "");

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
