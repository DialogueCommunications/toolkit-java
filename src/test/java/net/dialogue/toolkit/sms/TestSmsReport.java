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

public class TestSmsReport {

    private static final String SAMPLE_REPORT =
            "<callback " +
                    "X-E3-Delivery-Report=\"20\" " +
                    "X-E3-ID=\"90A9893BC2B645918034F4C358A062CE\" " +
                    "X-E3-Loop=\"1322229741.93646\" " +
                    "X-E3-Network=\"Orange\" " +
                    "X-E3-Recipients=\"447xxxxxxxxx\" " +
                    "X-E3-Timestamp=\"2011-12-01 18:02:21\" " +
                    "X-E3-User-Key=\"myKey1234\"/>";
    @Test
    public void testReportFromString() throws IOException {
        String s = SAMPLE_REPORT;
        testReport(SmsReport.getInstance(s));
    }

    @Test
    public void testReportFromReader() throws IOException {
        StringReader reader = new StringReader(SAMPLE_REPORT);
        testReport(SmsReport.getInstance(reader));
    }

    @Test
    public void testReportFromInputStream() throws IOException {
        InputStream is = new ByteArrayInputStream(SAMPLE_REPORT.getBytes("UTF-8"));
        testReport(SmsReport.getInstance(is));
    }

    private void testReport(SmsReport report) {

        assertNotNull(report);
        assertEquals(report.getId(), "90A9893BC2B645918034F4C358A062CE");
        assertEquals(report.getRecipient(), "447xxxxxxxxx");
        assertEquals(report.getDeliveryReport(), "20");
        assertEquals(report.getUserKey(), "myKey1234");
        assertEquals(report.getTimestamp().getTime(), 1322762541000L);
        assertEquals(report.getNetwork(), "Orange");
        assertEquals(report.toString(),
                "Id: 90A9893BC2B645918034F4C358A062CE, " +
                "Recipient: 447xxxxxxxxx, " +
                "DeliveryReport: 20, " +
                "UserKey: myKey1234, " +
                "Timestamp: 01/12/11 18:02, " +
                "Network: Orange"
        );

        report.setDeliveryReport("00");
        assertEquals(report.getState(), State.Delivered);
        assertTrue(report.isSuccessful());
        report.setDeliveryReport("1F");
        assertEquals(report.getState(), State.Delivered);
        assertTrue(report.isSuccessful());
        report.setDeliveryReport("20");
        assertEquals(report.getState(), State.TemporaryError);
        assertFalse(report.isSuccessful());
        report.setDeliveryReport("3F");
        assertEquals(report.getState(), State.TemporaryError);
        assertFalse(report.isSuccessful());
        report.setDeliveryReport("40");
        assertEquals(report.getState(), State.PermanentError);
        assertFalse(report.isSuccessful());
        report.setDeliveryReport("7F");
        assertEquals(report.getState(), State.PermanentError);
        assertFalse(report.isSuccessful());

        report.setDeliveryReport("");
        assertEquals(report.getState(), State.Undefined);
    }

    @Test(expected = IllegalStateException.class)
    public void testInvalidState() {
        SmsReport report = new SmsReport();
        report.setDeliveryReport("80");
        report.getState();
    }
}
