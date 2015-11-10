package com.github.totoCastaldi.restServer.filter;

import com.github.totoCastaldi.restServer.ApiHeaderUtils;
import com.github.totoCastaldi.restServer.UserType;
import com.github.totoCastaldi.restServer.annotation.UserProfileCustomer;
import com.github.totoCastaldi.restServer.response.ApiResponse;
import com.github.totoCastaldi.restServer.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by github on 08/11/15.
 */
@Slf4j
@UserProfileCustomer
public class ProfileCustomerAbortRequestFilter extends BaseProfileAbortRequestFilter {

    @Inject
    public ProfileCustomerAbortRequestFilter(
            ApiHeaderUtils apiHeaderUtils,
            ApiResponse apiResponse,
            HttpServletRequest httpRequest
    ) {
        super(
                apiHeaderUtils,
                apiResponse,
                httpRequest,
                UserType.CUSTOMER,
                ErrorResponse.DetailsCode.PROFILE_NOT_CUSTOMER
        );
    }

}

