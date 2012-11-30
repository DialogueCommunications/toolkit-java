/*
 * toolkit-java
 *
 * Copyright (C) 2012 Dialogue Communications Ltd.
 */

package net.dialogue.toolkit.sms;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

public class TestSmsReply {

    private static final String SAMPLE_REPLY =
            "<callback " +
                    "X-E3-Account-Name=\"test\" " +
                    "X-E3-Data-Coding-Scheme=\"00\" " +
                    "X-E3-Hex-Message=\"54657374204D657373616765\" " +
                    "X-E3-ID=\"809EF683F022441DB9C4895AED6382CF\" " +
                    "X-E3-Loop=\"1322223264.20603\" " +
                    "X-E3-MO-Campaign=\"\" " +
                    "X-E3-MO-Keyword=\"\" " +
                    "X-E3-Network=\"Orange\" " +
                    "X-E3-Originating-Address=\"447xxxxxxxxx\" " +
                    "X-E3-Protocol-Identifier=\"00\" " +
                    "X-E3-Recipients=\"1234567890\" " +
                    "X-E3-Session-ID=\"1234567890\" " +
                    "X-E3-Timestamp=\"2011-11-25 12:14:23.000000\" " +
                    "X-E3-User-Data-Header-Indicator=\"0\"/>";

    @Test
    public void testReplyFromString() throws IOException {
        String s = SAMPLE_REPLY;
        testReply(SmsReply.getInstance(s));
    }


    @Test
    public void testReplyFromReader() throws IOException {
        StringReader reader = new StringReader(SAMPLE_REPLY);
        testReply(SmsReply.getInstance(reader));
    }

    @Test
    public void testReplyFromInputStream() throws IOException {
        InputStream is = new ByteArrayInputStream(SAMPLE_REPLY.getBytes("UTF-8"));
        testReply(SmsReply.getInstance(is));
    }

    public void testReply(SmsReply reply) throws IOException {

        assertNotNull(reply);
        assertEquals(reply.getId(), "809EF683F022441DB9C4895AED6382CF");
        assertEquals(reply.getSender(), "447xxxxxxxxx");
        assertEquals(reply.getSessionId(), "1234567890");
        assertEquals(reply.getHexMessage(), "54657374204D657373616765");
        assertEquals(reply.getMessage(), "Test Message");
        assertEquals(reply.getTimestamp().getTime(), 1322223263000L);
        assertEquals(reply.getNetwork(), "Orange");
        assertEquals(reply.toString(),
                "Id: 809EF683F022441DB9C4895AED6382CF, " +
                        "Sender: 447xxxxxxxxx, " +
                        "SessionId: 1234567890, " +
                        "HexMessage: 54657374204D657373616765, " +
                        "Message: Test Message, " +
                        "Timestamp: 25/11/11 12:14, " +
                        "Network: Orange"
        );
    }
}
