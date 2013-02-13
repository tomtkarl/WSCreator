package com.thoughtress.jsp.gen;

import java.util.ArrayList;
import java.util.HashMap;

/**
*A generic Message class to be subclassed by specialised Message types.
*/
public class MessagePart{
	public HashMap<String, String> attrs;
	public ArrayList<MessagePart> children;
	public String textValue;
	public String name;
	public MessagePart(){
		this.attrs = new HashMap<String, String>();
		this.children = new ArrayList<MessagePart>();
		this.textValue = "";
		this.name = "";
	}
	public boolean isText(){
		return this.textValue != "";
	}
	/**
	*Set a key,value pair in the Message parameters.
	*This will overwrite existing keys if already set.
	*
	*@param key   The key to be inserted.
	*@param value The String value for the given key.
	*/
	public void setAttr(String key, String value){
		this.attrs.put(key, value);
	}
	/**
	*Extend the Message parameters using key,value pairs from
	*the given HashMap.
	*This will overwrite existing keys if already set.
	*
	*@param map   A HashMap<String, String> containing the key,value
	*             pairs to extend the Message params by.
	*/
	public void setAttrs(HashMap<String, String> map){
		for (String key : map.keySet()){
			this.attrs.put(key, map.get(key));
		}
	}
	/**
	*@return A String array containing the Message parameter keys.
	*/
	public String[] getAttrKeys(){
		return this.attrs.keySet().toArray(new String[0]);
	}
	/**
	*Get a required value from the Message params by key.
	*
	*@param key The key indexing the required value in Message params.
	*@return The String parameter value for the given key.
	*/
	public String getAttr(String key){
		return this.attrs.get(key);
	}
	/**
	*Get all Message params
	*
	*@return A HashMap<String,String> containing all key,value pairs.
	*/
	public HashMap<String,String> getAttrs(){
		return this.attrs;
	}
}

