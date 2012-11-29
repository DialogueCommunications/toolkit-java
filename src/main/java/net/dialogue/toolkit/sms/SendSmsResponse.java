/*
 * toolkit-java
 *
 * Copyright (C) 2012 Dialogue Communications Ltd.
 */

package net.dialogue.toolkit.sms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Response object containing a list of one or more submitted messages.
 */
public class SendSmsResponse implements Serializable {

    private List<Sms> messages = new ArrayList<Sms>();

    /**
     * Gets list of submitted messages.
     *
     * @return List of submitted messages
     */
    public List<Sms> getMessages() {
        return messages;
    }

    /**
     * Sets list of submitted messages.
     *
     * @param messages List of submitted messages
     */
    public void setMessages(List<Sms> messages) {
        this.messages = messages;
    }

    /**
     * Returns a string representation of this SensSmsResponse instance.
     *
     * @return String representation
     */
    @Override
    public String toString() {
        return "SendSmsResponse{" +
                "messages=" + messages +
                '}';
    }
}
