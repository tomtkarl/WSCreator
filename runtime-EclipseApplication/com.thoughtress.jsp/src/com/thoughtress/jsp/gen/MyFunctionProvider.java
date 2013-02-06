package com.thoughtress.jsp.gen;

public class MyFunctionProvider extends FunctionProvider {
	//Start of user code classvars
private static String[] methods = {"GetStockPrice"};
//End of user code
	/**
	*Match the functions defined by this FunctionProvider to
	*the method name of the given Request.
	*
	*@param  req  The Request object to be matched against.
	*@return      A Boolean value denoting whether this FunctionProvider matches the given Request.
	*/
	public static Boolean match(Request req) {
		//Start of user code match
for (String m : methods){
	if (req.getMethod().equals(m)){
return true;
	}
}
return false;
        //End of user code
	}

	public Response process(Request req) {
		//Start of user code process
Response resp = new Response();
resp.setResponseName(req.getMethod() + "Response");
for (String key : req.getParamKeys()){
	resp.setParam(key, req.getParam(key));
}
	    resp.setParam("named", new String[]{"param"});
	    resp.setParam("list", new String[]{"params", "grouped", "as", "items"});
	    return resp;
        //End of user code
	}

}
