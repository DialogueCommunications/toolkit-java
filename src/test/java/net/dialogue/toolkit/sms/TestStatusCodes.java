/*
 * toolkit-java
 *
 * Copyright (C) 2013 Dialogue Communications Ltd.
 */

package net.dialogue.toolkit.sms;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class TestStatusCodes {
    @Test
    public void test() {
        StatusCodes statusCodes = StatusCodes.INSTANCE;
        
        // Transaction Completed
        assertTrue(statusCodes.isTransactionCompleted("00"));
        assertTrue(statusCodes.isTransactionCompleted(0x00));
        assertTrue(statusCodes.isTransactionCompleted("0a"));
        assertTrue(statusCodes.isTransactionCompleted(0x0a));
        assertTrue(statusCodes.isTransactionCompleted("1F"));
        assertTrue(statusCodes.isTransactionCompleted(0x1F));

        assertFalse(statusCodes.isTemporaryError("00"));
        assertFalse(statusCodes.isTemporaryError(0x00));
        assertFalse(statusCodes.isTemporaryError("0a"));
        assertFalse(statusCodes.isTemporaryError(0x0a));
        assertFalse(statusCodes.isTemporaryError("1F"));
        assertFalse(statusCodes.isTemporaryError(0x1F));

        assertFalse(statusCodes.isPermanentError("00"));
        assertFalse(statusCodes.isPermanentError(0x00));
        assertFalse(statusCodes.isPermanentError("0a"));
        assertFalse(statusCodes.isPermanentError(0x0a));
        assertFalse(statusCodes.isPermanentError("1F"));
        assertFalse(statusCodes.isPermanentError(0x1F));
        
        assertFalse(statusCodes.isRetryError("00"));
        assertFalse(statusCodes.isRetryError(0x00));
        assertFalse(statusCodes.isRetryError("0a"));
        assertFalse(statusCodes.isRetryError(0x0a));
        assertFalse(statusCodes.isRetryError("1F"));
        assertFalse(statusCodes.isRetryError(0x1F));

        // Temporary Errors
        assertFalse(statusCodes.isTransactionCompleted("20"));
        assertFalse(statusCodes.isTransactionCompleted(0x20));
        assertFalse(statusCodes.isTransactionCompleted("2a"));
        assertFalse(statusCodes.isTransactionCompleted(0x2a));
        assertFalse(statusCodes.isTransactionCompleted("2F"));
        assertFalse(statusCodes.isTransactionCompleted(0x2F));

        assertTrue(statusCodes.isTemporaryError("20"));
        assertTrue(statusCodes.isTemporaryError(0x20));
        assertTrue(statusCodes.isTemporaryError("2a"));
        assertTrue(statusCodes.isTemporaryError(0x2a));
        assertTrue(statusCodes.isTemporaryError("2F"));
        assertTrue(statusCodes.isTemporaryError(0x2F));

        assertFalse(statusCodes.isPermanentError("20"));
        assertFalse(statusCodes.isPermanentError(0x20));
        assertFalse(statusCodes.isPermanentError("2a"));
        assertFalse(statusCodes.isPermanentError(0x2a));
        assertFalse(statusCodes.isPermanentError("2F"));
        assertFalse(statusCodes.isPermanentError(0x2F));

        assertFalse(statusCodes.isRetryError("20"));
        assertFalse(statusCodes.isRetryError(0x20));
        assertFalse(statusCodes.isRetryError("2a"));
        assertFalse(statusCodes.isRetryError(0x2a));
        assertFalse(statusCodes.isRetryError("2F"));
        assertFalse(statusCodes.isRetryError(0x2F));

        // Permanent Errors
        assertFalse(statusCodes.isTransactionCompleted("40"));
        assertFalse(statusCodes.isTransactionCompleted(0x40));
        assertFalse(statusCodes.isTransactionCompleted("4a"));
        assertFalse(statusCodes.isTransactionCompleted(0x4a));
        assertFalse(statusCodes.isTransactionCompleted("5F"));
        assertFalse(statusCodes.isTransactionCompleted(0x5F));

        assertFalse(statusCodes.isTemporaryError("40"));
        assertFalse(statusCodes.isTemporaryError(0x40));
        assertFalse(statusCodes.isTemporaryError("4a"));
        assertFalse(statusCodes.isTemporaryError(0x4a));
        assertFalse(statusCodes.isTemporaryError("5F"));
        assertFalse(statusCodes.isTemporaryError(0x5F));

        assertTrue(statusCodes.isPermanentError("40"));
        assertTrue(statusCodes.isPermanentError(0x40));
        assertTrue(statusCodes.isPermanentError("4a"));
        assertTrue(statusCodes.isPermanentError(0x4a));
        assertTrue(statusCodes.isPermanentError("5F"));
        assertTrue(statusCodes.isPermanentError(0x5F));

        assertFalse(statusCodes.isRetryError("40"));
        assertFalse(statusCodes.isRetryError(0x40));
        assertFalse(statusCodes.isRetryError("4a"));
        assertFalse(statusCodes.isRetryError(0x4a));
        assertFalse(statusCodes.isRetryError("5F"));
        assertFalse(statusCodes.isRetryError(0x5F));

        // Retry Errors
        assertFalse(statusCodes.isTransactionCompleted("60"));
        assertFalse(statusCodes.isTransactionCompleted(0x60));
        assertFalse(statusCodes.isTransactionCompleted("6a"));
        assertFalse(statusCodes.isTransactionCompleted(0x6a));
        assertFalse(statusCodes.isTransactionCompleted("7F"));
        assertFalse(statusCodes.isTransactionCompleted(0x7F));

        assertFalse(statusCodes.isTemporaryError("60"));
        assertFalse(statusCodes.isTemporaryError(0x60));
        assertFalse(statusCodes.isTemporaryError("6a"));
        assertFalse(statusCodes.isTemporaryError(0x6a));
        assertFalse(statusCodes.isTemporaryError("7F"));
        assertFalse(statusCodes.isTemporaryError(0x7F));

        assertFalse(statusCodes.isPermanentError("60"));
        assertFalse(statusCodes.isPermanentError(0x60));
        assertFalse(statusCodes.isPermanentError("6a"));
        assertFalse(statusCodes.isPermanentError(0x6a));
        assertFalse(statusCodes.isPermanentError("7F"));
        assertFalse(statusCodes.isPermanentError(0x7F));

        assertTrue(statusCodes.isRetryError("60"));
        assertTrue(statusCodes.isRetryError(0x60));
        assertTrue(statusCodes.isRetryError("6a"));
        assertTrue(statusCodes.isRetryError(0x6a));
        assertTrue(statusCodes.isRetryError("7F"));
        assertTrue(statusCodes.isRetryError(0x7F));

        // Invalid ones
        assertFalse(statusCodes.isTransactionCompleted("xx"));
        assertFalse(statusCodes.isTransactionCompleted(-1));
        assertFalse(statusCodes.isTemporaryError("xx"));
        assertFalse(statusCodes.isTemporaryError(-1));
        assertFalse(statusCodes.isPermanentError("xx"));
        assertFalse(statusCodes.isPermanentError(-1));
        assertFalse(statusCodes.isRetryError("xx"));
        assertFalse(statusCodes.isRetryError(-1));

        // Description
        assertEquals(statusCodes.getDescription("00"), "Successful");
        assertEquals(statusCodes.getDescription(0x00), "Successful");

        assertEquals(statusCodes.getDescription("4A"), "Unknown subscriber");
        assertEquals(statusCodes.getDescription("4a"), "Unknown subscriber");
        assertEquals(statusCodes.getDescription(0x4A), "Unknown subscriber");

        assertEquals(statusCodes.getDescription("74"), "Insufficient prepay credit");
        assertEquals(statusCodes.getDescription(0x74), "Insufficient prepay credit");
    }

    // Transaction Completed
    @Test(expected = IllegalArgumentException.class)
    public void testIsTransactionCompletedNullStatusCode() {
        StatusCodes.INSTANCE.isTransactionCompleted(null);
    } 
    
    @Test(expected = IllegalArgumentException.class)
    public void testIsTransactionCompletedEmptyStatusCode() {
        StatusCodes.INSTANCE.isTransactionCompleted("");
    }

    // Temporary Errors
    @Test(expected = IllegalArgumentException.class)
    public void testIsTemporaryErrorNullStatusCode() {
        StatusCodes.INSTANCE.isTemporaryError(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsTemporaryErrorEmptyStatusCode() {
        StatusCodes.INSTANCE.isTemporaryError("");
    }
    
    // Permanent Errors
    @Test(expected = IllegalArgumentException.class)
    public void testIsPermanentErrorNullStatusCode() {
        StatusCodes.INSTANCE.isPermanentError(null);
    } 
    
    @Test(expected = IllegalArgumentException.class)
    public void testIsPermanentErrorEmptyStatusCode() {
        StatusCodes.INSTANCE.isPermanentError("");
    }

    // Retry Errors
    @Test(expected = IllegalArgumentException.class)
    public void testIsRetryErrorNullStatusCode() {
        StatusCodes.INSTANCE.isRetryError(null);
    } 
    
    @Test(expected = IllegalArgumentException.class)
    public void testIsRetryErrorEmptyStatusCode() {
        StatusCodes.INSTANCE.isRetryError("");
    }

    // Description
    @Test(expected = IllegalArgumentException.class)
    public void testGetDescriptionNullStatusCode() {
        StatusCodes.INSTANCE.getDescription(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetDescriptionEmptyStatusCode() {
        StatusCodes.INSTANCE.getDescription("");
    }
}
