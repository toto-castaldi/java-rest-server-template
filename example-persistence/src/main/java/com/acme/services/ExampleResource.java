package com.acme.services;

import com.github.totoCastaldi.restServer.ApiCurrentExecution;
import com.github.totoCastaldi.restServer.annotation.BasicAuthenticated;
import com.github.totoCastaldi.restServer.annotation.UserProfileCustomer;
import com.github.totoCastaldi.restServer.response.ApiResponse;
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
@Path("person")
@Slf4j
public class ExampleResource {

    private final ApiResponse apiResponse;
    private final ExampleDao exampleDao;

    @Inject
    public ExampleResource(
            ApiResponse apiResponse,
            ExampleDao exampleDao
    ) {
        this.apiResponse = apiResponse;
        this.exampleDao = exampleDao;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response on(
            @Context HttpServletRequest httpServletRequest,
            @NotNull @Valid ExampleResourceCreateRequest request
    ) {
        log.info("create person {}", request);
        String id = exampleDao.create(request);
        return apiResponse.createdReturns(httpServletRequest, "person", id);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response status(
            @Context HttpServletRequest httpServletRequest,
            @PathParam("id") String id
    ) {
        log.info("id = {}", id);
        String name = exampleDao.getNameById(id);
        ExampleResponse exampleResponse = ExampleResponse.of(name);
        return apiResponse.ok(exampleResponse);
    }

}