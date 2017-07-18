package com.rant.action.interceptor;

import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.rant.config.Application;
import com.rant.model.Post;
/**
 * Archive Interceptor class
 * 
 * @author Austin Delamar
 * @date 11/30/2015
 */
public class ArchiveInterceptor implements Interceptor {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {

        Map<String, Object> sessionAttributes = actionInvocation.getInvocationContext()
                .getSession();

        List<Post> archiveFeatured = (List<Post>) sessionAttributes
                .get("archiveFeatured");
        List<String> archiveYears = (List<String>) sessionAttributes
                .get("archiveYears");
        List<String> archiveTags = (List<String>) sessionAttributes.get("archiveTags");
        List<String> archiveCategories = (List<String>) sessionAttributes
                .get("archiveCategories");

        if (archiveYears == null || archiveYears.isEmpty()) {
            // get the archive of posts by years and tag names
            archiveFeatured = Application.getDatabaseSource().getArchiveFeatured();
            archiveYears = Application.getDatabaseSource().getArchiveYears();
            archiveTags = Application.getDatabaseSource().getArchiveTags();
            archiveCategories = Application.getDatabaseSource().getArchiveCategories();

            // set attributes
            sessionAttributes.put("archiveFeatured", archiveFeatured);
            sessionAttributes.put("archiveYears", archiveYears);
            sessionAttributes.put("archiveTags", archiveTags);
            sessionAttributes.put("archiveCategories", archiveCategories);
        }

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
