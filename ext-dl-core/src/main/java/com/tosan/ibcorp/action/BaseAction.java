package com.extdl.action;

import com.kishware.ebank.channelmanager.bank.context.Context;
import com.kishware.ebank.channelmanager.bank.context.LanguageType;
import com.opensymphony.xwork2.ActionSupport;
import com.extdl.tag.TagHelper;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


/**
 * @author ahadi
 * @since 12/23/12
 */
public abstract class BaseAction extends ActionSupport
        implements ServletRequestAware, ServletResponseAware, ServletContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseAction.class);
    private static ServletContext servletContext;
    private HttpServletResponse httpServletResponse;
    protected HttpServletRequest httpServletRequest;
    protected static String channelName;
    public static final String CHANNEL_NAME = "channelName";
    public static final String MOBILE_TYPE = "mobileType";
    public static final String LANGUAGE = "language";
    public static final String USER_ACTIVATED_SERVICES = "serviceList";
    public static final String DISPATCHER_APPLIED = "dispatcherApplied";
    protected Map<String, Object> jsonResultMap = new HashMap<String, Object>();
    private String language;

    public Context getContext() {
        Context context = new Context();
        HttpSession session = httpServletRequest.getSession();
        if (httpServletRequest != null && session != null && session.getAttribute(Context.SESSIONID) != null) {
            context.addData(Context.SESSIONID, (String) session.getAttribute(Context.SESSIONID));
            context.addData(Context.CHANNELNAME, (session.getAttribute("channelName") != null) ?
                    (String) session.getAttribute(CHANNEL_NAME) : channelName);
            context.addData(Context.VERSION, (String) session.getAttribute(Context.VERSION));
            context.addData(Context.CLIENT_ADDRESS, (String) session.getAttribute(Context.CLIENT_ADDRESS));
            context.setLanguage((LanguageType) session.getAttribute(LANGUAGE));
        } else {
            context = null;
        }
        return context;
    }

    protected String getClientAddress() {
        return httpServletRequest.getRemoteAddr();
    }

    public void setLanguage(String language) {

        if (ArrayUtils.contains(LanguageType.values(), language)) {
            this.language = language;
        } else if (TagHelper.PERSIAN_LANGUAGE.equalsIgnoreCase(language)) {
            this.language = "Local";
        } else if (TagHelper.FOREIGN_LANGUAGE.equalsIgnoreCase(language)) {
            this.language = "Foreign";
        } else {
            this.language = LanguageType.Local.toString();
        }
    }

    public String getLanguage() {
        return language;
    }

    public static ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        BaseAction.servletContext = servletContext;
    }

    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }

    public void setServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }

    public Map<String, Object> getJsonResultMap() {
        return jsonResultMap;
    }

    public void setJsonResultMap(Map<String, Object> jsonResultMap) {
        this.jsonResultMap = jsonResultMap;
    }
}
