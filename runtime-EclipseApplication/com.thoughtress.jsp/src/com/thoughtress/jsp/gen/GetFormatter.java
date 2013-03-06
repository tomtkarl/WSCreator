package com.thoughtress.jsp.gen;

//Start of user code imports
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

//End of user code
/**
 * A Message Formatter for the Web Service<br />
 * Implements one or more Message Formats to support the web service.
 */
public class GetFormatter extends MessageFormatter {
    public static boolean match(HttpServletRequest req) {
        // Start of user code match
        return req.getMethod().equals("GET");
        // End of user code
    }

    @Override
    public MessagePart parseToRequest(String data, HttpServletRequest request)
            throws UserServiceException {
        // Start of user code parseToRequest
        String method = request.getPathInfo().split("/", 2)[1];
        MessagePart req = new MessagePart(method);
        req.attrs.put("host", request.getServerName());
        req.attrs.put("contextPath", request.getContextPath());
        req.attrs.put("servletPath", request.getServletPath());
        req.attrs.put("pathInfo", request.getPathInfo());
        for (String key : Collections.list(request.getParameterNames())) {
            MessagePart param = new MessagePart(key);
            param.textValue = request.getParameter(key);
            req.children.add(param);
        }
        return req;
        // End of user code
    }

    @Override
    public String parseToFormat(MessagePart answer) throws UserServiceException {
        // Start of user code parseToFormat
        return answer.toString();
        // End of user code
    }

    @Override
    public String buildError(UserServiceException e) throws Exception {
        // Start of user code buildError
        return "" + e.code + " " + e.getMessage();
        // End of user code
    }
}
