package com.thoughtress.jsp.gen;

public interface MessageFormatter {
	public  String[] getTypes();
	public  Request parseToRequest(String data);
	public  String parseToFormat(Response answer);
}
