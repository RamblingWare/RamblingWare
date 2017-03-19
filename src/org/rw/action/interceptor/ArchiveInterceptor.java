package org.rw.action.interceptor;

import java.util.ArrayList;
import java.util.Map;

import org.rw.bean.Post;
import org.rw.model.ApplicationStore;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
/**
 * Archive Interceptor class
 * @author Austin Delamar
 * @date 11/30/2015
 */
public class ArchiveInterceptor implements Interceptor {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {

		Map<String, Object> sessionAttributes = actionInvocation.getInvocationContext().getSession();

		ArrayList<Post> archive_featured = (ArrayList<Post>) sessionAttributes.get("archive_featured");
		ArrayList<String> archive_years = (ArrayList<String>) sessionAttributes.get("archive_years");
		ArrayList<String> archive_tags = (ArrayList<String>) sessionAttributes.get("archive_tags");
		
		if (archive_years == null || archive_years.isEmpty()) 
		{			
			// get the archive of posts by years and tag names
			archive_featured = ApplicationStore.getDatabaseSource().getArchiveFeatured();
			archive_years = ApplicationStore.getDatabaseSource().getArchiveYears();
			archive_tags = ApplicationStore.getDatabaseSource().getArchiveTags();
			
			// set attributes
			sessionAttributes.put("archive_featured", archive_featured);
			sessionAttributes.put("archive_years", archive_years);
			sessionAttributes.put("archive_tags", archive_tags);
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
