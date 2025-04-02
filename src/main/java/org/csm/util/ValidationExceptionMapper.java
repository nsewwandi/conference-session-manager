package org.csm.util;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        StringBuilder message = new StringBuilder();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            message.append(violation.getMessage()).append("\n");
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(message.toString())
                .build();
    }
}
