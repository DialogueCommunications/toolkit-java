package net.dialogue.toolkit.sms;

import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * User: oliver
 * Date: 20/11/12
 * Time: 12:57
 */
public class TestSubmission {

    @Test
    public void submission() throws IOException {

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("test.properties"));
        } catch (IOException e) {
            System.out.println(e.toString());
            return;
        }

        final String endpoints = properties.getProperty("endpoints");
        if (endpoints == null || endpoints.length() == 0) {
            System.out.println("Property 'endpoints' not defined in test.properties.");
            return;
        }

        final String login = properties.getProperty("login");
        if (login == null || login.length() == 0) {
            System.out.println("Property 'login' not defined in test.properties.");
            return;
        }

        final String password = properties.getProperty("password");
        if (password == null || password.length() == 0) {
            System.out.println("Property 'password' not defined in test.properties.");
            return;
        }

        final String recipients = properties.getProperty("recipients");
        if (recipients == null || recipients.length() == 0) {
            System.out.println("Property 'recipients' not defined in test.properties.");
            return;
        }

        for(String endpoint : endpoints.split(",")) {

            // TRANSPORT_SIMPLE_CLIENT
            testClient(new SendSmsClient.Builder()
                    .endpoint(endpoint)
                    .transport(SendSmsClient.TRANSPORT_SIMPLE_CLIENT)
                    .credentials(login, password)
                    .build(), recipients);

            // TRANSPORT_COMMONS_CLIENT
            testClient(new SendSmsClient.Builder()
                    .endpoint(endpoint)
                    .transport(SendSmsClient.TRANSPORT_COMMONS_CLIENT)
                    .credentials(login, password)
                    .build(), recipients);

            // TRANSPORT_HTTP_COMPONENTS_CLIENT
            testClient(new SendSmsClient.Builder()
                    .endpoint(endpoint)
                    .transport(SendSmsClient.TRANSPORT_HTTP_COMPONENTS_CLIENT)
                    .credentials(login, password)
                    .build(), recipients);

            testWrongCredentials(endpoint);
        }
    }

    private void testClient(SendSmsClient client, String recipients) throws IOException {
        client.setSecure(false);
        testClient2(client, recipients);
        /*
        client.setSecure(true); // TODO: depends on RM #1950
        testClient(client, recipients);
        */
    }

    public void testWrongCredentials(String endpoint) throws IOException {
        try {
            testClient2(new SendSmsClient.Builder()
                    .endpoint(endpoint)
                    .credentials("wrong", "wrong")
                    .secure(false)
                    .build(),
                    "1234"
            );
            assertTrue(false);
        } catch (HttpClientErrorException e) {
            assertNotNull(e.getStatusCode());
            assertEquals(e.getStatusCode().value(), 401);
            assertEquals(e.getStatusText(), "Authorization Required");
        }
    }

    @Test(expected = UnknownHostException.class)
    public void wrongEndpoint() throws IOException {
        testClient2(new SendSmsClient.Builder()
                .endpoint("wrong")
                .credentials("wrong", "wrong")
                .secure(false)
                .build(),
                "1234"
        );
    }

    private static void testClient2(SendSmsClient client, String _recipients) throws HttpClientErrorException, IOException {

        List<String> recipients = new ArrayList<String>(
                Arrays.asList(_recipients.split(",")));
        recipients.add("999"); // Hard-coded invalid recipient

        SendSmsRequest request = new SendSmsRequest(
                "This is a test message.", recipients);

        SendSmsResponse response = client.sendSms(request);

        assert response != null && response.getMessages() != null &&
                response.getMessages().size() == 3;

        for(int n = 0; n < recipients.size(); n++) {

            Sms sms = response.getMessages().get(n);
            if(n < recipients.size() - 1) {
                // Except succeeded submission for last recipient
                assertTrue(sms.isSuccessful());
                assertEquals(sms.getRecipient(), recipients.get(n));
                assertNotNull(sms.getId());
                assertTrue(sms.getId().length() > 0);
                assertEquals(sms.getSubmissionReport(), "00");
                assertNull(sms.getErrorDescription());
            } else {
                // Except failed submission for last recipient
                assertFalse(sms.isSuccessful());
                assertEquals(sms.getRecipient(), recipients.get(n));
                assertNull(sms.getId());
                assertEquals(sms.getSubmissionReport(), "43");
                assertNotNull(sms.getErrorDescription());
            }
        }
    }
}
