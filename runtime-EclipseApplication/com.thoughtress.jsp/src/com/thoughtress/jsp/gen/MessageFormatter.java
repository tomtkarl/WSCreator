package com.thoughtress.jsp.gen;
//Start of user code imports
import javax.servlet.http.HttpServletRequest;
//End of user code
/**
*The MessageFormatter abstract class.<br />
*MessageFormatter is subclassed by all Message Formatter implementations.
*/
public abstract class MessageFormatter {
	/**
	*Advertise the message formats supported by this formatter. <br />
	*It is suggested to use proper MIME types.
	*
	*@return A String array containing the the supported message formats.
	*/
	public static boolean match(HttpServletRequest request){
		throw new UnsupportedOperationException("Not yet implemented");
	}
	/**
	*Parse the given data string to a Request object
	*This method may return null if the data is not a recognised message format.
	*
	*@param data  The data string to be parsed
	*@return      A MessagePart object derived from the given data.
	*/
	public MessagePart parseToRequest(String data){
		throw new UnsupportedOperationException("Not yet implemented");
	}
	/**
	*Parse the given MessagePart object to a message format.
	*
	*@param  answer  The MessagePart object to be parsed to string
	*@return         A MessagePart object derived from the given data.
	*/
	public String parseToFormat(MessagePart answer){
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
