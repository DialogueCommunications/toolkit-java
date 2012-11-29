/*
 * toolkit-java
 *
 * Copyright (C) 2012 Dialogue Communications Ltd.
 */

package net.dialogue.toolkit.sms;

import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.support.AbstractMarshaller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.LexicalHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;

class Marshaller extends AbstractMarshaller {

    public static final String DEFAULT_ENCODING = "UTF-8";

    private String encoding;

    public String getEncoding() {
        return encoding != null ? encoding : DEFAULT_ENCODING;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    //
    // Marshalling
    //

    @Override
    protected void marshalOutputStream(Object o, OutputStream outputStream) throws XmlMappingException, IOException {
        marshalWriter(o, new OutputStreamWriter(outputStream, getEncoding()));
    }

    @Override
    protected void marshalWriter(Object o, Writer writer) throws XmlMappingException, IOException {
        try {
            DocumentBuilder builder = newDocumentBuilder();
            Document document = builder.newDocument();
            marshalDomNode(o, document);
            Properties p = new Properties();
            p.setProperty(OutputKeys.ENCODING, getEncoding());
            Transformer t = newTransformer();
            t.setOutputProperties(p);
            t.transform(
                    new DOMSource(document),
                    new StreamResult(writer)
            );
        } catch (ParserConfigurationException e) {
            throw new IOException(e.toString());
        } catch (TransformerConfigurationException e) {
            throw new IOException(e.toString());
        } catch (TransformerException e) {
            throw new IOException(e.toString());
        }
    }

    @Override
    protected void marshalDomNode(Object o, Node node) throws XmlMappingException {

        SendSmsRequest request = (SendSmsRequest) o;
        Document document = node instanceof Document ?
                (Document) node : node.getOwnerDocument();

        Element sendSmsRequest = document.createElement("sendSmsRequest");
        node.appendChild(sendSmsRequest);

        for (String message : request.getMessages()) {
            Element el = document.createElement("X-E3-Message");
            el.appendChild(document.createTextNode(message));
            sendSmsRequest.appendChild(el);
        }

        for (String recipient : request.getRecipients()) {
            Element el = document.createElement("X-E3-Recipients");
            el.appendChild(document.createTextNode(recipient));
            sendSmsRequest.appendChild(el);
        }

        for (Map.Entry<String, String> entry : request.entrySet()) {
            Element el = document.createElement(entry.getKey());
            el.appendChild(document.createTextNode(entry.getValue()));
            sendSmsRequest.appendChild(el);
        }
    }

    @Override
    protected void marshalXmlEventWriter(Object o, XMLEventWriter xmlEventWriter) throws XmlMappingException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void marshalXmlStreamWriter(Object o, XMLStreamWriter xmlStreamWriter) throws XmlMappingException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void marshalSaxHandlers(Object o, ContentHandler contentHandler, LexicalHandler lexicalHandler) throws XmlMappingException {
        throw new UnsupportedOperationException();
    }

    //
    // Unmarshalling
    //

    @Override
    protected Object unmarshalInputStream(InputStream inputStream) throws XmlMappingException, IOException {
        try {
            DocumentBuilder builder = newDocumentBuilder();
            Document document = builder.parse(inputStream);
            return unmarshalDomNode(document);
        } catch (ParserConfigurationException e) {
            throw new IOException(e.toString());
        } catch (SAXException e) {
            throw new IOException(e.toString());
        }
    }

    @Override
    protected Object unmarshalDomNode(Node node) throws XmlMappingException {

        if(node instanceof Document)
            node = ((Document)node).getDocumentElement();

        if(!"sendSmsResponse".equals(node.getNodeName()))
            return null;

        SendSmsResponse response = new SendSmsResponse();
        List<Sms> messages = response.getMessages();

        NodeList nodeList = ((Element)node).getElementsByTagName("sms");
        for(int n = 0; n < nodeList.getLength(); n++) {

            Sms sms = new Sms();
            Element el = (Element)nodeList.item(n);

            if(el.hasAttribute("X-E3-ID")) {
                sms.setId(el.getAttribute("X-E3-ID"));
            }

            if(el.hasAttribute("X-E3-Recipients")) {
                sms.setRecipient(el.getAttribute("X-E3-Recipients"));
            }

            if(el.hasAttribute("X-E3-Submission-Report")) {
                sms.setSubmissionReport(el.getAttribute("X-E3-Submission-Report"));
            }

            if(el.hasAttribute("X-E3-Error-Description")) {
                sms.setErrorDescription(el.getAttribute("X-E3-Error-Description"));
            }

            messages.add(sms);
        }

        return response;
    }

    @Override
    protected Object unmarshalReader(Reader reader) throws XmlMappingException, IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Object unmarshalXmlEventReader(XMLEventReader xmlEventReader) throws XmlMappingException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Object unmarshalXmlStreamReader(XMLStreamReader xmlStreamReader) throws XmlMappingException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Object unmarshalSaxReader(XMLReader xmlReader, InputSource inputSource) throws XmlMappingException, IOException {
        throw new UnsupportedOperationException();
    }

    public boolean supports(Class<?> aClass) {
        return SendSmsRequest.class.equals(aClass) || SendSmsResponse.class.equals(aClass);
    }

    //
    // Factories
    //

    protected DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        return factory.newDocumentBuilder();
    }

    protected Transformer newTransformer() throws TransformerConfigurationException {
        TransformerFactory factory = TransformerFactory.newInstance();
        return factory.newTransformer();
    }
}
