package org.csm.auth;


import io.dropwizard.auth.Authorizer;
import io.dropwizard.auth.PrincipalImpl;

public class SimpleAuthorizer implements Authorizer<PrincipalImpl> {

    @Override
    public boolean authorize(PrincipalImpl principal, String role) {
        return true; // Authorize all users for now since there are no roles
    }
}
