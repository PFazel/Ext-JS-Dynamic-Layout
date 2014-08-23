package com.extdl.tag;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.jsp.PageContext;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Map;

/**
 * @author Parham Fazel
 * @since Jun 1, 2008
 */
public class TagHelper {
    public static final String PAGE_LOCALE_ATTRIBUTE_NAME = "javax.servlet.jsp.jstl.fmt.locale.page";
    public static final String REQUEST_LOCALE_ATTRIBUTE_NAME = "javax.servlet.jsp.jstl.fmt.locale.request";
    public static final String SESSION_LOCALE_ATTRIBUTE_NAME = "javax.servlet.jsp.jstl.fmt.locale.session";
    public static final String APPLICATION_LOCALE_ATTRIBUTE_NAME = "javax.servlet.jsp.jstl.fmt.locale.application";
    public static final String ZERO = "0";
    public static final String DEPOSIT_DELIMITER = "-";
    public static final String PARSIAN_CUSTOMER = "Parsian";
    public static final String NON_PARSIAN_CUSTOMER = "NonParsian";
    public static final char DEPOSIT_DELIMITER_CHAR = '-';
    public static final int CIF_LENGTH = 8;
    public static final int ACCOUNT_SERIAL_LENGTH = 3;
    public static final String PERSIAN_LANGUAGE = "fa";
    public static final String FOREIGN_LANGUAGE = "en";

    private static final String PERSIAN_RIAL_SIGN = "\uFDFC";
    private static final char PERSIAN_PERCENT_SIGN = '\u066A';
    private static final char PERSIAN_DECIMAL_SEPARATOR = '\u066B';
    private static final char PERSIAN_THOUSANDS_SEPARATOR = '\u066C';
    private static DecimalFormatSymbols persianDecimalFormatSymbols;
    private static Locale defaultLocale = Locale.getDefault();

    public static boolean isPersianLanguage(PageContext pageContext) {
        return getLocale(pageContext).getLanguage().equals(PERSIAN_LANGUAGE);
    }

    public static boolean isRtl(PageContext pageContext) {
        return getLocale(pageContext).getLanguage().equals(PERSIAN_LANGUAGE);
    }

    public static Locale getLocale(PageContext pageContext) {
        Object result;

        result = pageContext.getAttribute(PAGE_LOCALE_ATTRIBUTE_NAME);
        if (result instanceof Locale) {
            return (Locale) result;
        }

        result = pageContext.getRequest().getAttribute(REQUEST_LOCALE_ATTRIBUTE_NAME);
        if (result instanceof Locale) {
            return (Locale) result;
        }

        result = pageContext.getSession().getAttribute(SESSION_LOCALE_ATTRIBUTE_NAME);
        if (result instanceof Locale) {
            return (Locale) result;
        }

        result = pageContext.getServletContext().getAttribute(APPLICATION_LOCALE_ATTRIBUTE_NAME);
        if (result instanceof Locale) {
            return (Locale) result;
        }

        return getDefaultLocale();
    }

    public static Locale getLocale(Map request, Map session, Map application) {
        Object result;

        result = request.get(REQUEST_LOCALE_ATTRIBUTE_NAME);
        if (result instanceof Locale) {
            return (Locale) result;
        }

        result = session.get(SESSION_LOCALE_ATTRIBUTE_NAME);
        if (result instanceof Locale) {
            return (Locale) result;
        }

        result = application.get(APPLICATION_LOCALE_ATTRIBUTE_NAME);
        if (result instanceof Locale) {
            return (Locale) result;
        }

        return getDefaultLocale();
    }

    public static Locale getDefaultLocale() {
        return defaultLocale;
    }

    public static void setDefaultLocale(Locale locale) {
        defaultLocale = locale;
    }

    public static synchronized DecimalFormatSymbols getPersianDecimalFormatSymbols() {
        if (persianDecimalFormatSymbols == null) {
            DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
            decimalFormatSymbols.setPercent(TagHelper.PERSIAN_PERCENT_SIGN);
            decimalFormatSymbols.setDecimalSeparator(TagHelper.PERSIAN_DECIMAL_SEPARATOR);
            decimalFormatSymbols.setGroupingSeparator(TagHelper.PERSIAN_THOUSANDS_SEPARATOR);
            decimalFormatSymbols.setCurrencySymbol(TagHelper.PERSIAN_RIAL_SIGN);
            persianDecimalFormatSymbols = decimalFormatSymbols;
        }
        return persianDecimalFormatSymbols;
    }

    public static String convertToPersianDigits(String stringWithDigits) {
        return stringWithDigits.replace('0', '\u06F0').replace('1', '\u06F1').replace('2', '\u06F2')
                .replace('3', '\u06F3').replace('4', '\u06F4').replace('5', '\u06F5').replace('6', '\u06F6')
                .replace('7', '\u06F7').replace('8', '\u06F8').replace('9', '\u06F9');
    }

    public static String convertToEnglishDigits(String stringWithDigits) {
        return stringWithDigits.replace('\u06F0', '0').replace('\u06F1', '1').replace('\u06F2', '2')
                .replace('\u06F3', '3').replace('\u06F4', '4').replace('\u06F5', '5').replace('\u06F6', '6')
                .replace('\u06F7', '7').replace('\u06F8', '8').replace('\u06F9', '9');
    }

    public static String convertDeigits(String stringWithDigits, Locale locale) {
        if (locale.toString().equals(PERSIAN_LANGUAGE) && StringUtils.isNotEmpty(stringWithDigits)) {
            stringWithDigits = convertToPersianDigits(stringWithDigits);
        }
        return stringWithDigits;
    }

    public static int getScope(String scope) {
        int result = 1;
        if ("request".equalsIgnoreCase(scope)) {
            result = 2;
        } else if ("session".equalsIgnoreCase(scope)) {
            result = 3;
        } else if ("application".equalsIgnoreCase(scope)) {
            result = 4;
        }
        return result;
    }

    public static String convertArabicCharacterToPersian(String inString) {
        return inString.replaceAll("ی", "ي");
    }

}
