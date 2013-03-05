package com.thoughtress.jsp.gen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Endpoint/Servlet entry point for the named Web Service
 * 
 */
@WebServlet("/MyEndpoint/*")
public class MyEndpoint extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handle GET requests to the service
     * 
     * @param request HttpServletRequest object representing all request attributes
     * @param response HttpServletResponse to be used in the construction of a response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        handleRequest(null, request, response);
    }

    /**
     * Handle POST requests to the service
     * 
     * @param request HttpServletRequest object representing all request attributes
     * @param response HttpServletResponse to be used in the construction of a response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        StringBuffer data = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                data.append(line);
        } catch (Exception e) {
            sendHTTPError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Server Error Whilst Reading Request");
        }
        handleRequest(data.toString(), request, response);
    }

    /**
     * Handle POST and GET requests
     * 
     * @param data A String containing any POST data. Use null for GET requests.
     * @param request HttpServletRequest object representing all request attributes
     * @param response HttpServletResponse to be used in the construction of a response
     */
    private void handleRequest(String data, HttpServletRequest request, HttpServletResponse response) {
        String ret;
        MessageFormatter mf = null;
        ;
        try {
            mf = getFormatter(request);
            MessagePart req = mf.parseToRequest(data, request);
            FunctionProvider func = getFunctionProvider(req);
            MessagePart resp = func.process(req);
            ret = mf.parseToFormat(resp);
        } catch (UserServiceException e) {
            try {
                e.printStackTrace();
                if (mf != null) {
                    ret = mf.buildError(e);
                } else {
                    ret = getDefaultFormatter().buildError(e);
                }
            } catch (Exception ohdear) {
                ohdear.printStackTrace();
                sendHTTPError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        ohdear.getMessage());
                return;
            }
        }
        PrintWriter pw = null;
        response.setCharacterEncoding("utf-8");
        try {
            pw = response.getWriter();
            pw.println(ret);
        } catch (IOException e) {
            e.printStackTrace();
            sendHTTPError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Server Error Whilst Sending Response");
        }
    }

    public void sendHTTPError(HttpServletResponse response, int code, String message) {
        try {
            response.sendError(code, message);
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private MessageFormatter getDefaultFormatter(){
        return new MyFormatter();
    }

    private MessageFormatter getFormatter(HttpServletRequest request) throws UserServiceException{
        MessageFormatter mf;
        if (MyFormatter.match(request)) {
            mf = new MyFormatter();
        } else if (GetFormatter.match(request)) {
            mf = new GetFormatter();
        } else {
            throw new UserServiceException(400, "Unsupported Content Type");
        }
        return mf;
    }

    private FunctionProvider getFunctionProvider(MessagePart req) throws UserServiceException{
        FunctionProvider func;
        if (MyFunctionProvider.match(req)) {
            func = new MyFunctionProvider();
        } else if (GithubFunctionProvider.match(req)) {
            func = new GithubFunctionProvider();
        } else if (TwitterFunctionProvider.match(req)) {
            func = new TwitterFunctionProvider();
        } else {
            throw new UserServiceException(404, "Requested Method Not Found");
        }
        return func;
    }
}
