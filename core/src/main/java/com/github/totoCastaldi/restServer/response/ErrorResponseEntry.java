package com.github.totoCastaldi.restServer.response;


import com.github.totoCastaldi.restServer.ApiCurrentExecution;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import java.util.Locale;

/**
 *  Created by toto on 17/11/15.
 */
@Getter
@ToString
@AllArgsConstructor (staticName = "of")
public class ErrorResponseEntry {

    @NonNull
    private String code;
    @NonNull
    private String message;

    public interface Factory {

        ErrorResponseEntry create(final Throwable e);

        ErrorResponseEntry create(final HttpServletRequest httpServletRequest, final ErrorResponseCode code);

        ErrorResponseEntry create(final ErrorResponseCode code);

        ErrorResponseEntry create(final ConstraintViolation<?> constraintViolation);

        ErrorResponseEntry create(final String description);
    }

    @AssistedInject
    public ErrorResponseEntry(
            @Assisted final Throwable e

    ) {
        this.code = ErrorResponseCode.EXCEPTION.name();
        this.message = e.getMessage();
    }

    @AssistedInject
    public ErrorResponseEntry(
            @Assisted("description") String description

    ) {
        this.code = description;
        this.message = description;
    }

    @AssistedInject
    public ErrorResponseEntry(
            @Assisted final HttpServletRequest httpServletRequest,
            @Assisted final ErrorResponseCode code,
            ApiErrorMessage apiErrorMessage
    ) {
        final ApiCurrentExecution apiCurrentExecution = ApiCurrentExecution.on(httpServletRequest);
        this.code = code.name();
        this.message = apiErrorMessage.getMessage(code, apiCurrentExecution.getLocale());
    }

    @AssistedInject
    public ErrorResponseEntry(
            @Assisted final ErrorResponseCode code,
            ApiErrorMessage apiErrorMessage
    ) {
        this.code = code.name();
        this.message = apiErrorMessage.getMessage(code, Optional.<Locale>absent());
    }

    @AssistedInject
    public ErrorResponseEntry(
            @Assisted final ConstraintViolation<?> constraintViolation
    ) {
        this.code = ErrorResponseCode.CONSTRAINT_VALIDATION_DETAIL_ERROR.name();
        this.message = Iterables.getLast(constraintViolation.getPropertyPath()).getName() + " " + constraintViolation.getMessage();
    }

}