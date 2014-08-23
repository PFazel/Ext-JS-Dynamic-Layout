package com.extdl.interceptor;

import com.extdl.util.RequestWrapper;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.extdl.action.BaseAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * User: javidan
 * Date: Dec 12, 2009
 * Time: 12:04:36 PM
 */
public class PreventCrossSiteScriptingInterceptor implements Interceptor {
    private static Logger log = LoggerFactory.getLogger(ExceptionInterceptor.class.getName());
    //private static final String SECURITY_RISK = "securityRisk";
    private static final int ERROR_STATUS = 500;
    private static final String SECURITY_ERROR = "SECURITY_ERROR";

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    private HttpServletRequest httpServletRequest;

    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation invocation) throws Exception {
        BaseAction baseAction = (BaseAction) invocation.getAction();
        RequestWrapper wrapper = new RequestWrapper(baseAction.getHttpServletRequest());
        Map parameters = wrapper.getParameterMap();
        Set keys = parameters.keySet();
        checkSecurityError(keys.toArray(), baseAction);
        for (Object key : keys) {
            Object value = parameters.get(key);
            checkSecurityError(value, baseAction);
        }
        return invocation.invoke();
    }

    private void checkSecurityError(Object object, BaseAction baseAction) {
        String[] obj = (String[]) object;
        for (String s : obj) {
            if (s.equals(SECURITY_ERROR)) {
                log.warn("Xss filter has found a security problem.");
                baseAction.getHttpServletResponse().setStatus(ERROR_STATUS);
                throw new SecurityException("Xss filter has found a security problem.");
            }
        }
    }

    private void checkSecurityError(Object[] object, BaseAction baseAction) {
        for (Object s : object) {
            if (s.equals(SECURITY_ERROR)) {
                log.warn("Xss filter has found a security problem.");
                baseAction.getHttpServletResponse().setStatus(ERROR_STATUS);
                throw new SecurityException("Xss filter has found a security problem.");
            }
        }
    }
}
