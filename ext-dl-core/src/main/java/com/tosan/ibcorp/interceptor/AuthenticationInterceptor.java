package com.extdl.interceptor;

import com.kishware.ebank.channelmanager.bank.context.Context;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.extdl.action.BaseAction;

/**
 * @author Parham Fazel
 */
public class AuthenticationInterceptor implements Interceptor {
    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {
        if (!(actionInvocation.getAction() instanceof BaseAction)) {
            return Action.LOGIN;
        }
        BaseAction baseAction = (BaseAction) actionInvocation.getAction();
        if (baseAction.getContext() == null) {
            return Action.LOGIN;
        }

        Context context = baseAction.getContext();
        String cmSessionId = context.getData(Context.SESSIONID);
        if (cmSessionId == null || cmSessionId.trim().length() == 0) {
            String requestUrl = baseAction.getHttpServletRequest().getRequestURL().toString();
            String[] subcontexts =
                    (String[]) actionInvocation.getInvocationContext().getApplication().get("subcontext");
            return getPattern(requestUrl, subcontexts) + Action.LOGIN;
        } else {
            return actionInvocation.invoke();
        }
    }

    private String getPattern(String requestedPath, String[] acceptedPatterns) {
        String result = "";
        if (acceptedPatterns != null) {
            int i = 0;
            while (acceptedPatterns.length > i && result.equals("")) {
                if (requestedPath.contains("/" + acceptedPatterns[i] + "/")) {
                    result = acceptedPatterns[i];
                }
                i++;
            }
        }
        return result;
    }
}
