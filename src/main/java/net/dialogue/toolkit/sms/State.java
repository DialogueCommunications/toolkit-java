/*
 * toolkit-java
 *
 * Copyright (C) 2012 Dialogue Communications Ltd.
 */

package net.dialogue.toolkit.sms;

/**
 * Classifies the delivery report into Delivered, TemporaryError or PermanentError.
 */
public enum State {
    /**
     * Undefined (null) state
     */
    Undefined,

    /**
     * Message delivered
     */
    Delivered,

    /**
     * Temporary error but could still be delivered
     */
    TemporaryError,

    /**
     * Permanent error, message was not delivered
     */
    PermanentError
}