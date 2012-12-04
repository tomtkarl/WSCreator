package com.thoughtress.jsp.gen;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

public class MyFormatter implements MessageFormatter{
	@Override
	public Request parseToRequest(String data){
		Request req = new Request();
		//build header object
		//content type is critical for dyanamic soap version parsing!
		MimeHeaders headers = new MimeHeaders();
		headers.addHeader("Content-Type", "application/soap+xml");
		//transform xml string to InputStream
		InputStream xmlStream = new ByteArrayInputStream(data.getBytes());
		//Construct SOAPMessage from xml
		SOAPMessage message = null;
		try{
			message = MessageFactory.newInstance(SOAPConstants.DYNAMIC_SOAP_PROTOCOL).createMessage(headers,xmlStream);
		} catch (SOAPException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		if (message == null){
			return null;
		}
		//get Body of SOAP message
		SOAPBody body = null;
		try {
			body = message.getSOAPBody();
		} catch (SOAPException e) {
			e.printStackTrace();
		}
		if (body == null){
			return null;
		}
		SOAPBodyElement callMethodElement = getMethodElement(body);
		String callMethodName = getElementName(callMethodElement);
		req.setMethod(callMethodName);
		req.setParams(parseParams(callMethodElement));	
		return req;
	}
	
	private static SOAPBodyElement getMethodElement(SOAPBody body){
		Iterator elemIter = body.getChildElements();
		if (!elemIter.hasNext()){
			return null;
		}
		//first element in SOAPBody is method name
		return (SOAPBodyElement)elemIter.next();
	}
	private static HashMap<String,String> parseParams(SOAPBodyElement methodElem){
		Iterator paramIter = methodElem.getChildElements();
		//handle child elements (params) of method
		//Supports named params
		HashMap<String,String> params = new HashMap<String,String>(); 
		while (paramIter.hasNext()){
			SOAPBodyElement param = (SOAPBodyElement)paramIter.next();
			String paramName = getElementName(param);
			String paramValue = param.getTextContent();
			params.put(paramName, paramValue);
		}
		return params;
	}
	private static String getElementName(SOAPBodyElement elem){
		return elem.getElementName().getLocalName();
	}

	@Override
	public String parseToFormat(Response resp) {
		SOAPMessage soapMessage = null;
		try {
			soapMessage = buildMessage(resp);
		} catch (SOAPException e) {
			e.printStackTrace();
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			soapMessage.writeTo(out);
		} catch (SOAPException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String formatted = new String(out.toByteArray());
		return formatted;
	}
	
	private static SOAPMessage buildMessage(Response resp) throws SOAPException{
		SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();
	    SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
	    //HEADERS
	    /*
	    SOAPHeader soapHeader = soapEnvelope.getHeader();
	    soapHeader.addHeaderElement(soapEnvelope.createName(
	        "Signature", "SOAP-SEC", "http://schemas.xmlsoap.org/soap/security/2000-12"));
	    */
	    //BODY
	    SOAPBody soapBody = soapEnvelope.getBody();
	    //soapBody.addAttribute(soapEnvelope.createName("FooBar", "m",
	    //    "http://thoughtress.com/WebService"), "bar");
	    //first method return node
	    Name bodyName = soapEnvelope.createName(resp.getResponseName(), "m", "http://thoughtress.com/WebService");
	    SOAPBodyElement bodyElement = soapBody.addBodyElement(bodyName);
	    //key,value response pairs
	    for (String key : resp.getParamKeys()){
	    	SOAPElement child = bodyElement.addChildElement(soapEnvelope.createName(key, "m", "http://thoughtress.com/WebService"));
		    child.addTextNode(resp.getParam(key));
	    }
	    return soapMessage;
	}
}
