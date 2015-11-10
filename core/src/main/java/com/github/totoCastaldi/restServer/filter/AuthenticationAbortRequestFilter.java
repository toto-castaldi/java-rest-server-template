package com.github.totoCastaldi.restServer.filter;

import com.github.totoCastaldi.restServer.ApiCurrentExecution;
import com.github.totoCastaldi.restServer.ApiHeaderUtils;
import com.github.totoCastaldi.restServer.AuthenticationType;
import com.github.totoCastaldi.restServer.response.ApiResponse;
import com.github.totoCastaldi.restServer.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;

/**
 * Created by github on 08/11/15.
 */
@Slf4j
abstract class AuthenticationAbortRequestFilter implements ContainerRequestFilter {

    private final ApiHeaderUtils apiHeaderUtils;
    private final ApiResponse apiResponse;
    private final HttpServletRequest httpRequest;
    private final AuthenticationType authenticationType;

    public AuthenticationAbortRequestFilter(
            ApiHeaderUtils apiHeaderUtils,
            ApiResponse apiResponse,
            HttpServletRequest httpRequest,
            AuthenticationType authenticationType
    ) {
        this.apiHeaderUtils = apiHeaderUtils;
        this.apiResponse = apiResponse;
        this.httpRequest = httpRequest;
        this.authenticationType = authenticationType;

    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        ApiCurrentExecution currentExecution = ApiCurrentExecution.on(httpRequest);

        if (!currentExecution.isAuthenticationPassed()) {
            String authorizationRequest = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
            log.info("login post match {} {}", authorizationRequest, currentExecution);

            containerRequestContext.abortWith(apiResponse.unauthorize(apiHeaderUtils.parseAuthorization(authorizationRequest, authenticationType), ErrorResponse.of(ErrorResponse.DetailsCode.AUTHENTICATION_REQUIRED, ErrorResponse.DetailsCode.INVALID_CREDENTIALS)));
        }

    }
}

