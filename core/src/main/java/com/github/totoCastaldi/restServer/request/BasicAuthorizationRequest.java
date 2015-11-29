package com.github.totoCastaldi.restServer.request;

import com.github.totoCastaldi.restServer.authorization.AuthorizationResult;
import com.github.totoCastaldi.restServer.authorization.BasicAuthorization;
import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.github.totoCastaldi.restServer.ApiPassword;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.glassfish.jersey.internal.util.Base64;

import javax.annotation.Nullable;

/**
 * Created by github on 08/11/15.
 */
@Slf4j
@ToString
public class BasicAuthorizationRequest implements AuthorizationRequest {

    @Getter
    private String username;
    @Getter
    private String request;
    @Getter
    private String errorMessage;
    @Getter
    private boolean passed;

    public interface Factory {

        BasicAuthorizationRequest create(@Assisted("md5Credentials") String md5Credentials);

    }

    @AssistedInject
    public BasicAuthorizationRequest(
            @Nullable BasicAuthorization basicAuthorization,
            @Assisted("md5Credentials") String md5Credentials
    ) {
        this.request = md5Credentials;
        String credentialDecode = Base64.decodeAsString(md5Credentials);
        log.debug("credential {}", credentialDecode);
        String[] credentials = StringUtils.splitByWholeSeparator(credentialDecode, ":");
        this.passed = false;
        this.errorMessage = StringUtils.EMPTY;
        this.username = null;

        if (credentials != null && credentials.length > 1) {
            this.username = credentials[0];

            final String password = credentials[1];
            if (basicAuthorization != null) {
                final AuthorizationResult authorizationResult = basicAuthorization.checkCredential(username, password);
                if (authorizationResult.isPassed()) {
                    this.passed = true;
                }
                this.errorMessage = authorizationResult.getErrorMessage();
            }
        }

    }

}
