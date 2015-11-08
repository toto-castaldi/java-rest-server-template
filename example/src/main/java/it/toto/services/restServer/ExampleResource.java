package it.toto.services.restServer;

import it.toto.services.restServer.annotation.BasicAuthenticated;
import it.toto.services.restServer.annotation.UserProfileCustomer;
import it.toto.services.restServer.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by toto on 05/12/14.
 */
@Path(ExampleApiPath.SEMAPHORE)
@Slf4j
public class ExampleResource {

    private final ApiResponse apiResponse;
    private final ExampleResourceSupport exampleResourceSupport;

    @Inject
    public ExampleResource(
            ApiResponse apiResponse,
            ExampleResourceSupport exampleResourceSupport
    ) {
        this.apiResponse = apiResponse;
        this.exampleResourceSupport = exampleResourceSupport;
    }

    @PUT
    @UserProfileCustomer
    @BasicAuthenticated
    @Path(ExampleApiPath.ON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response on(
            @Context HttpServletRequest httpServletRequest
    ) {
        final ApiCurrentExecution apiCurrentExecution = ApiCurrentExecution.on(httpServletRequest);
        exampleResourceSupport.on(apiCurrentExecution.getUsername().get());
        return apiResponse.createdReturns(httpServletRequest, ExampleApiPath.SEMAPHORE);
    }

    @PUT
    @UserProfileCustomer
    @BasicAuthenticated
    @Path(ExampleApiPath.OFF)
    @Produces(MediaType.APPLICATION_JSON)
    public Response off(
            @Context HttpServletRequest httpServletRequest
    ) {
        final ApiCurrentExecution apiCurrentExecution = ApiCurrentExecution.on(httpServletRequest);
        exampleResourceSupport.off(apiCurrentExecution.getUsername().get());
        return apiResponse.createdReturns(httpServletRequest, ExampleApiPath.SEMAPHORE);
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
        exampleResourceSupport.switchState(apiCurrentExecution.getUsername().get());
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
        return apiResponse.ok(ExampleResponse.of(exampleResourceSupport.status(apiCurrentExecution.getUsername().get())));
    }

}