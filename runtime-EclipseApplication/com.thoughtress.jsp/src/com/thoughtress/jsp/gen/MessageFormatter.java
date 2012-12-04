package com.thoughtress.jsp.gen;

public interface MessageFormatter {
	public  Request parseToRequest(String data);
	public  String parseToFormat(Response answer);
}
