package com.acme.services;

import com.github.totoCastaldi.restServer.UserType;
import com.github.totoCastaldi.restServer.authorization.BasicAuthorization;
import com.google.common.base.Optional;
import org.apache.commons.lang.StringUtils;

/**
 * Created by toto on 29/11/15.
 */
public class ExampleBasicAuthorization implements BasicAuthorization {
    @Override
    public boolean checkCredential(String username, String password) {
        return StringUtils.equals(username, "toto") && StringUtils.equals(password, "1234");
    }

    @Override
    public Optional<UserType> resolve(String username) {
        if (StringUtils.equals(username, "toto")) {
            return Optional.of(UserType.CUSTOMER);
        } else {
            return Optional.absent();
        }
    }
}
