package com.thoughtress.jsp.gen;

import java.util.HashMap;

public class Message {
	private HashMap<String, String[]> params;
	public Message(){
		this.params = new HashMap<String, String[]>();
	}
	public void setParam(String key, String[] value){
		params.put(key, value);
	}
	public void setParams(HashMap<String, String[]> _params){
		for (String key : _params.keySet()){
			params.put(key, _params.get(key));
		}
	}
	public String[] getParamKeys(){
		return params.keySet().toArray(new String[0]);
	}
	public String[] getParam(String key){
		return params.get(key);
	}
	public HashMap<String,String[]> getParams(){
		return params;
	}
}
