package com.github.totoCastaldi.restServer.request;

import com.github.totoCastaldi.restServer.UserType;

/**
 * Created by github on 11/12/14.
 */
public interface AuthorizationRequest {

    boolean isPassed();
    String getUsername();
    String getErrorMessage();
    String getRequest();

}
