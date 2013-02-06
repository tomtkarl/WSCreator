package com.thoughtress.jsp.gen;

/**
*A specific Message type representing the <b>outgoing</b> web service response.
*/
public class Response extends Message{
	private String responseName;
	/**
	*Create a new Response object with an empty response name
	*/
	public Response(){
		super();
		responseName = "";
	}
	/**
	*Set the response name of the Response
	*
	*@param name  The response name of the Response
	*/
	public void setResponseName(String name){
		responseName = name;
	}
	/**
	*@return The response name
	*/
	public String getResponseName(){
		return responseName;
	}
}
