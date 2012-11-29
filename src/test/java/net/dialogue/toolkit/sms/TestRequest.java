package net.dialogue.toolkit.sms;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

/**
 * User: oliver
 * Date: 20/11/12
 * Time: 10:26
 */
public class TestRequest {

    private static final List<String> EMPTY_STRING_LIST = Collections.emptyList();

    private static final long WEEK = 1000 * 60 * 60 * 24 * 7;
    private static final long DAY = 1000 * 60 * 60 * 24;
    private static final long HOUR = 1000 * 60 * 60;
    private static final long MINUTE = 1000 * 60;

    //
    // Invalid message(s) constructors
    //

    @Test(expected = IllegalArgumentException.class)
    public void constructor_message_null_string() throws Exception {
        new SendSmsRequest((String)null, "recipient");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_message_empty_string() throws Exception {
        new SendSmsRequest("", "recipient");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_messages_null_list() throws Exception {
        new SendSmsRequest((List<String>)null, "recipient");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_messages_empty_list() throws Exception {
        new SendSmsRequest(EMPTY_STRING_LIST, "recipient");
    }

    //
    // Invalid recipient(s) constructors
    //

    @Test(expected = IllegalArgumentException.class)
    public void constructor_recipient_null_string() throws Exception {
        new SendSmsRequest("message", (String)null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_recipient_empty_string() throws Exception {
        new SendSmsRequest("message", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_recipients_null_list() throws Exception {
        new SendSmsRequest("message", (List<String>)null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_recipients_empty_list() throws Exception {
        new SendSmsRequest("message", EMPTY_STRING_LIST);
    }

    //
    // Invalid message(s) setters
    //

    @Test(expected = IllegalArgumentException.class)
    public void setter_message_null_string() throws Exception {
        new SendSmsRequest("message", "recipient")
            .setMessage(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setter_message_empty_string() throws Exception {
        new SendSmsRequest("message", "recipient")
                .setMessage("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setter_messages_null_list() throws Exception {
        new SendSmsRequest("message", "recipient")
                .setMessages(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setter_messages_empty_list() throws Exception {
        new SendSmsRequest("message", "recipient")
                .setMessages(EMPTY_STRING_LIST);
    }

    //
    // Invalid recipient(s) setters
    //

    @Test(expected = IllegalArgumentException.class)
    public void setter_recipient_null_string() throws Exception {
        new SendSmsRequest("message", "recipient")
                .setRecipient(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setter_recipient_empty_string() throws Exception {
        new SendSmsRequest("message", "recipient")
                .setRecipient("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setter_recipients_null_list() throws Exception {
        new SendSmsRequest("message", "recipient")
                .setRecipients(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setter_recipients_empty_list() throws Exception {
        new SendSmsRequest("message", "recipient")
                .setRecipients(EMPTY_STRING_LIST);
    }

    //
    // Constructors and getters
    //

    @Test
    public void constructors_getters() throws Exception {

        SendSmsRequest request;

        // Default constructor
        request = new SendSmsRequest();
        assertEquals(request.getMessages(), Collections.emptyList());
        assertEquals(request.getRecipients(), Collections.emptyList());

        // String/String constructor
        request = new SendSmsRequest(
                "message",
                "recipient"
        );
        assertEquals(request.getMessages(), Arrays.asList("message"));
        assertEquals(request.getRecipients(), Arrays.asList("recipient"));

        // String/List constructor
        request = new SendSmsRequest(
                "message",
                Arrays.asList("recipient", "recipient2")
        );
        assertEquals(request.getMessages(), Arrays.asList("message"));
        assertEquals(request.getRecipients(), Arrays.asList("recipient", "recipient2"));

        // List/String constructor
        request = new SendSmsRequest(
                Arrays.asList("message", "message2"),
                "recipient"
        );
        assertEquals(request.getMessages(), Arrays.asList("message", "message2"));
        assertEquals(request.getRecipients(), Arrays.asList("recipient"));

        // List/List constructor
        request = new SendSmsRequest(
                Arrays.asList("message", "message2"),
                Arrays.asList("recipient", "recipient2")
        );
        assertEquals(request.getMessages(), Arrays.asList("message", "message2"));
        assertEquals(request.getRecipients(), Arrays.asList("recipient", "recipient2"));
    }

    @Test
    public void message_recipient_setters_getters() throws Exception {

        SendSmsRequest request = new SendSmsRequest();

        // Message: String
        request.setMessage("message");
        assertEquals(request.getMessages(), Arrays.asList("message"));
        // Messages: List
        request.setMessages(Arrays.asList("message", "message2"));
        assertEquals(request.getMessages(), Arrays.asList("message", "message2"));

        // Recipient: String
        request.setRecipient("recipient");
        assertEquals(request.getRecipients(), Arrays.asList("recipient"));
        // Recipients: List
        request.setRecipients(Arrays.asList("recipient", "recipient2"));
        assertEquals(request.getRecipients(), Arrays.asList("recipient", "recipient2"));
    }

    @Test
    public void sender() throws Exception {

        SendSmsRequest request = new SendSmsRequest();
        assertNull(request.getSender());

        request.setSender("sender");
        assertEquals(request.getSender(), "sender");
        assertEquals(request.get("X-E3-Originating-Address"), "sender");

        request.setSender(null);
        assertNull(request.getSender());
        assertFalse(request.containsKey("X-E3-Originating-Address"));
    }

    @Test
    public void concatenationLimit() throws Exception {

        SendSmsRequest request = new SendSmsRequest();
        assertNull(request.getConcatenationLimit());

        request.setConcatenationLimit(255);
        assertEquals(request.getConcatenationLimit(), 255);
        assertEquals(request.get("X-E3-Concatenation-Limit"), "255");

        request.setConcatenationLimit(null);
        assertNull(request.getConcatenationLimit());
        assertFalse(request.containsKey("X-E3-Concatenation-Limit"));
    }

    @Test
    public void scheduleFor() throws Exception {

        SendSmsRequest request = new SendSmsRequest();
        assertNull(request.getScheduleFor());

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        request.setScheduleFor(date);

        assertEquals(request.getScheduleFor(), date);

        calendar.setTimeZone(TimeZone.getTimeZone("Europe/London"));
        calendar.set(2012, 9 - 1, 1, 12, 30, 0);
        request.setScheduleFor(calendar.getTime());
        assertEquals(request.get("X-E3-Schedule-For"), "20120901123000");

        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        calendar.set(2012, 9 - 1, 1, 12, 30, 0);
        request.setScheduleFor(calendar.getTime());
        assertEquals(request.get("X-E3-Schedule-For"), "20120901113000");

        calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        calendar.set(2012, 9 - 1, 1, 12, 30, 0);
        request.setScheduleFor(calendar.getTime());
        assertEquals(request.get("X-E3-Schedule-For"), "20120901133000");

        request.setScheduleFor(null);
        assertNull(request.getScheduleFor());
        assertFalse(request.containsKey("X-E3-Schedule-For"));
    }

    @Test
    public void confirmDelivery() throws Exception {

        SendSmsRequest request = new SendSmsRequest();
        assertNull(request.getConfirmDelivery());

        request.setConfirmDelivery(true);
        assertTrue(request.getConfirmDelivery());
        assertEquals(request.get("X-E3-Confirm-Delivery"), "on");

        request.setConfirmDelivery(false);
        assertFalse(request.getConfirmDelivery());
        assertEquals(request.get("X-E3-Confirm-Delivery"), "off");

        request.setConfirmDelivery(null);
        assertNull(request.getConfirmDelivery());
        assertFalse(request.containsKey("X-E3-Confirm-Delivery"));
    }

    @Test
    public void replyPath() throws Exception {

        SendSmsRequest request = new SendSmsRequest();
        assertNull(request.getReplyPath());

        request.setReplyPath("/mypath");
        assertEquals(request.getReplyPath(), "/mypath");
        assertEquals(request.get("X-E3-Reply-Path"), "/mypath");

        request.setReplyPath(null);
        assertNull(request.getReplyPath());
        assertFalse(request.containsKey("X-E3-Reply-Path"));
    }

    @Test
    public void userKey() throws Exception {

        SendSmsRequest request = new SendSmsRequest();
        assertNull(request.getUserKey());

        request.setUserKey("123457890");
        assertEquals(request.getUserKey(), "123457890");
        assertEquals(request.get("X-E3-User-Key"), "123457890");

        request.setUserKey(null);
        assertNull(request.getUserKey());
        assertFalse(request.containsKey("X-E3-User-Key"));
    }

    @Test
    public void sessionReplyPath() throws Exception {

        SendSmsRequest request = new SendSmsRequest();
        assertNull(request.getSessionReplyPath());

        request.setSessionReplyPath("/mypath");
        assertEquals(request.getSessionReplyPath(), "/mypath");
        assertEquals(request.get("X-E3-Session-Reply-Path"), "/mypath");

        request.setSessionReplyPath(null);
        assertNull(request.getSessionReplyPath());
        assertFalse(request.containsKey("X-E3-Session-Reply-Path"));
    }

    @Test
    public void sessionId() throws Exception {

        SendSmsRequest request = new SendSmsRequest();
        assertNull(request.getSessionId());

        request.setSessionId("1234567890");
        assertEquals(request.getSessionId(), "1234567890");
        assertEquals(request.get("X-E3-Session-ID"), "1234567890");

        request.setSessionId(null);
        assertNull(request.getSessionId());
        assertFalse(request.containsKey("X-E3-Session-ID"));
    }

    @Test
    public void userTag() throws Exception {

        SendSmsRequest request = new SendSmsRequest();
        assertNull(request.getUserTag());

        request.setUserTag("1234567890");
        assertEquals(request.getUserTag(), "1234567890");
        assertEquals(request.get("X-E3-User-Tag"), "1234567890");

        request.setUserTag(null);
        assertNull(request.getUserTag());
        assertFalse(request.containsKey("X-E3-User-Tag"));
    }

    @Test
    public void validityPeriod() throws Exception {

        SendSmsRequest request = new SendSmsRequest();
        assertNull(request.getValidityPeriod());

        request.setValidityPeriod(WEEK * 2);
        assertEquals(request.getValidityPeriod(), WEEK * 2);
        assertEquals(request.get("X-E3-Validity-Period"), "2w");

        request.setValidityPeriod(DAY * 7);
        assertEquals(request.getValidityPeriod(), DAY * 7);
        assertEquals(request.get("X-E3-Validity-Period"), "1w");

        request.setValidityPeriod(DAY * 2);
        assertEquals(request.getValidityPeriod(), DAY * 2);
        assertEquals(request.get("X-E3-Validity-Period"), "2d");

        request.setValidityPeriod(HOUR * 24);
        assertEquals(request.getValidityPeriod(), HOUR * 24);
        assertEquals(request.get("X-E3-Validity-Period"), "1d");

        request.setValidityPeriod(HOUR * 2);
        assertEquals(request.getValidityPeriod(), HOUR * 2);
        assertEquals(request.get("X-E3-Validity-Period"), "2h");

        request.setValidityPeriod(MINUTE * 60);
        assertEquals(request.getValidityPeriod(), MINUTE * 60);
        assertEquals(request.get("X-E3-Validity-Period"), "1h");

        request.setValidityPeriod(MINUTE * 2);
        assertEquals(request.getValidityPeriod(), MINUTE * 2);
        assertEquals(request.get("X-E3-Validity-Period"), "2m");

        request.setValidityPeriod(1000L); // 1s
        assertEquals(request.getValidityPeriod(), 0L);
        assertEquals(request.get("X-E3-Validity-Period"), "0m");

        request.setValidityPeriod(null);
        assertNull(request.getValidityPeriod());
        assertFalse(request.containsKey("X-E3-Validity-Period"));
    }

    @Test
    public void custom() throws Exception {

        SendSmsRequest request = new SendSmsRequest();
        assertTrue(request.keySet().isEmpty());

        request.put("X-E3-Custom-Property", "test1234");
        assertEquals(request.get("X-E3-Custom-Property"), "test1234");

        request.remove("X-E3-Custom-Property");
        assertFalse(request.containsKey("X-E3-Custom-Property"));
    }

    @Test
    public void serialization() {
        SendSmsRequest request = new SendSmsRequest(
                Arrays.asList("message", "message2"),
                Arrays.asList("recipient", "recipient2"));
        request.setSender("sender");
        request.setConcatenationLimit(255);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/London"));
        calendar.set(2012, 9 - 1, 1, 12, 30, 0);
        request.setScheduleFor(calendar.getTime());
        request.setConfirmDelivery(true);
        request.setReplyPath("/path");
        request.setUserKey("123457890");
        request.setSessionReplyPath("/path");
        request.setSessionId("1234567890");
        request.setUserTag("123457890");
        request.setValidityPeriod(WEEK * 2);

        String xml = request.toString().replaceAll("\r\n", "").replaceAll("\n", "").replaceAll("  <", "<");
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><sendSmsRequest><X-E3-Message>message</X-E3-Message><X-E3-Message>message2</X-E3-Message><X-E3-Recipients>recipient</X-E3-Recipients><X-E3-Recipients>recipient2</X-E3-Recipients><X-E3-Originating-Address>sender</X-E3-Originating-Address><X-E3-Concatenation-Limit>255</X-E3-Concatenation-Limit><X-E3-Schedule-For>20120901123000</X-E3-Schedule-For><X-E3-Confirm-Delivery>on</X-E3-Confirm-Delivery><X-E3-Reply-Path>/path</X-E3-Reply-Path><X-E3-User-Key>123457890</X-E3-User-Key><X-E3-Session-Reply-Path>/path</X-E3-Session-Reply-Path><X-E3-Session-ID>1234567890</X-E3-Session-ID><X-E3-User-Tag>123457890</X-E3-User-Tag><X-E3-Validity-Period>2w</X-E3-Validity-Period></sendSmsRequest>", xml);
    }
}


