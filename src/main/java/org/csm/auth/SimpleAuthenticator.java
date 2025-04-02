package org.csm.auth;

import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.auth.basic.BasicCredentials;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

public class SimpleAuthenticator implements Authenticator<BasicCredentials, PrincipalImpl> {

    private static final String USERNAME = "apiuser";
    private static final String PASSWORD = "apipassword";

    @Override
    public Optional<PrincipalImpl> authenticate(BasicCredentials credentials) {
        String providedUsername = credentials.getUsername();
        String providedPassword = credentials.getPassword();

        if (USERNAME.equals(providedUsername) && PASSWORD.equals(providedPassword)) {
            return Optional.of(new PrincipalImpl(providedUsername));
        }
        return Optional.empty();
    }

    private BasicCredentials parseAuthorizationHeader(String authHeader) {
        String encodedCredentials = authHeader.substring("Basic ".length()).trim();
        String decodedCredentials = new String(Base64.getDecoder().decode(encodedCredentials), StandardCharsets.UTF_8);
        String[] split = decodedCredentials.split(":", 2);
        return new BasicCredentials(split[0], split[1]);
    }

    public BasicCredentials extractCredentialsFromHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Basic ")) {
            return parseAuthorizationHeader(authHeader);
        }
        return null;
    }
}