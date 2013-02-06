package com.thoughtress.jsp.gen;

import java.util.HashMap;

/**
*A generic Message class to be subclassed by specialised Message types.
*/
public class Message {
	private HashMap<String, String[]> params;
	public Message(){
		this.params = new HashMap<String, String[]>();
	}
	/**
	*Set a key,value pair in the Message parameters.
	*This will overwrite existing keys if already set.
	*
	*@param key   The key to be inserted
	*@param value A String array containing the value(s). Use a single element array for single values.
	*/
	public void setParam(String key, String[] value){
		params.put(key, value);
	}
	/**
	*Extend the Message parameters using key,value pairs from
	*the given HashMap.
	*This will overwrite existing keys if already set.
	*
	*@param map   A HashMap<String, String[]> containing the key,value
	*             pairs to extend the Message params by.
	*/
	public void setParams(HashMap<String, String[]> map){
		for (String key : map.keySet()){
			params.put(key, map.get(key));
		}
	}
	/**
	*@return A String array containing the Message parameter keys.
	*/
	public String[] getParamKeys(){
		return params.keySet().toArray(new String[0]);
	}
	/**
	*Get a required value from the Message params by key.
	*
	*@param key The key indexing the required value in Message params.
	*@return A String array containing the parameter value for the given key.
	*/
	public String[] getParam(String key){
		return params.get(key);
	}
	/**
	*Get all Message params
	*
	*@return A HashMap<String,String[]> containing all key,value pairs.
	*/
	public HashMap<String,String[]> getParams(){
		return params;
	}
}
