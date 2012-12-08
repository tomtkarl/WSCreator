package com.thoughtress.jsp.gen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyEndpoint{

	public static void doGet(HttpServletRequest request, HttpServletResponse response){
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    //pw.println("Content-Type: " + request.getContentType());
	    pw.println("<h1>Served by doGet</h1>");
	}
	public static void doPost(HttpServletRequest request, HttpServletResponse response){
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
	    //pw.println("Content-Type: " + request.getContentType());
	    Request req = MyFormatter.parseToRequest(data.toString());
	    System.out.println("Method: " + req.getMethod() + "\n");
	    Response resp = null;
	    if (MyFunctionProvider.match(req)){
	    	FunctionProvider func = new MyFunctionProvider();
	    	resp = func.process(req);
	    }
	    
	    if (resp != null){
		    String ret = MyFormatter.parseToFormat(resp);
		    pw.println(ret);
			//for (String key : msg.getParamKeys()){
				//pw.println("Param: " + key + " : " + msg.getParam(key));
			//}	
	    }
	}
}
