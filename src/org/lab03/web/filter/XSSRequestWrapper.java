package org.lab03.web.filter;

import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XSSRequestWrapper extends HttpServletRequestWrapper {

    public XSSRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return value != null ? stripXSS(value) : null;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null) {
            return null;
        }
        
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = stripXSS(values[i]);
        }
        return encodedValues;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return value != null ? stripXSS(value) : null;
    }

    private String stripXSS(String value) {
        if (value == null) {
            return null;
        }
        
        // Remove all < and > characters
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        
        // Remove all script tags
        value = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE).matcher(value).replaceAll("");
        
        // Remove src='...' attributes
        value = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL).matcher(value).replaceAll("");
        value = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL).matcher(value).replaceAll("");
        
        // Remove event handlers
        value = Pattern.compile("on\\w+\\s*=\\s*\"[^\"]*\"", Pattern.CASE_INSENSITIVE).matcher(value).replaceAll("");
        value = Pattern.compile("on\\w+\\s*=\\s*'[^']*'", Pattern.CASE_INSENSITIVE).matcher(value).replaceAll("");
        
        return value;
    }
}