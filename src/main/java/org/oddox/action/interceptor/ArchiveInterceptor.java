package org.oddox.action.interceptor;

import java.util.List;
import java.util.Map;

import org.oddox.config.Application;
import org.oddox.objects.Category;
import org.oddox.objects.Post;
import org.oddox.objects.Tag;
import org.oddox.objects.Year;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * Archive Interceptor class
 * 
 * @author Austin Delamar
 * @date 11/30/2015
 */
public class ArchiveInterceptor implements Interceptor {

    private static final long serialVersionUID = 1L;

    private static long cacheTime = 0l;
    private static int archiveTotal = 0;
    private static List<Post> archiveFeatured = null;
    private static List<Year> archiveYears = null;
    private static List<Tag> archiveTags = null;
    private static List<Category> archiveCategories = null;

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {

        // Has it been 24 hours since fresh archive check?
        long diff = Math.abs(System.currentTimeMillis() - cacheTime);
        if (diff >= 86400000) {
            // cache expired.
            // get fresh archive metadata

            // get the archive of posts by years and tag names
            archiveFeatured = Application.getDatabaseService()
                    .getFeatured();
            archiveYears = Application.getDatabaseService()
                    .getYears();
            archiveTags = Application.getDatabaseService()
                    .getTags();
            archiveCategories = Application.getDatabaseService()
                    .getCategories();

            // count total posts
            archiveTotal = 0;
            for (Year year : archiveYears) {
                archiveTotal += year.getCount();
            }

            // set new cacheTime
            cacheTime = System.currentTimeMillis();
        }
        // else,
        // just use cache archive metadata,
        // which at this point is all ready.

        // set to context
        Map<String, Object> map = actionInvocation.getInvocationContext()
                .getContextMap();
        map.put("archiveTotal", archiveTotal);
        map.put("archiveFeatured", archiveFeatured);
        map.put("archiveYears", archiveYears);
        map.put("archiveTags", archiveTags);
        map.put("archiveCategories", archiveCategories);

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

    public static long getCacheTime() {
        return cacheTime;
    }

    public static void setCacheTime(long cacheTime) {
        ArchiveInterceptor.cacheTime = cacheTime;
    }

    public static int getArchiveTotal() {
        return archiveTotal;
    }

    public static void setArchiveTotal(int archiveTotal) {
        ArchiveInterceptor.archiveTotal = archiveTotal;
    }

    public static List<Post> getArchiveFeatured() {
        return archiveFeatured;
    }

    public static void setArchiveFeatured(List<Post> archiveFeatured) {
        ArchiveInterceptor.archiveFeatured = archiveFeatured;
    }

    public static List<Year> getArchiveYears() {
        return archiveYears;
    }

    public static void setArchiveYears(List<Year> archiveYears) {
        ArchiveInterceptor.archiveYears = archiveYears;
    }

    public static List<Tag> getArchiveTags() {
        return archiveTags;
    }

    public static void setArchiveTags(List<Tag> archiveTags) {
        ArchiveInterceptor.archiveTags = archiveTags;
    }

    public static List<Category> getArchiveCategories() {
        return archiveCategories;
    }

    public static void setArchiveCategories(List<Category> archiveCategories) {
        ArchiveInterceptor.archiveCategories = archiveCategories;
    }
}
