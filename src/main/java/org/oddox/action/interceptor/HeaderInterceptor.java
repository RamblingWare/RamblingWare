package org.oddox.action.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * HeaderInterceptor class appends the HTTP Headers before receving a request. Remember the original
 * URI request, because if we forward, chain, redirect at all then this value is lost. Its
 * beneficial to keep for pagination and such.
 * 
 * @author Austin Delamar
 * @date 6/11/2017
 */
public class HeaderInterceptor implements Interceptor {

    private static final long serialVersionUID = 1L;

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {

        final ActionContext ac = actionInvocation.getInvocationContext();

        // Remember the original URI request, because if we forward, chain, redirect at all
        // then this value is lost. Its beneficial to keep for pagination and such.
        HttpServletRequest request = (HttpServletRequest) ac.get(StrutsStatics.HTTP_REQUEST);
        String pageUri = request.getRequestURI();

        // Valid URIs might have different contexts...
        // /blog/page/1
        // /year/2017/page/1
        // /category/test/page/1
        // /tag/java/page/1
        // /author/page/1

        if (pageUri.endsWith("/WEB-INF")) {
            pageUri = pageUri.replace("/WEB-INF", "");
        }
        if (pageUri.endsWith(".jsp")) {
            pageUri = pageUri.replace(".jsp", "");
        }

        if (pageUri.contains("/page/")) {
            String context = pageUri.substring(0, pageUri.indexOf("/page/"));
            pageUri = context + "/page";
        } else {
            pageUri = pageUri + "/page";
        }

        // remove any duplicate slashes
        while (pageUri.contains("//")) {
            pageUri = pageUri.replace("//", "/");
        }

        request.setAttribute("pageUri", pageUri);

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
