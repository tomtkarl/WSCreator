package com.thoughtress.jsp.gen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyEndpoint{
	public static void respond(HttpServletRequest request, HttpServletResponse response){
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
	    pw.println("Content-Type: " + request.getContentType());
	    Message msg = MyFormatter.parseToMessage(data.toString());
	    pw.println("Method: " + msg.getMethod());
		for (String key : msg.getParamKeys()){
			pw.println("Param: " + key + " : " + msg.getParam(key));
		}	
	}
}
