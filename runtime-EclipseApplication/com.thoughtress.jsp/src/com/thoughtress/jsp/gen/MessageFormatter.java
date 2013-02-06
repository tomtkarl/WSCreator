package com.thoughtress.jsp.gen;

public abstract class MessageFormatter {
	/**
	*Advertise the message formats supported by this formatter.
	*It is suggested to proper MIME types.
	*
	*@return A String array containing the the supported message formats.
	*/
	public static String[] getTypes(){
		throw new UnsupportedOperationException("Not yet implemented");
	}
	/**
	*Parse the given data string to a Request object
	*This method may return null if the data is not a recognised message format.
	*
	*@param data  The data string to be parsed
	*@return      A Request object derived from the given data.
	*/
	public Request parseToRequest(String data){
		throw new UnsupportedOperationException("Not yet implemented");
	}
	/**
	*Parse the given Response object to a message format.
	*
	*@param  answer  The Response object to be parsed to string
	*@return         A Request object derived from the given data.
	*/
	public String parseToFormat(Response answer){
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
