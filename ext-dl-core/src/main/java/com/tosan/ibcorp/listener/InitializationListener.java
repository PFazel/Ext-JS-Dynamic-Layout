package com.extdl.listener;

import com.extdl.tag.TagHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Parham Fazel
 */
public class InitializationListener implements ServletContextListener {
    public static final String SUPPORTED_LOCALES_CONTEXT_PARAM = "supportedLocales";
    public static final String SUPPORTED_LOCALES_MAP_KEY = "supportedLocalesMap";
    public static final String SUPPORTED_LOCALES_SET_KEY = "supportedLocalesSet";
    public final static String SUBCONTEXT = "subcontext";

    public void contextInitialized(ServletContextEvent event) {
        SLF4JBridgeHandler.install();

        putSupportedSubContext(event.getServletContext());
        Map<String, String> locales = new LinkedHashMap<String, String>();
        String supportedLocales = event.getServletContext().
                getInitParameter(SUPPORTED_LOCALES_CONTEXT_PARAM);
        if (supportedLocales == null) {
            throw new RuntimeException("You should specify a context parameter with name 'supportedLocales' and with" +
                    " a value such as 'fa=فارسی,en=English'.");
        }
        for (String supportedLocalePair : supportedLocales.split(",")) {
            String[] splittedPair = supportedLocalePair.split("=");
            locales.put(splittedPair[0], splittedPair[1]);
        }
        event.getServletContext().setAttribute(SUPPORTED_LOCALES_MAP_KEY, locales);
        event.getServletContext().setAttribute(SUPPORTED_LOCALES_SET_KEY, locales.keySet());
        TagHelper.setDefaultLocale(new Locale((String) locales.keySet().toArray()[0]));
    }

    private void putSupportedSubContext(ServletContext servletContext) {
        String acceptedPatterns = servletContext.getInitParameter(SUBCONTEXT);
        if (!StringUtils.isEmpty(acceptedPatterns)) {
            String[] subContext = StringUtils.split(acceptedPatterns, ',');
            servletContext.setAttribute(SUBCONTEXT, subContext);
        }
    }

    public void contextDestroyed(ServletContextEvent event) {
    }
}
