package com.thoughtress.jsp.gen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
*The Endpoint/Servlet entry point for the named Web Service
*
*/
@WebServlet("/MyEndpoint")
public class MyEndpoint extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	/**
	*Handle GET requests to the service
	*
	*@param request   HttpServletRequest object representing all request attributes
	*@param response  HttpServletResponse to be used in the construction of a response
	*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response){
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    pw.println("<h1>Served by doGet</h1>");
	}
	
	/**
	*Handle POST requests to the service
	*
	*@param request   HttpServletRequest object representing all request attributes
	*@param response  HttpServletResponse to be used in the construction of a response
	*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		StringBuffer data = new StringBuffer();
		  String line = null;
		  try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null)
		      data.append(line);
		  } catch (Exception e) { /*report an error*/ }

	    PrintWriter pw = null;
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//parse request using the correct MessageFormatter
		MessageFormatter mf = null;
		if (Arrays.asList(MyFormatter.getTypes()).contains(request.getContentType())){
			mf = new MyFormatter();	
		}
		else if (Arrays.asList(MyGenericFormatter.getTypes()).contains(request.getContentType())){
			mf = new MyGenericFormatter();	
		}
		else {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,
								   "Unsupported Content Type");
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		MessagePart req = mf.parseToRequest(data.toString());
		
	    FunctionProvider func = null;
		if (MyFunctionProvider.match(req)){
	    	func = new MyFunctionProvider();
	    }
		else if (GithubFunctionProvider.match(req)){
	    	func = new GithubFunctionProvider();
	    }
	    else {
			try {
				response.sendError(HttpServletResponse.SC_NOT_FOUND,
								   "Requested Method Not Found");
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}    
	    if (func != null){
	    	MessagePart resp = func.process(req);
		    String ret = mf.parseToFormat(resp);
		    pw.println(ret);
	    }
	}
}
