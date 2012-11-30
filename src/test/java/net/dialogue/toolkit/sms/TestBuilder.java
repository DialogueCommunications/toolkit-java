package net.dialogue.toolkit.sms;

import org.junit.Test;
import static org.junit.Assert.*;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

import static net.dialogue.toolkit.sms.SendSmsClient.Builder.*;

public class TestBuilder {

    @Test
    public void transport() throws Exception {

        SendSmsClient client;

        // Default transport
        client = new SendSmsClient.Builder()
                .build();
        assertTrue(((RestTemplate)client.getRestOperations()).getRequestFactory()
                instanceof SimpleClientHttpRequestFactory);

        // Transport.SIMPLE_CLIENT
        client = new SendSmsClient.Builder()
                .transport(TRANSPORT_SIMPLE_CLIENT).build();
        assertTrue(((RestTemplate)client.getRestOperations()).getRequestFactory()
                instanceof SimpleClientHttpRequestFactory);

        // Transport.COMMONS_CLIENT
        client = new SendSmsClient.Builder()
                .transport(TRANSPORT_COMMONS_CLIENT).build();
        assertTrue(((RestTemplate)client.getRestOperations()).getRequestFactory()
                instanceof CommonsClientHttpRequestFactory);

        // Transport.HTTP_COMPONENTS_CLIENT
        client = new SendSmsClient.Builder()
                .transport(TRANSPORT_HTTP_COMPONENTS_CLIENT).build();
        assertTrue(((RestTemplate)client.getRestOperations()).getRequestFactory()
                instanceof HttpComponentsClientHttpRequestFactory);

        // Custom transport
        ClientHttpRequestFactory customTransport = new ClientHttpRequestFactory() {
            public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
                return null;
            }
        };
        client = new SendSmsClient.Builder()
                .transport(customTransport).build();
        assertEquals(((RestTemplate)client.getRestOperations()).getRequestFactory(), customTransport);
    }

    @Test
    public void endpoint() throws Exception {

        SendSmsClient client;

        // No endpoint set by default
        client = new SendSmsClient.Builder()
                .build();
        assertNull(client.getEndpoint());

        client = new SendSmsClient.Builder()
                .endpoint("endpoint")
                .build();
        assertEquals(client.getEndpoint(), "endpoint");
    }

    @Test(expected = IllegalArgumentException.class)
    public void endpoint_null() throws Exception {
        new SendSmsClient.Builder()
                .endpoint(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void endpoint_empty() throws Exception {
        new SendSmsClient.Builder()
                .endpoint("");
    }

    @Test
    public void credentials() throws Exception {

        SendSmsClient client;

        // No user name, password set by default
        client = new SendSmsClient.Builder()
                .build();
        assertNull(client.getUserName());
        assertNull(client.getPassword());

        client = new SendSmsClient.Builder()
                .credentials("user123", "pass456")
                .build();
        assertEquals(client.getUserName(), "user123");
        assertEquals(client.getPassword(), "pass456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void userName_null() throws Exception {
        new SendSmsClient.Builder()
                .credentials(null, "pass456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void userName_empty() throws Exception {
        new SendSmsClient.Builder()
                .credentials("", "pass456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void password_null() throws Exception {
        new SendSmsClient.Builder()
                .credentials("user123", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void password_empty() throws Exception {
        new SendSmsClient.Builder()
                .credentials("user123", "");
    }

    @Test
    public void secure() throws Exception {

        SendSmsClient client;

        // Secure by default
        client = new SendSmsClient.Builder()
                .build();
        assertTrue(client.getSecure());

        client = new SendSmsClient.Builder()
                .secure(false)
                .build();
        assertFalse(client.getSecure());

        client = new SendSmsClient.Builder()
                .secure(true)
                .build();
        assertTrue(client.getSecure());
    }

    @Test
    public void path() throws Exception {

        SendSmsClient client;

        // Check correct default
        client = new SendSmsClient.Builder()
                .build();
        assertEquals("/submit_sm", client.getPath());

        client = new SendSmsClient.Builder()
                .path("/mypath")
                .build();
        assertEquals("/mypath", client.getPath());
    }

    @Test(expected = IllegalArgumentException.class)
    public void path_null() throws Exception {
        new SendSmsClient.Builder()
                .path(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void path_empty() throws Exception {
        new SendSmsClient.Builder()
                .path("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void path_invalid() throws Exception {
        new SendSmsClient.Builder()
                .path("mypath");
    }


    /*
    @Test
    public void test() throws Exception {
        SendSmsClient client = new SendSmsClient.Builder()
                .transport(SendSmsClient.TRANSPORT_SIMPLE_CLIENT)
                .endpoint("sendmsg.dialogue.net")
                .credentials("load.test", "rip3Feiy")
                .secure(false)
                .build();

        SendSmsRequest request = new SendSmsRequest(
                Arrays.asList("message", "message2"),
                Arrays.asList("447956247525", "34637975280"));
        request.setSender("Oliver");
        request.put("X-E3-Async-Submit", "off");

        SendSmsResponse response = client.sendSms(request);
        System.out.println(response);
    }
    */
}
