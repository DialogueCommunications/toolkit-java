/*
 * toolkit-java
 *
 * Copyright (C) 2012 Dialogue Communications Ltd.
 */

package net.dialogue.toolkit.sms;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A RestTemplate that configures the marshaller required by the API protocol. If you want to
 * provide your own RestOperations instance to SendSmsClient you may want to subclass this class.
 */
public class SendSmsClientRestTemplate extends RestTemplate {

    /**
     * List passed to {@link #setMessageConverters(List<HttpMessageConverter<?>>) setMessageConverters} that
     * contains the marshaller required by the API protocol.
     */
    public static List<HttpMessageConverter<?>> MESSAGE_CONVERTERS = Collections.unmodifiableList(
            new ArrayList<HttpMessageConverter<?>>(Arrays.asList(
                    new MarshallingHttpMessageConverter(new Marshaller())
            )
            ));

    /**
     * Creates a RestTemplate without a transport.
     * Use {@link #setRequestFactory(ClientHttpRequestFactory) setRequestFactory} to provide a transport.
     */
    public SendSmsClientRestTemplate() {
        super();
        setMessageConverters(MESSAGE_CONVERTERS);
    }

    /**
     * Creates a RestTemplate with the given underlying transport, a ClientHttpRequestFactory.
     *
     * @param requestFactory The transport as a ClientHttpRequestFactory
     */
    public SendSmsClientRestTemplate(ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
        setMessageConverters(MESSAGE_CONVERTERS);
    }
}
