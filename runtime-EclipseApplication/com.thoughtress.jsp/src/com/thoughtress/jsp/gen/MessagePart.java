package com.thoughtress.jsp.gen;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A generic Message class to be subclassed by specialised Message types.
 */
public class MessagePart<T> {
    public HashMap<String, String> attrs;
    public HashMap<String, String> options;
    public ArrayList<MessagePart<?>> children;
    private T value;
    public String name;

    public MessagePart(String name) {
        this.attrs = new HashMap<String, String>();
        this.options = new HashMap<String, String>();
        this.children = new ArrayList<MessagePart<?>>();
        this.value = null;
        this.name = name;
    }

    public boolean isText() {
        return this.value instanceof String;
    }
    public T getValueAsType(){
        return this.value;
    }
    public String getValue(){
        return (this.value == null)? "" : this.value.toString();
    }
    public void setValue(T value){
        this.value = value;
    }

    /**
     * Get a named child of the MessagePart
     * 
     * @param name The name of the child to search for.
     * @return A MessagePart matching the given name if it exists. Otherwise null is returned.
     */
    public MessagePart<?> getChild(String name) {
        for (MessagePart<?> child : this.children) {
            if (child.name.equals(name)) {
                return child;
            }
        }
        return null;
    }

    /**
     * Set a key,value pair in the MessagePart attributes. This will overwrite existing keys if already set.
     * 
     * @param key The key to be inserted.
     * @param value The String value for the given key.
     */
    public void setAttr(String key, String value) {
        this.attrs.put(key, value);
    }

    /**
     * Extend the MessagePart attributes using key,value pairs from the given HashMap. This will overwrite existing keys
     * if already set.
     * 
     * @param map A HashMap<String, String> containing the key,value pairs to extend the Message params by.
     */
    public void setAttrs(HashMap<String, String> map) {
        for (String key : map.keySet()) {
            this.attrs.put(key, map.get(key));
        }
    }

    /**
     * @return A String array containing the MessagePart attribute keys.
     */
    public String[] getAttrKeys() {
        return this.attrs.keySet().toArray(new String[0]);
    }

    /**
     * Get a required value from the MessagePart attributes by key.
     * 
     * @param key The key indexing the required value in MessagePart attributes.
     * @return The String attribute value for the given key.
     */
    public String getAttr(String key) {
        return this.attrs.get(key);
    }

    /**
     * Set a key,value pair in the MessagePart options. This will overwrite existing keys if already set.
     * 
     * @param key The key to be inserted.
     * @param value The String value for the given key.
     */
    public void setOption(String key, String value) {
        this.options.put(key, value);
    }

    /**
     * Extend the MessagePart options using key,value pairs from the given HashMap. This will overwrite existing keys if
     * already set.
     * 
     * @param map A HashMap<String, String> containing the key,value pairs to extend the MessagePart options by.
     */
    public void setOptions(HashMap<String, String> map) {
        for (String key : map.keySet()) {
            this.options.put(key, map.get(key));
        }
    }

    /**
     * @return A String array containing the MessagePart options keys.
     */
    public String[] getOptionKeys() {
        return this.options.keySet().toArray(new String[0]);
    }

    /**
     * Get a required value from the MessagePart options by key.
     * 
     * @param key The key indexing the required value in MessagePart options.
     * @return The String parameter value for the given key.
     */
    public String getOption(String key) {
        return this.options.get(key);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Name: " + this.name);
        sb.append("\n>>Attrs: " + this.attrs);
        sb.append("\n>>Options: " + this.options);
        sb.append("\n>>Value: " + this.value);
        for (MessagePart<?> child : this.children) {
            sb.append("\n\n" + child.toString());
        }
        return sb.toString();
    }
}
