package com.thoughtress.jsp.gen;

public class Request extends Message{
	private String method;
	public Request(){
		super();
		method = "";
	}
	public void setMethod(String _method){
		method = _method;
	}
	public String getMethod(){
		return method;
	}
}
