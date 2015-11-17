package com.acme.services;

import com.github.totoCastaldi.restServer.ApiCurrentExecution;
import com.github.totoCastaldi.restServer.annotation.BasicAuthenticated;
import com.github.totoCastaldi.restServer.annotation.UserProfileCustomer;
import com.github.totoCastaldi.restServer.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by github on 05/12/14.
 */
@Path(ExampleApiPath.SEMAPHORE)
@Slf4j
public class ExampleResourceSemaphore {

    private final ApiResponse apiResponse;
    private final ExampleSemaphoreSupport exampleSemaphoreSupport;

    @Inject
    public ExampleResourceSemaphore(
            ApiResponse apiResponse,
            ExampleSemaphoreSupport exampleSemaphoreSupport
    ) {
        this.apiResponse = apiResponse;
        this.exampleSemaphoreSupport = exampleSemaphoreSupport;
    }

    @PUT
    @UserProfileCustomer
    @BasicAuthenticated
    @Path(ExampleApiPath.SWITCH)
    @Produces(MediaType.APPLICATION_JSON)
    public Response switchState(
            @Context HttpServletRequest httpServletRequest
    ) {
        final ApiCurrentExecution apiCurrentExecution = ApiCurrentExecution.on(httpServletRequest);
        exampleSemaphoreSupport.switchState(apiCurrentExecution.getUsername().get());
        return apiResponse.createdReturns(httpServletRequest, ExampleApiPath.SEMAPHORE);
    }

    @GET
    @UserProfileCustomer
    @BasicAuthenticated
    @Produces(MediaType.APPLICATION_JSON)
    public Response status(
            @Context HttpServletRequest httpServletRequest
    ) {
        final ApiCurrentExecution apiCurrentExecution = ApiCurrentExecution.on(httpServletRequest);
        return apiResponse.ok(ExampleResponse.of(exampleSemaphoreSupport.status(apiCurrentExecution.getUsername().get())));
    }

}