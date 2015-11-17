package com.github.totoCastaldi.restServer.response;

import com.google.common.base.Optional;

import java.util.Locale;

/**
 * Created by toto on 17/11/15.
 */
public interface ApiErrorMessage {
    String getMessage(ErrorResponseCode code, Optional<Locale> locale);
}
