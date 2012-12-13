package com.thoughtress.jsp.gen;

public abstract class MessageFormatter {
	public static String[] getTypes(){
		return null;
	}
	public Request parseToRequest(String data){
		return null;
	}
	public String parseToFormat(Response answer){
		return null;
	}
}
