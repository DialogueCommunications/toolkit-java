/*
 * toolkit-java
 *
 * Copyright (C) 2012 Dialogue Communications Ltd.
 */
package net.dialogue.toolkit.sms;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Allows parsing incoming message reply POST requests.
 */
public class SmsReply implements Serializable {

    private String id;

    /**
     * Gets the unique message identifier (not that of the original submission).
     * @return The message identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique message identifier (not that of the original submission).
     * @param id The message identifier
     */
    public void setId(String id) {
        this.id = id;
    }

    private String sender;

    /**
     * Gets the original recipient of the submission (now the sender as it's a reply).
     * @return The original recipient
     */
    public String getSender() {
        return sender;
    }

    /**
     * Sets the original recipient of the submission (now the sender as it's a reply).
     * @param sender The original recipient
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    private String sessionId;

    /**
     * Gets the optional session identifier from submission. Can be used to associate unique submissions with replies, even if the recipient is the same.
     * @return The session identifier
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Sets the optional session identifier from submission. Can be used to associate unique submissions with replies, even if the recipient is the same.
     * @param sessionId The session identifier
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    private String hexMessage;

    /**
     * Gets the hex-encoded message text.
     * @return The hex-encoded message text
     */
    public String getHexMessage() {
        return hexMessage;
    }

    /**
     * Sets the hex-encoded message text.
     * @param hexMessage The hex-encoded message text
     */
    public void setHexMessage(String hexMessage) {
        this.hexMessage = hexMessage;
    }

    /**
     * Gets the message text.
     * @return The message text
     */
    public String getMessage() {
        int len = hexMessage.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexMessage.charAt(i), 16) << 4)
                    + Character.digit(hexMessage.charAt(i + 1), 16));
        }

        try {
            return new String(data, "ISO-8859-15");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private Date timestamp;

    /**
     * Gets the date and time the message was received.
     * @return The date and time the message was received
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the date and time the message was received.
     * @param timestamp The date and time the message was received
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    private String network;

    /**
     * Gets the mobile operator network name.
     * @return The mobile operator network name
     */
    public String getNetwork() {
        return network;
    }

    /**
     * Sets the mobile operator network name.
     * @param network The mobile operator network name
     */
    public void setNetwork(String network) {
        this.network = network;
    }

    /**
     * Returns a string representation of this SmsReply instance.
     * @return String representation
     */
    @Override
    public String toString() {
        return MessageFormat.format("Id: {0}, Sender: {1}, SessionId: {2}, HexMessage: {3}, Message: {4}, Timestamp: {5}, Network: {6}",
                id, sender, sessionId, hexMessage, getMessage(), timestamp, network);
    }

    /**
     * Parses a reply from the given string.
     * @param str Input string
     * @return The parsed SmsReply instance
     * @throws IOException if parsing the input string failed
     */
    public static SmsReply getInstance(String str) throws IOException {
        return getInstance(new StringReader(str));
    }

    /**
     * Parses a reply from the given reader.
     * @param reader Input reader
     * @return The parsed SmsReply instance
     * @throws IOException if parsing the input string failed
     */
    public static SmsReply getInstance(Reader reader) throws IOException {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(reader));

            return getInstance(document);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new IOException(e.toString());
        }
    }

    /**
     * Parses a reply from the given stream.
     * @param stream Input stream
     * @return The parsed SmsReply instance
     * @throws IOException if parsing the input string failed
     */
    public static SmsReply getInstance(InputStream stream) throws IOException {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(stream));

            return getInstance(document);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new IOException(e.toString());
        }
    }

    private static SmsReply getInstance(Document document) throws IOException {
        SmsReply reply = new SmsReply();
        Element element = document.getDocumentElement();

        reply.setId(element.getAttribute("X-E3-ID"));
        reply.setSender(element.getAttribute("X-E3-Originating-Address"));
        reply.setSessionId(element.getAttribute("X-E3-Session-ID"));
        reply.setHexMessage(element.getAttribute("X-E3-Hex-Message"));

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Europe/London"));
        try {
            reply.setTimestamp(format.parse(element.getAttribute("X-E3-Timestamp")));
        } catch (ParseException e) {
            throw new IOException(
                    "Failed to parse X-E3-Timestamp: " + element.getAttribute("X-E3-Timestamp")
            );
        }

        reply.setNetwork(element.getAttribute("X-E3-Network"));

        return reply;
    }
}
