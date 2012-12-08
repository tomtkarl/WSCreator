package com.thoughtress.jsp.gen;

public class MyFunctionProvider extends FunctionProvider {
	private static String[] methods = {"GetStockPrice"}; 
	public static Boolean match(Request req) {
		for (String m : methods){
			if (req.getMethod().equals(m)){
				return true;
			}
		}
		return false;
	}

	public Response process(Request req) {
		Response resp = new Response();
		resp.setResponseName(req.getMethod() + "Response");
	    resp.setParam("foo", "bar");
	    resp.setParam("Karl", "AWESOME");
	    return resp;
	}

}
