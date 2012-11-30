package net.dialogue.toolkit.sms;

import org.junit.Test;
import static org.junit.Assert.*;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

public class TestClient {

    @Test
    public void transport() throws Exception {

        SendSmsClient client;

        client = new SendSmsClient();
        assertTrue(((RestTemplate) client.getRestOperations()).getRequestFactory()
                instanceof SimpleClientHttpRequestFactory);

        RestOperations restOperations = new RestTemplate();
        client = new SendSmsClient();
        client.setRestOperations(restOperations);
        assertSame(client.getRestOperations(), restOperations);
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
