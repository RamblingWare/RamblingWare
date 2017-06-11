package org.rw.action.interceptor;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * HeaderInterceptor class appends and cleans the HTTP Headers before sending out a response.
 * 
 * @author Austin Delamar
 * @date 6/11/2017
 */
public class HeaderInterceptor implements Interceptor {

    private static final long serialVersionUID = 1L;

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {

        final ActionContext ac = actionInvocation.getInvocationContext();
        HttpServletResponse response = (HttpServletResponse) ac.get(StrutsStatics.HTTP_RESPONSE);

        response.setCharacterEncoding("UTF-8");

        // Tell a browser that you always want a user to connect using HTTPS instead of HTTP for 1
        // year
        response.addHeader("Strict-Transport-Security", "max-age=15552000; includeSubDomains");

        // The browser will send the full URL to requests to the same origin but only the origin
        // when requests are cross-origin.
        response.addHeader("Referrer-Policy", "origin-when-cross-origin");

        // Enforce TLS on all assets like JS and CSS and prevent mixed secure content warnings.
        response.addHeader("Content-Security-Policy", "default-src 'self' cdn.ramblingware.com");

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

        return actionInvocation.invoke();
    }

    @Override
    public void destroy() {
        // Auto-generated method stub

    }

    @Override
    public void init() {
        // Auto-generated method stub

    }

}
