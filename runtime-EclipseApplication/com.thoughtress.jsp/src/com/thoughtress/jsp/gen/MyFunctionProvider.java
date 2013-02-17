package com.thoughtress.jsp.gen;

/**
*A FunctionProvider for the Web Service<br />
*Implements one or more functions to be made available through the service.
*/
public class MyFunctionProvider extends FunctionProvider {
	//Start of user code classvars
private static String[] methods = {"GetStockPrice"};
//End of user code
	/**
	*Match the functions defined by this FunctionProvider to
	*the method of the given MessagePart.
	*
	*@param  req  The MessagePart object to be matched against.
	*@return      A Boolean value denoting whether this FunctionProvider matches the given MessagePart.
	*/
	public static Boolean match(MessagePart req) {
		//Start of user code match
		System.out.println("match::" + req.name);
		if (req.name.equals("EchoRequest")){
        	return true;
        } else {
        	return false;
        }
        //End of user code
	}

	public MessagePart process(MessagePart req) {
		//Start of user code process
return req;
        //End of user code
	}
}
