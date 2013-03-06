package com.thoughtress.jsp.gen;

//Start of user code imports
import static org.w3c.dom.Node.ELEMENT_NODE;
import static org.w3c.dom.Node.TEXT_NODE;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
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

//End of user code
/**
 * A Message Formatter for the web service.<br />
 * Supports the SOAP+XML MIME types.
 */
public class SOAPFormatter extends MessageFormatter {
    // Start of user code classvars
    private static String[] supportedTypes = { "text/xml", "application/soap+xml" };

    // End of user code
    public static boolean match(HttpServletRequest req) {
        // Start of user code match
        for (String type : supportedTypes) {
            if (req.getContentType() != null && req.getContentType().equals(type)) {
                return true;
            }
        }
        return false;
        // End of user code
    }

    @Override
    public MessagePart parseToRequest(String data, HttpServletRequest request)
    // Start of user code parseToRequest
            throws UserServiceException {
        MimeHeaders headers = new MimeHeaders();
        headers.addHeader("Content-Type", request.getContentType());
        InputStream xmlStream = new ByteArrayInputStream(data.getBytes());
        // Construct SOAPMessage from xml
        SOAPMessage message = null;
        try {
            message = MessageFactory.newInstance(SOAPConstants.DYNAMIC_SOAP_PROTOCOL)
                    .createMessage(headers, xmlStream);
        } catch (SOAPException e) {
            e.printStackTrace();
            throw new UserServiceException(500, "Error Whilst Parsing Request");
        } catch (IOException e) {
            e.printStackTrace();
            throw new UserServiceException(500, "Error Whilst Parsing Request");
        }
        // get Body of SOAP message
        SOAPBody body = null;
        try {
            body = message.getSOAPBody();
        } catch (SOAPException e) {
            e.printStackTrace();
            throw new UserServiceException(500, "Error Whilst Parsing Request");
        }
        MessagePart msg = buildMessageRoot(body);
        return msg;
        // End of user code
    }

    /**
     * @throws UserServiceException
     * @generated
     */
    private static MessagePart buildMessageRoot(SOAPBody body) throws UserServiceException {
        Iterator<Node> elemIter = body.getChildElements();
        if (elemIter.hasNext()) {
            Node next = elemIter.next();
            return buildMessagePart((SOAPElement) next);
        } else {
            throw new UserServiceException(500, "Error Whilst Parsing Request");
        }
    }

    /**
     * @generated
     */
    private static MessagePart buildMessagePart(SOAPElement elem) {
        MessagePart<String> part = new MessagePart<String>(getElementName(elem));
        Iterator<String> nsIter = elem.getNamespacePrefixes();
        while (nsIter.hasNext()) {
            String nsPrefix = nsIter.next();
            part.setOption("nsPrefix", nsPrefix);
            part.setOption("nsURI", elem.getNamespaceURI(nsPrefix));
            break;
        }
        Iterator<Name> attrIter = elem.getAllAttributes();
        while (attrIter.hasNext()) {
            Name nextAttr = attrIter.next();
            part.setAttr(nextAttr.getLocalName(), elem.getAttributeValue(nextAttr));
        }
        System.out.println("buildMessagePart> " + part.name + "::" + part.attrs);
        Iterator<Node> elemIter = elem.getChildElements();
        while (elemIter.hasNext()) {
            Node nextElem = elemIter.next();
            short type = nextElem.getNodeType();
            if (type == ELEMENT_NODE) {
                part.children.add(buildMessagePart((SOAPElement) nextElem));
            } else if (type == TEXT_NODE) {
                part.setValue(((Text)nextElem).getTextContent());
            }
        }
        return part;
    }

    /**
     * @generated
     */
    private static String getElementName(SOAPElement elem) {
        return elem.getElementName().getLocalName();
    }

    /**
     * @throws UserServiceException
     * @generated
     */
    @Override
    public String parseToFormat(MessagePart resp) throws UserServiceException {
        // Start of user code parseToFormat
        SOAPMessage soapMessage = null;
        try {
            soapMessage = buildSOAPMessageRoot(resp);
        } catch (SOAPException e) {
            e.printStackTrace();
            throw new UserServiceException(500, "Error Whilst Building Response");
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            soapMessage.writeTo(out);
        } catch (SOAPException e) {
            e.printStackTrace();
            throw new UserServiceException(500, "Error Whilst Building Response");
        } catch (IOException e) {
            e.printStackTrace();
            throw new UserServiceException(500, "Error Whilst Building Response");
        }
        try {
            return new String(out.toByteArray(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new UserServiceException(500, "Error Whilst Building Response");
        }
        // End of user code
    }

    /**
     * @generated
     */
    private static SOAPMessage buildSOAPMessageRoot(MessagePart root) throws SOAPException {
        final SOAPFactory soapFactory = SOAPFactory.newInstance();
        SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
        soapMessage.setProperty(SOAPMessage.WRITE_XML_DECLARATION, "true");
        SOAPPart soapPart = soapMessage.getSOAPPart();
        final SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
        SOAPBody soapBody = soapEnvelope.getBody();
        class NodeBuilder {
            public SOAPElement buildNode(MessagePart<?> part) throws SOAPException {
                Name partName;
                if (part.options.containsKey("nsURI") && part.options.containsKey("nsPrefix")) {
                    partName = soapEnvelope.createName(part.name, part.options.get("nsPrefix"),
                            part.options.get("nsURI"));
                } else {
                    partName = soapEnvelope.createName(part.name);
                }
                SOAPElement elem = soapFactory.createElement(partName);
                for (String key : part.getAttrKeys()) {
                    elem.addAttribute(soapEnvelope.createName(key), part.getAttr(key));
                }
                for (MessagePart<?> childPart : part.children) {
                    elem.addChildElement(buildNode(childPart));
                }
                if (part.isText()) {
                    elem.addTextNode(part.getValue());
                }
                return elem;
            }
        }
        NodeBuilder nlb = new NodeBuilder();
        soapBody.addChildElement(nlb.buildNode(root));
        return soapMessage;
    }

    @Override
    public String buildError(UserServiceException ue) throws Exception {
        // Start of user code buildError
        SOAPMessage soapMessage = null;
        try {
            soapMessage = MessageFactory.newInstance().createMessage();
            SOAPPart soapPart = soapMessage.getSOAPPart();
            SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
            SOAPBody soapBody = soapEnvelope.getBody();
            Name faultCode;
            if (ue.code >= 400 && ue.code < 500) {
                faultCode = soapEnvelope.createName("Client", null,
                        SOAPConstants.URI_NS_SOAP_ENVELOPE);

            } else {
                faultCode = soapEnvelope.createName("Server", null,
                        SOAPConstants.URI_NS_SOAP_ENVELOPE);
            }
            String faultString = ue.getMessage();
            soapBody.addFault(faultCode, faultString);
        } catch (SOAPException e) {
            e.printStackTrace();
            throw new UserServiceException(500, "Error Whilst Building Error");
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            soapMessage.writeTo(out);
        } catch (SOAPException e) {
            e.printStackTrace();
            throw new UserServiceException(500, "Error Whilst Building Error");
        } catch (IOException e) {
            e.printStackTrace();
            throw new UserServiceException(500, "Error Whilst Building Error");
        }
        String formatted = new String(out.toByteArray());
        return formatted;
        // End of user code
    }
}
