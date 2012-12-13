package com.thoughtress.jsp.gen;

public class Response extends Message{
	private String responseName;
	public Response(){
		super();
		responseName = "";
	}
	public void setResponseName(String name){
		responseName = name;
	}
	public String getResponseName(){
		return responseName;
	}
}
