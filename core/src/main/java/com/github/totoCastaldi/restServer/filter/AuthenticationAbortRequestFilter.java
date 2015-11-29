package com.github.totoCastaldi.restServer.filter;

import com.github.totoCastaldi.restServer.ApiCurrentExecution;
import com.github.totoCastaldi.restServer.ApiHeaderUtils;
import com.github.totoCastaldi.restServer.AuthenticationType;
import com.github.totoCastaldi.restServer.response.ApiResponse;
import com.github.totoCastaldi.restServer.response.ErrorResponseCode;
import com.github.totoCastaldi.restServer.response.ErrorResponseEntry;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

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
            log.info("login post match {} {} {}", authorizationRequest, currentExecution, currentExecution.getAuthenticationErrors());

            final Iterable<ErrorResponseEntry> errorResponseEntries = Iterables.transform(currentExecution.getAuthenticationErrors(), new Function<String, ErrorResponseEntry>() {
                @Nullable
                @Override
                public ErrorResponseEntry apply(@Nullable String input) {
                    log.info("error auth {}", input);
                    return ErrorResponseEntry.of(ErrorResponseCode.AUTHENTICATION_REQUIRED.name(), input);
                }
            });

            containerRequestContext.abortWith(
                    apiResponse.unauthorize(
                            apiHeaderUtils.parseAuthorizationRequestes(authorizationRequest, authenticationType),
                            Iterables.toArray(errorResponseEntries, ErrorResponseEntry.class)
                    )
            );
        }

    }
}