package com.github.totoCastaldi.restServer;

import com.github.totoCastaldi.restServer.response.ErrorResponse;
import com.github.totoCastaldi.restServer.response.ErrorResponseEntry;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.nio.charset.Charset;

/**
 * Created by github on 07/10/14.
 */
@Slf4j
@Provider
public class ApiMapperThrowable implements ExceptionMapper<Throwable> {

    private final ErrorResponseEntry.Factory errorResponseEntryFactory;

    @Inject
    public ApiMapperThrowable(
            ErrorResponseEntry.Factory errorResponseEntryFactory
    ) {
        this.errorResponseEntryFactory = errorResponseEntryFactory;
    }

    @Override
    public Response toResponse(Throwable e) {

        log.error("Trapped an unexpected throws.", e);

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON + "; charset=" + Charset.defaultCharset().displayName()).entity(new ErrorResponse(errorResponseEntryFactory.create(e))).build();
    }

}
