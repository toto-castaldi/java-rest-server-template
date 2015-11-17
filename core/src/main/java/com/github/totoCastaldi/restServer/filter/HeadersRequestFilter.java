package com.github.totoCastaldi.restServer.filter;

import com.github.totoCastaldi.restServer.ApiCurrentExecution;
import com.github.totoCastaldi.restServer.response.ApiResponse;
import com.google.common.base.Enums;
import com.google.common.base.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import java.io.IOException;

/**
 * Created by toto on 17/11/14.
 */
@Slf4j
@PreMatching
public class HeadersRequestFilter implements ContainerRequestFilter {

    private final ApiResponse apiResponse;
    private final HttpServletRequest httpServletRequest;
    private final ApiCurrentExecution.Factory apiCurrentExecutionFactory;

    @Inject
    public HeadersRequestFilter(
            ApiResponse apiResponse,
            @Context HttpServletRequest httpServletRequest,
            ApiCurrentExecution.Factory apiCurrentExecutionFactory
    ) {
        this.apiResponse = apiResponse;
        this.httpServletRequest = httpServletRequest;
        this.apiCurrentExecutionFactory = apiCurrentExecutionFactory;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        final ApiCurrentExecution currentExecution = apiCurrentExecutionFactory.create(httpServletRequest);
        currentExecution.populateHttpHeaders(httpServletRequest);
    }
}
