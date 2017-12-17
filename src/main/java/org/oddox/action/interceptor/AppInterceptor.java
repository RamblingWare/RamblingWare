package org.oddox.action.interceptor;

import java.util.Map;

import org.oddox.config.Application;
import org.oddox.config.Utils;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * Application Interceptor class
 * 
 * @author Austin Delamar
 * @date 12/17/2017
 */
public class AppInterceptor implements Interceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		Map<String, Object> map = actionInvocation.getInvocationContext().getApplication();

		// set app properties
		map.put("date", Utils.getDateRfc1123());
		map.putAll(Application.getAppConfig().getSettings());

		actionInvocation.getInvocationContext().setApplication(map);

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
