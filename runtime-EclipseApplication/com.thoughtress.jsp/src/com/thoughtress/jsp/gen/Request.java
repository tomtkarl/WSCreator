package com.thoughtress.jsp.gen;

/**
*A specific Message type representing the <b>incoming</b> web service request.
*/
public class Request extends Message{
	private String method;
	/**
	*Create a new Request object with an empty method
	*/
	public Request(){
		super();
		method = "";
	}
	/**
	*Set the method of the Request
	*
	*@param meth  The method name of the Request
	*/
	public void setMethod(String meth){
		method = meth;
	}
	/**
	*@return The method name
	*/
	public String getMethod(){
		return method;
	}
}
