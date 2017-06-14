package com.rw.action.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.rw.model.Author;
import com.rw.model.AuthorAware;

/**
 * Authentication Interceptor class
 * 
 * @author Austin Delamar
 * @date 11/9/2015
 */
public class AuthenticationInterceptor implements Interceptor {

    private static final long serialVersionUID = 1L;

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {

        Map<String, Object> sessionAttributes = actionInvocation.getInvocationContext()
                .getSession();

        Author user = (Author) sessionAttributes.get("USER");

        if (user == null) {
            System.out.println("Unknown user was redirected to login page.");
            return Action.LOGIN;
        } else if (user.isOTPEnabled() && !user.isOTPAuthenticated()) {
            System.out.println("User " + user.getUsername() + " was redirected to OTP page.");
            return Action.INPUT;
        } else {
            Action action = (Action) actionInvocation.getAction();
            if (action instanceof AuthorAware) {
                ((AuthorAware) action).setUser(user);
            }
            return actionInvocation.invoke();
        }
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
