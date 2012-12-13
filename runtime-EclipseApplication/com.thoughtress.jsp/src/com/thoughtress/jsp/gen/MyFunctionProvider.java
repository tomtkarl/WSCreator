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
		for (String key : req.getParamKeys()){
			resp.setParam(key, req.getParam(key));
		}
	    resp.setParam("named", new String[]{"param"});
	    resp.setParam("list", new String[]{"params", "grouped", "as", "items"});
	    return resp;
	}

}
