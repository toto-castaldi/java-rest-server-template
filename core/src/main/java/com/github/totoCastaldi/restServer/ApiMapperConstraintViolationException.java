package com.github.totoCastaldi.restServer;

import com.github.totoCastaldi.restServer.response.ErrorResponse;
import com.github.totoCastaldi.restServer.response.ErrorResponseCode;
import com.github.totoCastaldi.restServer.response.ErrorResponseEntry;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

/**
 * Created by github on 07/10/14.
 */
@Slf4j
@Provider
public class ApiMapperConstraintViolationException implements ExceptionMapper<ConstraintViolationException> {

    private final ErrorResponseEntry.Factory errorResponseEntryFactory;

    @Inject
    public ApiMapperConstraintViolationException(
            ErrorResponseEntry.Factory errorResponseEntryFactory
    ) {
        this.errorResponseEntryFactory = errorResponseEntryFactory;
    }

    @Override
    public Response toResponse(ConstraintViolationException e) {
        List<ErrorResponseEntry> entries = Lists.newArrayList();

        entries.add(errorResponseEntryFactory.create(ErrorResponseCode.CONSTRAINT_VALIDATION_ERROR));

        for (final ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            entries.add(errorResponseEntryFactory.create(constraintViolation));
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON + "; charset=" + Charset.defaultCharset().displayName())
                .entity(new ErrorResponse(Iterables.toArray(entries, ErrorResponseEntry.class))).build();
    }

}
