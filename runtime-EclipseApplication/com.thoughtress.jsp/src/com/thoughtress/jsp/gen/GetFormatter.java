package com.thoughtress.jsp.gen;

//Start of user code imports
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

//End of user code
/**
 * A Message Formatter for the Web Service<br />
 * Implements one or more Message Formats to support the web service.
 */
public class GetFormatter extends MessageFormatter {
    public static boolean match(HttpServletRequest req) {
        // Start of user code getTypes
        return req.getMethod().equals("GET");
        // End of user code
    }

    @Override
    public MessagePart parseToRequest(String data, HttpServletRequest request) {
        // Start of user code parseToRequest
        MessagePart req = new MessagePart("GET");
        req.attrs.put("host", request.getServerName());
        req.attrs.put("contextPath", request.getContextPath());
        req.attrs.put("servletPath", request.getServletPath());
        req.attrs.put("pathInfo", request.getPathInfo());
        System.out.println(request+"::"+req.attrs);
        for (String key : Collections.list(request.getParameterNames())){
            req.attrs.put(key, request.getParameter(key));
        }
        return req;
        // End of user code
    }

    @Override
    public String parseToFormat(MessagePart answer) {
        // Start of user code parseToFormat
        return answer.toString(); 
        // End of user code
    }
}
