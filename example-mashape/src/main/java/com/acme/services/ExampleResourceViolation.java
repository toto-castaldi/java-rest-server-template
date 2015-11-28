package com.acme.services;

import com.github.totoCastaldi.restServer.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("example")
@Slf4j
public class ExampleResourceViolation {


    private final ApiResponse apiResponse;

    @Inject
    public ExampleResourceViolation(
            ApiResponse apiResponse
    ) {
        this.apiResponse = apiResponse;
    }

    @GET
    public Response get(
            @Context HttpServletRequest httpServletRequest
    ) {
        return apiResponse.ok();
    }

}