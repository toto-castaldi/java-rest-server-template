package com.acme.services;

import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by github on 05/12/14.
 */
@Path(ExampleApiPath.VALIDATION)
@Slf4j
public class ExampleResourceViolation {


    @Inject
    public ExampleResourceViolation(
    ) {
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response get(
            @Context HttpServletRequest httpServletRequest,
            @Valid @NotNull ViolationRequest request
    ) {
        return null;
    }

}