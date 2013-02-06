package com.thoughtress.jsp.gen;

public abstract class FunctionProvider {
	public static Boolean match(Request req){
		throw new UnsupportedOperationException("Not yet implemented");
	}
	/**
	*Process the given Request using the defined method.
	*
	*@param  req  The Request object to processed.
	*@return      A Response object constructed using the results of processing the request.
	*/
	public Response process(Request req){
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
