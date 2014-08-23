package com.extdl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: mrezaei
 * Date: 10/16/12
 */
public class SecurityUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUtil.class);
    public static final String SECURITY_ERROR = "SECURITY_ERROR";

    public static String checkXSS(String value) {
        if (value == null) {
            return null;
        }
        String oldValue = value;
        Pattern whiteSpaceChars = Pattern.compile("\\s", Pattern.DOTALL);
        Matcher whiteSpaceMatcher = whiteSpaceChars.matcher(value);
        while (whiteSpaceMatcher.find()) {
            value = value.replaceAll(whiteSpaceMatcher.group(), "");
        }
        //Due to OWASP documentation these 9 characters are harmful in xss attacks, except for some fields that
        //are never shown in page, such as password.
        if (value.contains("&") || value.contains("%") || value.contains(";") || value.contains("|") || value.contains(
                "$") || value.contains("#") || value.contains(")") || value.contains("(") || value.contains("'") ||
                value.contains("`") || value.contains("\"") || value.contains("<") || value.contains(">") ||
                value.toLowerCase().contains("null") || value.contains("\\") || value.contains("/*") ||
                value.contains("*/") || value.contains("../") || value.contains("..\\") //for )
                ) {
            LOGGER.debug(SECURITY_ERROR + " found. Input= \"" + oldValue + "\"" + " Output= " + SECURITY_ERROR);
            return SECURITY_ERROR;
        }
        Pattern evalPattern = Pattern.compile("eval\\((.*)\\)", Pattern.CASE_INSENSITIVE);
        Matcher evalMatcher = evalPattern.matcher(value);
        if (evalMatcher.find()) {
            LOGGER.debug(SECURITY_ERROR + " found. Input= \"" + oldValue + "\"" + " Output= " + SECURITY_ERROR);
            return SECURITY_ERROR;
        }
        //TODO: After a better regexp is found for "script" evaluation, the following lines for "description" should be removed!
        Pattern descriptionPattern = Pattern.compile("description", Pattern.CASE_INSENSITIVE);
        Matcher descriptionMatcher = descriptionPattern.matcher(value);
        if (descriptionMatcher.find()) {
            value = value.replaceAll(descriptionMatcher.group(), "");
        }
        Pattern scriptPattern = Pattern.compile("script", Pattern.CASE_INSENSITIVE);
        Matcher scriptMatcher = scriptPattern.matcher(value);
        if (scriptMatcher.find()) {
            LOGGER.debug(SECURITY_ERROR + " found. Input= \"" + oldValue + "\"" + ", Output= " + SECURITY_ERROR);
            return SECURITY_ERROR;
        }

        Pattern exPattern = Pattern.compile("expression", Pattern.CASE_INSENSITIVE);
        Matcher exMatcher = exPattern.matcher(value);
        if (exMatcher.find()) {
            LOGGER.debug(SECURITY_ERROR + " found. Input= \"" + oldValue + "\"" + " Output= " + SECURITY_ERROR);
            return SECURITY_ERROR;
        }
        return oldValue;
    }
}
