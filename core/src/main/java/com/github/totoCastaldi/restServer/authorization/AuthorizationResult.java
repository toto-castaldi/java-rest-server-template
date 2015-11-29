package com.github.totoCastaldi.restServer.authorization;

import com.github.totoCastaldi.restServer.UserType;
import com.google.common.base.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

/**
 * Created by toto on 29/11/15.
 */
@Getter
@AllArgsConstructor (staticName = "of", access = AccessLevel.PRIVATE)
public class AuthorizationResult {

    private boolean passed;
    private String errorMessage;

    public static AuthorizationResult notPassed(String errorMessage) {
        return AuthorizationResult.of(false, errorMessage);
    }

    public static AuthorizationResult passed() {
        return AuthorizationResult.of(true, StringUtils.EMPTY);
    }
}
