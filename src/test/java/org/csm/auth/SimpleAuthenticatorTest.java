package org.csm.auth;

import io.dropwizard.auth.basic.BasicCredentials;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class SimpleAuthenticatorTest {
    private final SimpleAuthenticator authenticator = new SimpleAuthenticator();

    @Test
    void testAuthenticateValidCredentials() {
        BasicCredentials validCredentials = new BasicCredentials("apiuser", "apipassword");
        assertTrue(authenticator.authenticate(validCredentials).isPresent());
    }

    @Test
    void testAuthenticateInvalidUsername() {
        BasicCredentials invalidUsername = new BasicCredentials("wronguser", "apipassword");
        assertFalse(authenticator.authenticate(invalidUsername).isPresent());
    }

    @Test
    void testAuthenticateInvalidPassword() {
        BasicCredentials invalidPassword = new BasicCredentials("apiuser", "wrongpassword");
        assertFalse(authenticator.authenticate(invalidPassword).isPresent());
    }

    @Test
    void testAuthenticateInvalidCredentials() {
        BasicCredentials invalidCredentials = new BasicCredentials("wronguser", "wrongpassword");
        assertFalse(authenticator.authenticate(invalidCredentials).isPresent());
    }
}
