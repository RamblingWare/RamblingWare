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

	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {

		Map<String, Object> sessionAttributes = actionInvocation.getInvocationContext().getSession();

		List<Post> archiveFeatured = (List<Post>) sessionAttributes.get("archiveFeatured");
		List<Year> archiveYears = (List<Year>) sessionAttributes.get("archiveYears");
		List<Tag> archiveTags = (List<Tag>) sessionAttributes.get("archiveTags");
		List<Category> archiveCategories = (List<Category>) sessionAttributes.get("archiveCategories");

		if (archiveYears == null || archiveYears.isEmpty()) {
			// get the archive of posts by years and tag names
			archiveFeatured = Application.getDatabaseService().getFeatured();
			archiveYears = Application.getDatabaseService().getYears();
			archiveTags = Application.getDatabaseService().getTags();
			archiveCategories = Application.getDatabaseService().getCategories();

			// set attributes
			sessionAttributes.put("archiveFeatured", archiveFeatured);
			sessionAttributes.put("archiveYears", archiveYears);
			sessionAttributes.put("archiveTags", archiveTags);
			sessionAttributes.put("archiveCategories", archiveCategories);

			// set to context
			Map<String, Object> map = actionInvocation.getInvocationContext().getContextMap();
			map.put("archiveFeatured", archiveFeatured);
			map.put("archiveYears", archiveYears);
			map.put("archiveTags", archiveTags);
			map.put("archiveCategories", archiveCategories);
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
