package com.github.totoCastaldi.restServer.filter;

import com.github.totoCastaldi.restServer.authorization.UserProfile;
import com.google.common.collect.Lists;
import com.github.totoCastaldi.restServer.*;
import com.github.totoCastaldi.restServer.request.AuthorizationRequest;
import com.github.totoCastaldi.restServer.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.List;

/**
 * Created by github on 08/11/15.
 */
@Slf4j
public class AuthenticationAndProfileRequestFilter implements ContainerRequestFilter {

    private final ApiHeaderUtils apiHeaderUtils;
    private final ApiResponse apiResponse;
    private final UserProfile userProfile;
    private final HttpServletRequest httpServletRequest;

    @Inject
    public AuthenticationAndProfileRequestFilter(
            ApiHeaderUtils apiHeaderUtils,
            ApiResponse apiResponse,
            @Nullable UserProfile userProfile,
            @Context HttpServletRequest httpServletRequest
    ) {
        this.apiHeaderUtils = apiHeaderUtils;
        this.apiResponse = apiResponse;
        this.userProfile = userProfile;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isNotBlank(authorizationHeader)) {
            log.info("login {}", authorizationHeader);

            ApiCurrentExecution currentExecution = ApiCurrentExecution.on(httpServletRequest);

            Iterable<AuthorizationRequestInfo> requests = apiHeaderUtils.parseAuthorization(authorizationHeader);

            boolean passed = false;
            String username = null;

            List<AuthenticationType> authenticationPassed = Lists.newArrayList();
            List<AuthenticationType> authenticationNotPassed = Lists.newArrayList();

            for (AuthorizationRequestInfo request : requests) {
                AuthorizationRequest authorizationRequest = request.getAuthorizationRequest();
                final AuthenticationType authenticationType = request.getAuthenticationType();
                if (authorizationRequest.isPassed()) {
                    username = authorizationRequest.getUsername();
                    authenticationPassed.add(authenticationType);
                    passed = true;
                } else {
                    authenticationNotPassed.add(authenticationType);
                }
            }
            if (passed) {
                currentExecution.setUsername(username);
                currentExecution.authenticationPassed(authenticationPassed);
                currentExecution.authenticationNotPassed(authenticationNotPassed);
                if (userProfile != null) {
                    currentExecution.setUserType(userProfile.resolve(username).get());
                } else {
                    currentExecution.setUserType(UserType.NONE);
                }
            }
        } else {
            log.debug("no authorization header {}", containerRequestContext.getHeaders());
        }

    }
}

