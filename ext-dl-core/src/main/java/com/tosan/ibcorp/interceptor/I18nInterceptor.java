package com.extdl.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.extdl.tag.TagHelper;

import java.util.Locale;

/**
 * @author Parham Fazel
 * @since Jun 22, 2008
 */
public class I18nInterceptor implements Interceptor {
    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {
        ActionContext context = actionInvocation.getInvocationContext();
        Locale locale = TagHelper.getLocale(context.getParameters(), context.getSession(), context.getApplication());
        actionInvocation.getInvocationContext().setLocale(locale);
        return actionInvocation.invoke();
    }
}
