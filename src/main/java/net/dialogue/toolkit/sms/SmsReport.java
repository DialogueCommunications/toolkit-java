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
 * Allows parsing incoming message report POST requests.
 */
public class SmsReport implements Serializable {

    private String id;

    /**
     * Gets the unique delivery report identifier (not that of the original submission).
     * @return The delivery report identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique delivery report identifier (not that of the original submission).
     * @param id The delivery report identifier
     */
    public void setId(String id) {
        this.id = id;
    }

    private String recipient;

    /**
     * Gets the original recipient of the submission.
     * @return The original recipient
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Sets the original recipient of the submission.
     * @param recipient The original recipient
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    private String deliveryReport;

    /**
     * Gets the delivery report value, 00 to 1F indicating a successful delivery, 20 to 3F indicating a temporary error and 40 to 7F indicating a permanent error.
     * @return The delivery report value
     */
    public String getDeliveryReport() {
        return deliveryReport;
    }

    /**
     * Sets the delivery report value, 00 to 1F indicating a successful delivery, 20 to 3F indicating a temporary error and 40 to 7F indicating a permanent error.
     * @param deliveryReport The delivery report value
     */
    public void setDeliveryReport(String deliveryReport) {
        this.deliveryReport = deliveryReport;
    }

    private String userKey;

    /**
     * Gets the optional user key parameter from the message submission. Can be used to associate unique submissions with reports, even if the recipient is the same.
     * @return The user key parameter
     */
    public String getUserKey() {
        return userKey;
    }

    /**
     * Sets the optional user key parameter from the message submission. Can be used to associate unique submissions with reports, even if the recipient is the same.
     * @param userKey The user key parameter
     */
    public void setUserKey(String userKey) {
        this.userKey = userKey;
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
     * Gets the delivery report value classified into State.Delivered, State.TemporaryError or State.PermanentError.
     * @return The delivery report state
     */
    public State getState() {
        if (deliveryReport == null || deliveryReport.length() == 0)
            return State.Undefined;
        if (deliveryReport.startsWith("0") || deliveryReport.startsWith("1"))
            return State.Delivered;
        if (deliveryReport.startsWith("2") || deliveryReport.startsWith("3"))
            return State.TemporaryError;
        if (deliveryReport.startsWith("4") || deliveryReport.startsWith("5") ||
                deliveryReport.startsWith("6") || deliveryReport.startsWith("7"))
            return State.PermanentError;
        throw new IllegalStateException(
                "Unknown delivery report value: " + deliveryReport
        );
    }

    /**
     * Checks if the delivery was successful.
     * @return True if the delivery report state is State.Delivered, false otherwise.
     */
    public boolean isSuccessful() {
        return getState().equals(State.Delivered);
    }

    /**
     * Returns a string representation of this SmsReport instance.
     * @return String representation
     */
    @Override
    public String toString() {
        return MessageFormat.format("Id: {0}, Recipient: {1}, DeliveryReport: {2}, UserKey: {3}, Timestamp: {4}, Network: {5}",
                id, recipient, deliveryReport, userKey, timestamp, network);
    }

    /**
     * Parses a report from the given string.
     * @param str Input string
     * @return The parsed SmsReport instance
     * @throws java.io.IOException if parsing the input string failed
     */
    public static SmsReport getInstance(String str) throws IOException {
        return getInstance(new StringReader(str));
    }

    /**
     * Parses a report from the given reader.
     * @param reader Input reader
     * @return The parsed SmsReport instance
     * @throws IOException  if parsing the input string failed
     */
    public static SmsReport getInstance(Reader reader) throws IOException {
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
     * Parses a report from the given stream.
     * @param stream Input stream
     * @return The parsed SmsReport instance
     * @throws IOException  if parsing the input string failed
     */
    public static SmsReport getInstance(InputStream stream) throws IOException {
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

    private static SmsReport getInstance(Document document) throws IOException {
        SmsReport report = new SmsReport();
        Element element = document.getDocumentElement();

        report.setId(element.getAttribute("X-E3-ID"));
        report.setRecipient(element.getAttribute("X-E3-Recipients"));
        report.setDeliveryReport(element.getAttribute("X-E3-Delivery-Report"));
        report.setUserKey(element.getAttribute("X-E3-User-Key"));

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Europe/London"));
        try {
            report.setTimestamp(format.parse(element.getAttribute("X-E3-Timestamp")));
        } catch (ParseException e) {
            throw new IOException(
                    "Failed to parse X-E3-Timestamp: " + element.getAttribute("X-E3-Timestamp")
            );
        }

        report.setNetwork(element.getAttribute("X-E3-Network"));

        return report;
    }
}
