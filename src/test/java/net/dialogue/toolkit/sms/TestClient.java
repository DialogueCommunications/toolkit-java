package net.dialogue.toolkit.sms;

import org.junit.Test;
import static org.junit.Assert.*;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.*;

import java.io.IOException;
import java.net.URI;

import static net.dialogue.toolkit.sms.SendSmsClient.TRANSPORT_COMMONS_CLIENT;
import static net.dialogue.toolkit.sms.SendSmsClient.TRANSPORT_HTTP_COMPONENTS_CLIENT;
import static net.dialogue.toolkit.sms.SendSmsClient.TRANSPORT_SIMPLE_CLIENT;

/**
 * User: oliver
 * Date: 20/11/12
 * Time: 09:24
 */
public class TestClient {

    @Test
    public void transport() throws Exception {

        SendSmsClient client;

        // Default transport
        client = new SendSmsClient();
        assertTrue(client.getTransport() instanceof SimpleClientHttpRequestFactory);

        // Transport.SIMPLE_CLIENT
        client = new SendSmsClient();
        client.setTransport(TRANSPORT_SIMPLE_CLIENT);
        assertTrue(client.getTransport() instanceof SimpleClientHttpRequestFactory);

        // Transport.COMMONS_CLIENT
        client = new SendSmsClient();
        client.setTransport(TRANSPORT_COMMONS_CLIENT);
        assertTrue(client.getTransport() instanceof CommonsClientHttpRequestFactory);

        // Transport.HTTP_COMPONENTS_CLIENT
        client = new SendSmsClient();
        client.setTransport(TRANSPORT_HTTP_COMPONENTS_CLIENT);
        assertTrue(client.getTransport() instanceof HttpComponentsClientHttpRequestFactory);

        // Custom transport
        ClientHttpRequestFactory customTransport = new ClientHttpRequestFactory() {
            public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
                return null;
            }
        };
        client = new SendSmsClient();
        client.setTransport(customTransport);
        assertEquals(client.getTransport(), customTransport);
    }

    @Test
    public void endpoint() throws Exception {

        SendSmsClient client = new SendSmsClient();

        // No endpoint set by default
        assertNull(client.getEndpoint());

        client.setEndpoint("endpoint");
        assertEquals(client.getEndpoint(), "endpoint");
    }

    @Test(expected = IllegalArgumentException.class)
    public void endpoint_null() throws Exception {
        SendSmsClient client = new SendSmsClient();
        client.setEndpoint(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void endpoint_empty() throws Exception {
        SendSmsClient client = new SendSmsClient();
        client.setEndpoint("");
    }

    @Test
    public void credentials() throws Exception {

        SendSmsClient client = new SendSmsClient();

        // No user name, password set by default
        assertNull(client.getUserName());
        assertNull(client.getPassword());

        client.setUserName("user123");
        assertEquals(client.getUserName(), "user123");

        client.setPassword("pass456");
        assertEquals(client.getPassword(), "pass456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void userName_null() throws Exception {
        SendSmsClient client = new SendSmsClient();
        client.setUserName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void userName_empty() throws Exception {
        SendSmsClient client = new SendSmsClient();
        client.setUserName("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void password_null() throws Exception {
        SendSmsClient client = new SendSmsClient();
        client.setPassword(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void password_empty() throws Exception {
        SendSmsClient client = new SendSmsClient();
        client.setPassword("");
    }

    @Test
    public void secure() throws Exception {

        SendSmsClient client = new SendSmsClient();

        // Secure by default
        assertTrue(client.getSecure());

        client.setSecure(false);
        assertFalse(client.getSecure());
        client.setSecure(true);
        assertTrue(client.getSecure());
    }

    @Test
    public void path() throws Exception {

        SendSmsClient client = new SendSmsClient();

        // Check correct default
        assertEquals("/submit_sm", client.getPath());

       client.setPath("/mypath");
        assertEquals("/mypath", client.getPath());
    }

    @Test(expected = IllegalArgumentException.class)
    public void path_null() throws Exception {
        SendSmsClient client = new SendSmsClient();
        client.setPath(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void path_empty() throws Exception {
        SendSmsClient client = new SendSmsClient();
        client.setPath("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void path_invalid() throws Exception {
        SendSmsClient client = new SendSmsClient();
        client.setPath("mypath");
    }
}
