package com.github.totoCastaldi.restServer.filter;

import com.github.totoCastaldi.restServer.ApiHeaderUtils;
import com.github.totoCastaldi.restServer.AuthenticationType;
import com.github.totoCastaldi.restServer.annotation.BasicAuthenticated;
import com.github.totoCastaldi.restServer.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

/**
 * Created by github on 08/11/15.
 */
@Slf4j
@BasicAuthenticated
public class BasicAuthenticationAbortRequestFilter extends AuthenticationAbortRequestFilter {

    @Inject
    public BasicAuthenticationAbortRequestFilter(
            ApiHeaderUtils apiHeaderUtils,
            ApiResponse apiResponse,
            @Context HttpServletRequest httpServletRequest) {
        super(apiHeaderUtils,apiResponse,httpServletRequest, AuthenticationType.BASIC);
    }
}
