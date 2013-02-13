package com.thoughtress.jsp.gen;

import static org.w3c.dom.Node.ELEMENT_NODE;
import static org.w3c.dom.Node.TEXT_NODE;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.Name;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.w3c.dom.Text;

/**
*A Message Formatter for the web service.<br />
*Supports the SOAP+XML MIME types.
*/
public class MyFormatter extends MessageFormatter{
	
	private static String[] supportedTypes = {"text/xml", "application/soap+xml"};
	
	public static String[] getTypes(){
		return supportedTypes;
	}
	
	@Override
	public MessagePart parseToRequest(String data){
		MimeHeaders headers = new MimeHeaders();
		headers.addHeader("Content-Type", "application/soap+xml");
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
		MessagePart msg = buildMessageRoot(body);
		return msg;
	}
	
	private static MessagePart buildMessageRoot(SOAPBody body){
		Iterator<Node> elemIter = body.getChildElements();
		if (elemIter.hasNext()){
			Node next = elemIter.next();
			return buildMessagePart((SOAPElement)next);
		} else {
			//TODO: Send error - no root!
			return null;
		}
	}
	
	private static MessagePart buildMessagePart(SOAPElement elem){
		MessagePart part = new MessagePart();
		part.name = getElementName(elem);
		Iterator<Name> attrIter = elem.getAllAttributes();
		while (attrIter.hasNext()){
			Name nextAttr = attrIter.next();
			part.setAttr(nextAttr.getLocalName(), elem.getAttributeValue(nextAttr));
		}
		System.out.println("buildMessagePart> " + part.name +"::"+ part.attrs);
		Iterator<Node> elemIter = elem.getChildElements();
		while (elemIter.hasNext()){
			Node nextElem = elemIter.next();
			short type = nextElem.getNodeType();
			if (type == ELEMENT_NODE){
				part.children.add(buildMessagePart((SOAPElement)nextElem));
			} else if (type == TEXT_NODE){
				part.textValue = ((Text)nextElem).getTextContent();
				System.out.println("buildMessagePart:: " + part.textValue);
			}
		}
		return part;
	}

	private static String getElementName(SOAPElement elem){
		return elem.getElementName().getLocalName();
	}

	@Override
	public String parseToFormat(MessagePart resp) {
		SOAPMessage soapMessage = null;
		try {
			soapMessage = buildSOAPMessageRoot(resp);
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

	private static SOAPMessage buildSOAPMessageRoot(MessagePart root) throws SOAPException{
		final SOAPFactory soapFactory = SOAPFactory.newInstance();
		SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();
	    final SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
	    SOAPBody soapBody = soapEnvelope.getBody();	    
	    class NodeBuilder{
	    	public SOAPElement buildNode(MessagePart part) throws SOAPException{
	    		System.out.println("buildNode> " + part.name + ": " + part.isText());
	    	    Name partName = soapEnvelope.createName(part.name);
	    	    SOAPElement elem = soapFactory.createElement(partName);
	    	    for (String key : part.getAttrKeys()){
	    	    	elem.addAttribute(soapEnvelope.createName(key),
	    	    					  part.getAttr(key));
	    	    }
	    	    for (MessagePart childPart : part.children){
	    	    	elem.addChildElement(buildNode(childPart));
	    	    }
    		    if (part.isText()){
    		    	elem.addTextNode(part.textValue);
    		    } 
	    	    return elem;
	    	}
	    }
	    NodeBuilder nlb = new NodeBuilder();
	    soapBody.addChildElement(nlb.buildNode(root));
	    return soapMessage;
	}
}
