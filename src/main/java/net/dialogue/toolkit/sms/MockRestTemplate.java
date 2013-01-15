/*
 * toolkit-java
 *
 * Copyright (C) 2012 Dialogue Communications Ltd.
 */

package net.dialogue.toolkit.sms;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

/**
 * An abstract mock RestTemplate.
 */
public abstract class MockRestTemplate extends RestTemplate {

    protected abstract Sms getSms(String message, String recipient);

    @SuppressWarnings("unchecked")
    @Override
    public <T> T postForObject(String url,
                               Object entity, Class<T> responseType,
                               Object... uriVariables) throws RestClientException {
        SendSmsResponse response = new SendSmsResponse();
        SendSmsRequest request = ((HttpEntity<SendSmsRequest>) entity).getBody();
        for (String message : request.getMessages()) {
            for (String recipient : request.getRecipients()) {
                response.getMessages().add(getSms(message, recipient));
            }
        }
        return (T) response;
    }

    public static class SucceededMockRestTemplate extends MockRestTemplate {
        @Override
        protected Sms getSms(String message, String recipient) {
            Sms sms = new Sms();
            sms.setId(UUID.randomUUID().toString());
            sms.setRecipient(recipient);
            sms.setSubmissionReport(StatusCodes.TransactionCompleted.STATUS_SUCCESSFUL);
            return sms;
        }
    }

    /**
     * A mock RestTemplate, which simulates submissions failures.
     */
    public static class FailingMockRestTemplate extends MockRestTemplate {
        /**
         * Returns an instance of Sms with a failed submission report.
         *
         * @param message
         * @param recipient
         * @return
         */
        @Override
        protected Sms getSms(String message, String recipient) {
            Sms sms = new Sms();
            sms.setRecipient(recipient);
            sms.setSubmissionReport(StatusCodes.PermanentError.STATUS_UNKNOWN_ERROR);
            sms.setErrorDescription("Unknown error");
            return sms;
        }
    }
}
