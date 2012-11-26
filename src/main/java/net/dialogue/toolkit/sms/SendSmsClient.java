package net.dialogue.toolkit.sms;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.util.support.Base64;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * User: oliver
 * Date: 06/11/12
 * Time: 13:20
 */
public class SendSmsClient {

    /**
     * Default transport that uses standard J2SE facilities (HttpURLConnection).
     */
    public static final ClientHttpRequestFactory TRANSPORT_SIMPLE_CLIENT;

    /**
     * Transport using Apache Commons HttpClient 3.x. Note that the Commons HttpClient
     * project is no longer being actively developed and has been replaced by Apache HttpComponents.
     */
    public static final ClientHttpRequestFactory TRANSPORT_COMMONS_CLIENT;

    /**
     * Transport using Apache HttpComponents 4.x.
     */
    public static final ClientHttpRequestFactory TRANSPORT_HTTP_COMPONENTS_CLIENT;

    static {
        TRANSPORT_SIMPLE_CLIENT = new SimpleClientHttpRequestFactory();
        TRANSPORT_COMMONS_CLIENT = new CommonsClientHttpRequestFactory();
        TRANSPORT_HTTP_COMPONENTS_CLIENT = new HttpComponentsClientHttpRequestFactory();
    }

    protected final RestTemplate restTemplate = new RestTemplate();
    private String endpoint;
    private String userName;
    private String password;
    private boolean secure = true;
    private String path = "/submit_sm";

    public SendSmsClient() {
        setTransport(TRANSPORT_SIMPLE_CLIENT);
        restTemplate.setMessageConverters(
                new ArrayList<HttpMessageConverter<?>>(
                        Arrays.asList(
                                new MarshallingHttpMessageConverter(new Marshaller())
                        )
                )
        );
    }

    /**
     * Gets the underlying transport, a ClientHttpRequestFactory.
     *
     * @return The transport as a ClientHttpRequestFactory
     */
    public ClientHttpRequestFactory getTransport() {
        return restTemplate.getRequestFactory();
    }

    /**
     * Sets the underlying transport, a ClientHttpRequestFactory.
     * <p/>
     * You can use TRANSPORT_SIMPLE_CLIENT, TRANSPORT_COMMONS_CLIENT, TRANSPORT_HTTP_COMPONENTS_CLIENT
     * or your custom transport implementation.
     * <p/>
     * Note that built-in credentials support is only available for transports of type
     * CommonsClientHttpRequestFactory and HttpComponentsClientHttpRequestFactory;
     * any other custom transport requires you to provide credentials yourself.
     * <p/>
     * Provide null to use the default (TRANSPORT_SIMPLE_CLIENT) transport.
     *
     * @param requestFactory The transport as a ClientHttpRequestFactory
     */
    public void setTransport(ClientHttpRequestFactory requestFactory) {
        restTemplate.setRequestFactory(requestFactory);
    }

    /**
     * Gets the Endpoint property.
     *
     * @return The endpoint (host name) used for sending messages
     * @see SendSmsClient#setEndpoint(String)
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * Sets the Endpoint property.
     *
     * @param endpoint The endpoint (host name) used for sending messages
     * @throws IllegalArgumentException Thrown if endpoint is null or empty
     */
    public void setEndpoint(String endpoint) {
        if (endpoint == null || endpoint.length() == 0) {
            throw new IllegalArgumentException(
                    "No endpoint provided."
            );
        }

        this.endpoint = endpoint;
    }

    /**
     * Gets the UserName property.
     *
     * @return The user name used for sending messages
     * @see SendSmsClient#setUserName(String)
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the UserName property.
     *
     * @param userName The user name used for sending messages
     * @throws IllegalArgumentException Thrown if userName is null or empty
     */
    public void setUserName(String userName) {
        if (userName == null || userName.length() == 0) {
            throw new IllegalArgumentException(
                    "No userName provided."
            );
        }

        this.userName = userName;
    }

    /**
     * Gets the Password property.
     *
     * @return The password used for sending messages
     * @see SendSmsClient#setPassword(String)
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the Password property.
     *
     * @param password The user name used for sending messages
     * @throws IllegalArgumentException Thrown if password is null or empty
     */
    public void setPassword(String password) {
        if (password == null || password.length() == 0) {
            throw new IllegalArgumentException(
                    "No password provided."
            );
        }

        this.password = password;
    }

    /**
     * Gets the Secure property.
     *
     * @return True if secure communication (SSL) is used; false otherwise.
     * @see SendSmsClient#setSecure(boolean)
     */
    public boolean getSecure() {
        return secure;
    }

    /**
     * Sets the Secure property. By default secure communication is enabled and we recommended that you don't change this property.
     *
     * @param secure True if secure communication (SSL) is used; false otherwise.
     */
    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    /**
     * Gets the Path property.
     *
     * @return The path used for sending messages
     * @see SendSmsClient#setPath(String)
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the Path property. Normally you should not and don't need to modify this.
     *
     * @param path The path used for sending messages
     * @throws IllegalArgumentException Thrown if path is null, empty or does not start with /
     */
    public void setPath(String path) {
        if (path == null || path.length() == 0) {
            throw new IllegalArgumentException(
                    "No path provided."
            );
        }

        if (!path.startsWith("/")) {
            throw new IllegalArgumentException(
                    "The path must start with '/'."
            );
        }

        this.path = path;
    }

    /**
     * Performs the message submission.
     *
     * @param request Request object containing message(s), recipient(s) and other optional properties
     * @return Response object containing a list of one or more submitted messages
     * @throws java.io.IOException Thrown if there is a networking or communication problem with the endpoint
     */
    public SendSmsResponse sendSms(SendSmsRequest request) throws HttpClientErrorException, IOException {
        try {
            return restTemplate.postForObject(
                    (secure ? "https://" : "http://") + endpoint + path,
                    new HttpEntity<SendSmsRequest>(request, createHeaders()),
                    SendSmsResponse.class);
        } catch (ResourceAccessException e) {
            if (e.getCause() instanceof IOException) {
                throw (IOException) e.getCause();
            } else {
                throw e;
            }
        }
    }

    /**
     * Creates default headers using Content-Type: application/xml and credentials.
     *
     * @return HTTP headers
     */
    protected HttpHeaders createHeaders() {
        return new HttpHeaders() {
            {
                setContentType(MediaType.APPLICATION_XML);
                String authorization = "Basic " + Base64.encodeBytes(
                        (getUserName() + ":" + getPassword()).getBytes());
                set("Authorization", authorization);
            }
        };
    }

    /**
     * Utility class for building new SendSmsClient instances. Usage:
     * <p/>
     * import static net.dialogue.toolkit.sms.SendSmsClient.*;
     * <p/>
     * SendSmsClient client = new SendSmsClient.Builder()
     * .transport(TRANSPORT_HTTP_COMPONENTS_CLIENT)
     * .endpoint("endpoint")
     * .credentials("username", "password")
     * .build();
     */
    public static class Builder {

        private SendSmsClient client = new SendSmsClient();

        /**
         * Creates a new builder instance.
         */
        public Builder() {
            transport(TRANSPORT_SIMPLE_CLIENT);
        }

        /**
         * Provides the underlying transport, a ClientHttpRequestFactory.
         * <p/>
         * You can use TRANSPORT_SIMPLE_CLIENT, TRANSPORT_COMMONS_CLIENT, TRANSPORT_HTTP_COMPONENTS_CLIENT
         * or your custom transport implementation.
         * <p/>
         * Note that built-in credentials support is only available for transports of type
         * CommonsClientHttpRequestFactory and HttpComponentsClientHttpRequestFactory;
         * any other custom transport requires you to provide credentials yourself.
         *
         * @param requestFactory The transport as a ClientHttpRequestFactory
         * @return The builder for chaining calls
         */
        public Builder transport(ClientHttpRequestFactory requestFactory) {
            client.setTransport(requestFactory);
            return this;
        }

        /**
         * Provides the endpoint (host name) used for sending messages.
         *
         * @param endpoint The endpoint (host name) used for sending messages
         * @return The builder for chaining calls
         * @throws IllegalArgumentException Thrown if endpoint is null or empty
         */
        public Builder endpoint(String endpoint) {
            client.setEndpoint(endpoint);
            return this;
        }

        /**
         * Provides the credentials (user name, password) used for sending messages.
         *
         * @param userName The user name
         * @param password The password
         * @return The builder for chaining calls
         * @throws IllegalArgumentException If userName or password is null or empty
         */
        public Builder credentials(String userName, String password) {
            client.setUserName(userName);
            client.setPassword(password);
            return this;
        }

        /**
         * Indicates whether to enable or disable secure communication.
         * <p/>
         * By default secure communication is enabled and we recommended that you do not disable it.
         *
         * @param secure True to enable secure communication, false to disable secure communications
         * @return The builder for chaining calls
         */
        public Builder secure(boolean secure) {
            client.setSecure(secure);
            return this;
        }

        /**
         * Optionally provides an alternative path used for sending messages. Normally you should not
         * and don't need to modify this.
         *
         * @param path The path used for sending messages
         * @return The builder for chaining calls
         */
        public Builder path(String path) {
            client.setPath(path);
            return this;
        }

        /**
         * Returns client instance used to send messages.
         *
         * @return The client instance used to send messages
         */
        public SendSmsClient build() {
            try {
                return client;
            } finally {
                client = null;
            }
        }
    }
}
