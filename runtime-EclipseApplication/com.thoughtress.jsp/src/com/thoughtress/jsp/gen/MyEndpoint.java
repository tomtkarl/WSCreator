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
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MessageFormatter mf = getFormatter(request);
        if (mf == null) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Unsupported Content Type");
            return;
        }
        MessagePart req = mf.parseToRequest(null, request);

        FunctionProvider func = getFunctionProvider(req);
        if (func == null) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, "Requested Method Not Found");
            return;
        }
        MessagePart resp = func.process(req);
        String ret = mf.parseToFormat(resp);
        pw.println(ret);
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
        } catch (Exception e) { /* report an error */
        }

        PrintWriter pw = null;
        try {
            pw = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MessageFormatter mf = getFormatter(request);
        if (mf == null) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Unsupported Content Type");
            return;
        }
        MessagePart req = mf.parseToRequest(data.toString(), request);

        FunctionProvider func = getFunctionProvider(req);
        if (func == null) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, "Requested Method Not Found");
            return;
        }
        MessagePart resp = func.process(req);
        String ret = mf.parseToFormat(resp);
        pw.println(ret);
    }

    protected void sendError(HttpServletResponse response, int code, String message) {
        try {
            response.sendError(code, message);
            return;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    protected MessageFormatter getFormatter(HttpServletRequest request) {
        MessageFormatter mf = null;
        if (MyFormatter.match(request)) {
            mf = new MyFormatter();
        } else if (GetFormatter.match(request)) {
            mf = new GetFormatter();
        }
        return mf;
    }

    protected FunctionProvider getFunctionProvider(MessagePart req) {
        FunctionProvider func = null;
        if (MyFunctionProvider.match(req)) {
            func = new MyFunctionProvider();
        } else if (GithubFunctionProvider.match(req)) {
            func = new GithubFunctionProvider();
        } else if (TwitterFunctionProvider.match(req)) {
            func = new TwitterFunctionProvider();
        }
        return func;
    }
}
