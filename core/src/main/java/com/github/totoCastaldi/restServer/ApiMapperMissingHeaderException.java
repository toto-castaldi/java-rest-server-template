package com.github.totoCastaldi.restServer;

import com.github.totoCastaldi.restServer.response.ErrorResponse;
import com.github.totoCastaldi.restServer.response.ErrorResponseEntry;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by github on 07/10/14.
 */
@Slf4j
@Provider
@Produces (MediaType.APPLICATION_JSON)
public class ApiMapperMissingHeaderException implements ExceptionMapper<MissingHeaderException> {

    private final ErrorResponseEntry.Factory errorResponseFactory;

    @Inject
    public ApiMapperMissingHeaderException (
           ErrorResponseEntry.Factory errorResponseFactory
    ) {

        this.errorResponseFactory = errorResponseFactory;
    }

    @Override
    public Response toResponse(MissingHeaderException e) {

        log.error("Trapped an unexpected throws.", e);

        return Response.status(Response.Status.BAD_REQUEST).entity(errorResponseFactory.create(e)).build();
    }

}
