package com.github.totoCastaldi.restServer;

import com.github.totoCastaldi.restServer.request.AuthorizationRequest;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Created by github on 11/12/14.
 */
@RequiredArgsConstructor
public class AuthorizationRequestInfo {

    @NonNull
    @Getter
    AuthorizationRequest authorizationRequest;
    @NonNull
    @Getter
    AuthenticationType authenticationType;
}
