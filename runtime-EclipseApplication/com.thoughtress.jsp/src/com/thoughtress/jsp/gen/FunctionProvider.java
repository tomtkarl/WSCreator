package com.thoughtress.jsp.gen;

/**
*The FunctionProvider abstract class<br />
*FunctionProvider is subclassed by all Function Provider implementations.
*/
public abstract class FunctionProvider {
	public static Boolean match(MessagePart req){
		throw new UnsupportedOperationException("Not yet implemented");
	}
	/**
	*Process the given MessagePart using the defined method.
	*
	*@param  req  The MessagePart object to processed.
	*@return      A MessagePart object constructed using the results of processing the request.
	*/
	public MessagePart process(MessagePart req){
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
