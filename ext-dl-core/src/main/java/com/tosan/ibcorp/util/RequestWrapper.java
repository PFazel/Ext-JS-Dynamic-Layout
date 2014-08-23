package com.extdl.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * @author reza
 */
public class RequestWrapper extends HttpServletRequestWrapper {
    private static final String SECURITY_ERROR = "SECURITY_ERROR";
    private static final List<String> XSSWhiteListParameterNames =
            Arrays.asList("password", "oldPassword", "newPassword", "confirmPassword", "secondPassword");

    @Override
    /**
     * This method overrides HttpServletRequestWrapper s getQueryString() method that is used after getRequestURL() OR
     * getRequestURI() methods & cleans the threats...
     */
    public String getQueryString() {
        return cleanXSS(super.getQueryString());
    }

    @Override
    /**
     * This method overrides HttpServletRequestWrapper s getParameterMap() method & sanitizes all request parameters from suspicious threats.
     */
    public Map getParameterMap() {
        Map parameterMap = super.getParameterMap();
        Map sanitizedParameterMap = new HashMap();
        for (Object o : parameterMap.keySet()) {
            // we also make KEY part of parameterMap sanitized...
            String oldKey = (String) o;
            String cleanKey = cleanXssForKeys(oldKey);
            String[] valueArray = (String[]) parameterMap.get(oldKey);
            String[] sanitizedValueArray = new String[valueArray.length];
            for (int i = 0; i < valueArray.length; i++) {
                // the main part - cleaning the VALUE part of parameterMap...
                if (StringUtils.startsWith(valueArray[i], "[") || StringUtils.startsWith(valueArray[i],
                        "{")) { //The parameter may be a json object.
                    try {
                        sanitizedParameterMap.putAll(cleanXSSForJSON(oldKey, valueArray[i]));
                    } catch (JSONException e) { //If the parameter is not a json object, it is assumed to be an ordinary parameter, so it should be checked for xss.
                        sanitizedValueArray[i] = cleanXSS(oldKey, valueArray[i]);
                        sanitizedParameterMap.put(cleanKey, sanitizedValueArray);
                    }
                } else {
                    sanitizedValueArray[i] = cleanXSS(oldKey, valueArray[i]);
                    sanitizedParameterMap.put(cleanKey, sanitizedValueArray);
                }
            }
        }
        return sanitizedParameterMap;
    }

    private String cleanXssForKeys(String key) {
        if (key != null) {
            return cleanXSS(key);
        }
        return null;
    }

    public RequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    public String[] getParameterValues(String parameter) {

        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = cleanXSS(values[i]);
        }
        return encodedValues;
    }

    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value == null) {
            return null;
        }
        return cleanXSS(value);
    }

    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null) {
            return null;
        }
        return cleanXSS(value);
    }

    private String cleanXSS(String value) {
        if (value == null) {
            return null;
        }
        String result = SecurityUtil.checkXSS(value);
        if (StringUtils.equals(result, SecurityUtil.SECURITY_ERROR)) {
            return SECURITY_ERROR;
        } else {
            return value;
        }
    }

    private Map cleanXSSForJSON(String paramName, String paramValue) {
        JSONArray jsonArray;
        JSONObject jsonObject;
        if (StringUtils.startsWith(paramValue, "[")) {
            jsonArray = JSONArray.fromObject(paramValue.trim());
            return cleanXssJSONArray(paramName, jsonArray);
        } else if (StringUtils.startsWith(paramValue, "{")) {
            jsonObject = JSONObject.fromObject(paramValue.trim());
            return cleanXssJSONObject(paramName, jsonObject);
        }
        return null;
    }

    private Map cleanXssJSONArray(String paramName, JSONArray jsonArray) {
        if (jsonArray != null && jsonArray.size() > 0) {
            Map sanitizedParameterMap = new HashMap();
            String[] sanitizedValueArray = new String[jsonArray.size()];
            // we also make KEY part of parameterMap sanitized...
            String oldName = paramName;
            String cleanName = cleanXssForKeys(oldName);
            for (int index = 0; index < jsonArray.size(); index++) {
                Object element = jsonArray.get(index);
                if (element instanceof JSONNull) {
                    //skip
                } else if (element instanceof JSONObject) {
                    return cleanXssJSONObject(oldName, (JSONObject) element);
                } else if (element instanceof JSONArray) {
                    return cleanXssJSONArray(oldName, (JSONArray) element);
                } else {
                    // the main part - cleaning the VALUE part of parameterMap...
                    sanitizedValueArray[index] = cleanXSS((String) element);
                    sanitizedParameterMap.put(cleanName + index, sanitizedValueArray);
                    return sanitizedParameterMap;
                }
            }
        }
        return null;
    }

    private Map cleanXssJSONObject(String paramName, JSONObject jsonObject) {
        if (jsonObject != null) {
            Iterator<?> keys = jsonObject.keys();
            Map sanitizedParameterMap = new HashMap();
            String[] sanitizedValueArray = new String[1];
            while (keys.hasNext()) {
                String propertyKey = (String) keys.next();
                Object propertyValue = jsonObject.get(propertyKey);
                if (propertyValue instanceof JSONNull) {
                    //skip
                } else if (propertyValue instanceof JSONObject) {
                    return cleanXssJSONObject(paramName, (JSONObject) propertyValue);
                } else if (propertyValue instanceof JSONArray) {
                    return cleanXssJSONArray(paramName, (JSONArray) propertyValue);
                } else {
                    // we also make KEY part of parameterMap sanitized...
                    String cleanName = cleanXssForKeys(paramName);
                    // the main part - cleaning the VALUE part of parameterMap...
                    sanitizedValueArray[0] = cleanXSS(paramName, cleanXSS((String) propertyValue));
                    sanitizedParameterMap.put(cleanName, sanitizedValueArray);
                    return sanitizedParameterMap;
                }
            }
        }
        return null;
    }

    private String cleanXSS(String paramName, String paramValue) {
        if (!XSSWhiteListParameterNames.contains(paramName)) {
            return cleanXSS(paramValue);
        } else {
            return paramValue;
        }
    }
}
