package com.thoughtress.jsp.gen;

import java.util.ArrayList;
import java.util.HashMap;

/**
*A generic Message class to be subclassed by specialised Message types.
*/
public class MessagePart{
	public HashMap<String, String> attrs;
	public HashMap<String, String> options;
	public ArrayList<MessagePart> children;
	public String textValue;
	public String name;
	public MessagePart(){
		this.attrs = new HashMap<String, String>();
		this.options = new HashMap<String, String>();
		this.children = new ArrayList<MessagePart>();
		this.textValue = "";
		this.name = "";
	}
	public boolean isText(){
		return this.textValue != "";
	}
	/**
	*Set a key,value pair in the MessagePart attributes.
	*This will overwrite existing keys if already set.
	*
	*@param key   The key to be inserted.
	*@param value The String value for the given key.
	*/
	public void setAttr(String key, String value){
		this.attrs.put(key, value);
	}
	/**
	*Extend the MessagePart attributes using key,value pairs from
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
	*@return A String array containing the MessagePart attribute keys.
	*/
	public String[] getAttrKeys(){
		return this.attrs.keySet().toArray(new String[0]);
	}
	/**
	*Get a required value from the MessagePart attributes by key.
	*
	*@param key The key indexing the required value in MessagePart attributes.
	*@return The String attribute value for the given key.
	*/
	public String getAttr(String key){
		return this.attrs.get(key);
	}
	/**
	*Set a key,value pair in the MessagePart options.
	*This will overwrite existing keys if already set.
	*
	*@param key   The key to be inserted.
	*@param value The String value for the given key.
	*/
	public void setOption(String key, String value){
		this.options.put(key, value);
	}
	/**
	*Extend the MessagePart options using key,value pairs from
	*the given HashMap.
	*This will overwrite existing keys if already set.
	*
	*@param map   A HashMap<String, String> containing the key,value
	*             pairs to extend the MessagePart options by.
	*/
	public void setOptions(HashMap<String, String> map){
		for (String key : map.keySet()){
			this.options.put(key, map.get(key));
		}
	}
	/**
	*@return A String array containing the MessagePart options keys.
	*/
	public String[] getOptionKeys(){
		return this.options.keySet().toArray(new String[0]);
	}
	/**
	*Get a required value from the MessagePart options by key.
	*
	*@param key The key indexing the required value in MessagePart options.
	*@return The String parameter value for the given key.
	*/
	public String getOption(String key){
		return this.options.get(key);
	}
}