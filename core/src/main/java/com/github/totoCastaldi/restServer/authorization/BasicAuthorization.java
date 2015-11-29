package com.github.totoCastaldi.restServer.authorization;

/**
 * Created by toto on 29/11/15.
 */
public interface BasicAuthorization extends UserProfile {

    AuthorizationResult checkCredential(String username, String password);

}
