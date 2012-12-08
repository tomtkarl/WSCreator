package com.thoughtress.jsp.gen;

public class MyFunctionProvider extends FunctionProvider {

	public static Boolean match(Request req) {
		return true;
	}

	public Response process(Request req) {
		Response resp = new Response();
		resp.setResponseName(req.getMethod() + "Response");
	    resp.setParam("foo", "bar");
	    resp.setParam("Karl", "AWESOME");
	    return resp;
	}

}
