package com.thoughtress.jsp.gen;

public class Response extends Message{
	private String responseName;
	public Response(){
		super();
		responseName = "";
	}
	public void responseTo(Request req){
		responseName = req.getMethod() + "Response";
	}
	public String getResponseName(){
		return responseName;
	}
}
