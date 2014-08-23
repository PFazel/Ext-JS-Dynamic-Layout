package com.extdl.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;

import java.util.Collection;
import java.util.Map;

/**
 * @author Parham Fazel
 */
public class RedirectAfterPostInterceptor implements Interceptor {
    public static final String ACTION_MESSAGES_SESSION_KEY = "__actionMessages";
    public static final String ACTION_ERRORS_SESSION_KEY = "__actionErrors";

    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {
        Map session = actionInvocation.getInvocationContext().getSession();

        Object actionMessages = session.remove(ACTION_MESSAGES_SESSION_KEY);
        if (actionMessages != null) {
            ActionSupport actionSupport = (ActionSupport) actionInvocation.getAction();
            actionSupport.setActionMessages((Collection) actionMessages);
        }

        Object actionErrors = session.remove(ACTION_ERRORS_SESSION_KEY);
        if (actionErrors != null) {
            ActionSupport actionSupport = (ActionSupport) actionInvocation.getAction();
            actionSupport.setActionErrors((Collection) actionErrors);
        }
        return actionInvocation.invoke();
    }
}
