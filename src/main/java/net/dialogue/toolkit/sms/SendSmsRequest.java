package net.dialogue.toolkit.sms;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: oliver
 * Date: 06/11/12
 * Time: 15:25
 */
@XStreamAlias("sendSmsRequest")
public class SendSmsRequest extends LinkedHashMap<String, String> implements Serializable {

    private List<String> messages;
    private List<String> recipients;

    /**
     * Constructs a SendSmsRequest object without message or recipient.
     */
    public SendSmsRequest() {
        messages = new ArrayList<String>();
        recipients = new ArrayList<String>();
    }

    /**
     * Constructs a SendSmsRequest object with a single message and single recipient.
     *
     * @param message   The message
     * @param recipient The recipient
     * @throws IllegalArgumentException Thrown if message or recipient is null or empty.
     */
    public SendSmsRequest(String message, String recipient) {
        this(Arrays.asList(message), Arrays.asList(recipient));
    }

    /**
     * Constructs a SendSmsRequest object with a single message and a list of recipients.
     *
     * @param message    The message
     * @param recipients The recipients list
     * @throws IllegalArgumentException Thrown if message or recipients is null or empty.
     */
    public SendSmsRequest(String message, List<String> recipients) {
        this(Arrays.asList(message), recipients);
    }

    /**
     * Constructs a SendSmsRequest object with a list of messages and single recipient.
     *
     * @param messages  The messages list
     * @param recipient The recipient
     * @throws IllegalArgumentException Thrown if messages or recipient is null or empty.
     */
    public SendSmsRequest(List<String> messages, String recipient) {
        this(messages, Arrays.asList(recipient));
    }

    /**
     * Constructs a SendSmsRequest object with a list of messages and a list of recipients.
     *
     * @param messages   The messages list
     * @param recipients The recipients list
     * @throws IllegalArgumentException Thrown if messages or recipients is null or empty.
     */
    public SendSmsRequest(List<String> messages, List<String> recipients) {
        setMessages(messages);
        setRecipients(recipients);
    }

    /**
     * Gets the Messages property
     *
     * @return The messages list
     * @see SendSmsRequest#setMessages(java.util.List)
     */
    public List<String> getMessages() {
        return messages;
    }

    /**
     * Sets the Messages property.
     *
     * @param messages The messages list
     * @throws IllegalArgumentException Thrown if messages is null or empty.
     */
    public void setMessages(List<String> messages) {
        if (messages == null || messages.isEmpty()) {
            throw new IllegalArgumentException(
                    "No messages provided."
            );
        }

        if (messages.size() == 1 && (messages.get(0) == null || messages.get(0).length() == 0)) {
            throw new IllegalArgumentException(
                    "No message provided."
            );
        }

        this.messages = messages;
    }

    /**
     * Replaces the messages list by a single message. Setter only; to get the message(s) use getMessages.
     *
     * @param message The message
     * @throws IllegalArgumentException Thrown if message is null or empty.
     */
    public void setMessage(String message) {
        setMessages(Arrays.asList(message));
    }

    /**
     * Gets the Recipients property.
     *
     * @return The recipients list
     * @see SendSmsRequest#setRecipients(java.util.List)
     */
    public List<String> getRecipients() {
        return recipients;
    }

    /**
     * Sets the Recipients property.
     *
     * @param recipients The recipients list
     * @throws IllegalArgumentException Thrown if recipients is null or empty.
     */
    public void setRecipients(List<String> recipients) {
        if (recipients == null || recipients.isEmpty()) {
            throw new IllegalArgumentException(
                    "No recipients provided."
            );
        }

        if (recipients.size() == 1 && (recipients.get(0) == null || recipients.get(0).length() == 0)) {
            throw new IllegalArgumentException(
                    "No recipient provided."
            );
        }

        this.recipients = recipients;
    }

    /**
     * Replaces the recipients list by a single recipient. Setter only; to get the recipient(s) use getRecipients.
     *
     * @param recipient The recipient
     * @throws IllegalArgumentException Thrown if recipient is null or empty.
     */
    public void setRecipient(String recipient) {
        setRecipients(Arrays.asList(recipient));
    }

    /**
     * Do not use this property unless instructed.
     *
     * @return The sender
     */
    public String getSender() {
        if (containsKey("X-E3-Originating-Address"))
            return get("X-E3-Originating-Address");
        else
            return null;
    }

    /**
     * Do not use this property unless instructed.
     *
     * @param sender The sender
     */
    public void setSender(String sender) {
        if (sender != null)
            put("X-E3-Originating-Address", sender);
        else
            remove("X-E3-Originating-Address");
    }

    /**
     * Gets the ConcatenationLimit property.
     *
     * @return The concatenation limit or null if the property is disabled
     * @see SendSmsRequest#setConcatenationLimit(Integer)
     */
    public Integer getConcatenationLimit() {
        if (containsKey("X-E3-Concatenation-Limit"))
            return Integer.parseInt(get("X-E3-Concatenation-Limit"));
        else
            return null;
    }

    /**
     * Sets the ConcatenationLimit property.
     *
     * By setting the ConcatenationLimit property, you enable long message concatenation. If the length of any message exceeds the default character limit the messaging gateway will send multiple concatenated messages up to the concatenation limit, after which the text is truncated. Concatenation works by splitting and wrapping the message in packets or fragments, each fragment being prefixed by the current fragment number and the total number of fragments. This allows the phone to know when it received all fragments and how to reassemble the message even if fragments arrive out of order. The concatenation limit refers to the maximum number of message fragments, not the number of characters, i.e. a concatenation limit of "3" means no more than 3 SMS messages will be sent, which in total can contain up to 459 GSM-compatible characters. The concatenation overhead is 7 characters, so 160 - 7 = 153 available characters per fragment x 3 = 459 total characters. In the response you will find one Sms object for each message segment.
     *
     * @param concatenationLimit The concatenation limit or null to disable the property
     */
    public void setConcatenationLimit(Integer concatenationLimit) {
        if (concatenationLimit != null)
            put("X-E3-Concatenation-Limit", Integer.toString(concatenationLimit));
        else
            remove("X-E3-Concatenation-Limit");
    }

    /**
     * Gets the ScheduleFor property.
     *
     * @return The schedule date and time or null if scheduling is disabled
     * @see SendSmsRequest#setScheduleFor(java.util.Date)
     */
    public Date getScheduleFor() {
        if (containsKey("X-E3-Schedule-For")) {
            try {
                DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                format.setTimeZone(TimeZone.getTimeZone("Europe/London"));
                return format.parse(get("X-E3-Schedule-For"));
            } catch (ParseException e) {
                throw new IllegalStateException(e);
            }
        } else {
            return null;
        }
    }

    /**
     * Sets the ScheduleFor property. Use the ScheduleFor property to delay sending messages until the specified date and time. The property is of type DateTime and must be an instance of DateTimeKind.Utc so that time zone conversion is possible. If the schedule date/time is in the past the message is sent immediately.
     *
     * @param scheduleFor The schedule date and time or null to disable scheduling
     */
    public void setScheduleFor(Date scheduleFor) {
        if (scheduleFor != null) {
            DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            format.setTimeZone(TimeZone.getTimeZone("Europe/London"));
            put("X-E3-Schedule-For", format.format(scheduleFor));
        } else {
            remove("X-E3-Schedule-For");
        }
    }

    /**
     * Gets the ConfirmDelivery property.
     *
     * @return True if confirmation of delivery is enabled, false if confirmation of delivery is disabled or null if the property is disabled
     * @see SendSmsRequest#setConfirmDelivery(Boolean)
     */
    public Boolean getConfirmDelivery() {
        if (containsKey("X-E3-Confirm-Delivery"))
            return "on".equals(get("X-E3-Confirm-Delivery"));
        else
            return null;
    }

    /**
     * Sets the ConfirmDelivery property. The property ConfirmDelivery can be set to true to enable tracking of message delivery. If you enable ConfirmDelivery you must also set the ReplyPath property pointing to an HTTP event handler that you implement. Optionally, set UserKey to an arbitrary, custom identifier, which will be posted back to you. You can use this to associate a message submission with a delivery report.
     *
     * @param confirmDelivery True to enable confirmation of delivery, false to disable confirmation of delivery or null to disable the property
     */
    public void setConfirmDelivery(Boolean confirmDelivery) {
        if (confirmDelivery != null)
            put("X-E3-Confirm-Delivery", confirmDelivery ? "on" : "off");
        else
            remove("X-E3-Confirm-Delivery");
    }

    /**
     * Gets the ReplyPath property.
     *
     * @return The reply path or null if the property is disabled
     * @see SendSmsRequest#setReplyPath(String)
     */
    public String getReplyPath() {
        if (containsKey("X-E3-Reply-Path"))
            return get("X-E3-Reply-Path");
        else
            return null;
    }

    /**
     * Sets the ReplyPath property.
     *
     * @param replyPath The reply path or null to disable the property
     * @see SendSmsRequest#setConfirmDelivery(Boolean)
     */
    public void setReplyPath(String replyPath) {
        if (replyPath != null)
            put("X-E3-Reply-Path", replyPath);
        else
            remove("X-E3-Reply-Path");
    }

    /**
     * Gets the UserKey property.
     *
     * @return The user key or null if the property is disabled
     * @see SendSmsRequest#setUserKey(String)
     */
    public String getUserKey() {
        if (containsKey("X-E3-User-Key"))
            return get("X-E3-User-Key");
        else
            return null;
    }

    /**
     * Sets the UserKey property.
     *
     * @param userKey The user key or null to disable the property
     * @see SendSmsRequest#setConfirmDelivery(Boolean)
     */
    public void setUserKey(String userKey) {
        if (userKey != null)
            put("X-E3-User-Key", userKey);
        else
            remove("X-E3-User-Key");
    }

    /**
     * Gets the SessionReplyPath property.
     *
     * @return The session reply path or null if the property is disabled
     * @see SendSmsRequest#setSessionReplyPath(String)
     */
    public String getSessionReplyPath() {
        if (containsKey("X-E3-Session-Reply-Path"))
            return get("X-E3-Session-Reply-Path");
        else
            return null;
    }

    /**
     * Sets the SessionReplyPath property. The property SessionReplyPath points to an HTTP event handler that you have implemented. The handler is invoked if the recipient replies to the message they have received. Optionally you can specify the SessionId property, which will be posted back to you. You can use this to associate a message submission with a reply.
     *
     * @param sessionReplyPath The session reply path or null to disable the property
     */
    public void setSessionReplyPath(String sessionReplyPath) {
        if (sessionReplyPath != null)
            put("X-E3-Session-Reply-Path", sessionReplyPath);
        else
            remove("X-E3-Session-Reply-Path");
    }

    /**
     * Gets the SessionId property.
     *
     * @return The session identifier or null if the property is disabled
     * @see SendSmsRequest#setSessionId(String)
     */
    public String getSessionId() {
        if (containsKey("X-E3-Session-ID"))
            return get("X-E3-Session-ID");
        else
            return null;
    }

    /**
     * Sets the SessionId property.
     *
     * @param sessionId The session identifier or null to disable the property
     * @see SendSmsRequest#setSessionReplyPath(String)
     */
    public void setSessionId(String sessionId) {
        if (sessionId != null)
            put("X-E3-Session-ID", sessionId);
        else
            remove("X-E3-Session-ID");
    }

    /**
     * Gets the UserTag property.
     *
     * @return The user tag or null if the property is disabled
     * @see SendSmsRequest#setUserTag(String)
     */
    public String getUserTag() {
        if (containsKey("X-E3-User-Tag"))
            return get("X-E3-User-Tag");
        else
            return null;
    }

    /**
     * Sets the UserTag property. By setting the UserTag property you can tag messages. You can use this for billing purposes when sending messages on behalf of several customers. The UserTag property must not exceed 50 characters; longer values will make the submission fail.
     *
     * @param userTag The user tag or null to disable the property
     */
    public void setUserTag(String userTag) {
        if (userTag != null)
            put("X-E3-User-Tag", userTag);
        else
            remove("X-E3-User-Tag");
    }

    private static final long WEEK = 1000 * 60 * 60 * 24 * 7;
    private static final long DAY = 1000 * 60 * 60 * 24;
    private static final long HOUR = 1000 * 60 * 60;
    private static final long MINUTE = 1000 * 60;

    /**
     * Gets the ValidityPeriod property.
     *
     * @return The validity period in milliseconds or null if the property is disabled
     * @see SendSmsRequest#setValidityPeriod(Long)
     */
    public Long getValidityPeriod() {
        if (containsKey("X-E3-Validity-Period")) {
            String value = get("X-E3-Validity-Period");
            long millis = Long.parseLong(value.replaceFirst("(\\d)+.", "$1"));
            if (value.endsWith("m"))
                millis *= MINUTE;
            else if (value.endsWith("h"))
                millis *= HOUR;
            else if (value.endsWith("d"))
                millis *= DAY;
            else if (value.endsWith("w"))
                millis *= WEEK;
            else {
                throw new IllegalStateException("Failed to parse X-E3-Validity-Period=" + value);
            }

            return millis;
        } else {
            return null;
        }
    }

    /**
     * Sets the ValidityPeriod property. Use the ValidityPeriod property to specify the maximum message delivery validity after which the message is discarded unless received. The property is of type TimeSpan (seconds and milliseconds are ignored). If not set the default validity period is applied. The maximum validity period is 14 days, if you specify a longer validity period 14 days will be used.
     *
     * @param validityPeriod The validity period in milliseconds or null to disable the property
     */
    public void setValidityPeriod(Long validityPeriod) {
        if (validityPeriod != null) {
            String value;
            if (validityPeriod >= WEEK && validityPeriod % WEEK == 0)
                value = validityPeriod / WEEK + "w";
            else if (validityPeriod >= DAY && validityPeriod % DAY == 0)
                value = validityPeriod / DAY + "d";
            else if (validityPeriod >= HOUR && validityPeriod % HOUR == 0)
                value = validityPeriod / HOUR + "h";
            else
                value = validityPeriod / MINUTE + "m";
            put("X-E3-Validity-Period", value);
        } else {
            remove("X-E3-Validity-Period");
        }
    }

    @Override
    public String toString() {

        Marshaller marshaller = new Marshaller();
        StringWriter sw = new StringWriter();

        try {
            marshaller.marshalWriter(this, sw);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sw.toString();
    }
}