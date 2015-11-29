package com.github.totoCastaldi.restServer.authorization;

import com.github.totoCastaldi.restServer.UserType;
import com.google.common.base.Optional;

/**
 * Created by github on 08/11/15.
 */
public interface UserProfile {

    public Optional<UserType> resolve(String username);
}
