package com.github.totoCastaldi.restServer.response;

import com.google.common.collect.Iterables;
import lombok.*;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by github on 07/10/14.
 */


@NoArgsConstructor (access = AccessLevel.PRIVATE)
@ToString
public class ErrorResponse {

    private ErrorResponseEntry main;

    public String getCode() {
        return main.getCode();
    }

    public String getMessage() {
        return main.getMessage();
    }

    @Getter
    private ErrorResponseEntry[] errors;

    public ErrorResponse(ErrorResponseEntry main) {
        this.main = main;
        this.errors = new ErrorResponseEntry[0];
    }

    public ErrorResponse(ErrorResponseEntry... entries) {
        final List<ErrorResponseEntry> errors = Arrays.asList(entries);
        this.main = Iterables.getFirst(errors, null);
        this.errors = Iterables.toArray(Iterables.skip(errors, 1), ErrorResponseEntry.class);
    }


}
