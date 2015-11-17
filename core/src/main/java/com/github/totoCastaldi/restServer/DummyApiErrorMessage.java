package com.github.totoCastaldi.restServer;

import com.github.totoCastaldi.restServer.response.ApiErrorMessage;
import com.github.totoCastaldi.restServer.response.ErrorResponseCode;
import com.google.common.base.Optional;

import java.util.Locale;

/**
 * Created by toto on 17/11/15.
 */
public class DummyApiErrorMessage implements ApiErrorMessage {
    @Override
    public String getMessage(ErrorResponseCode code, Optional<Locale> locale) {
        return code.name();
    }
}
