package com.github.totoCastaldi.restServer.plugin;

/**
 * Created by toto on 28/11/15.
 */

import com.github.totoCastaldi.restServer.response.ApiResponse;
import com.github.totoCastaldi.restServer.response.ErrorResponse;
import com.github.totoCastaldi.restServer.response.ErrorResponseEntry;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.io.IOException;

@Slf4j
public class MashapeHeaderCheck implements ContainerRequestFilter {

    public final static String headerConfigName = "MashapeHeaderCheck.headerConfigName";
    public final static String switchConfigName = "MashapeHeaderCheck.switchConfigName";
    private final String mashapeApiSecret;
    private final ApiResponse apiResponse;
    private final Boolean switchControl;

    @Inject
    public MashapeHeaderCheck(
            ApiResponse apiResponse,
            Injector injector,
            @Named(MashapeHeaderCheck.headerConfigName) String headerConfigName,
            @Named(MashapeHeaderCheck.switchConfigName) String switchConfigName
    ) {
        this.apiResponse = apiResponse;
        this.mashapeApiSecret = injector.getInstance(Key.get(String.class, Names.named(headerConfigName)));
        this.switchControl = injector.getInstance(Key.get(Boolean.class, Names.named(switchConfigName)));
    }

    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (!BooleanUtils.isFalse(switchControl)) {
            String proxySecret = requestContext.getHeaderString("x-mashape-proxy-secret");

            log.info("check {} on {}", proxySecret, mashapeApiSecret);

            if (!StringUtils.equals(proxySecret, mashapeApiSecret)) {
                requestContext.abortWith(apiResponse.authenticationRequired(new ErrorResponse(ErrorResponseEntry.of("invalid source", "please use the service via Mashape https://market.mashape.com/toto"))));
            }
        }
    }
}