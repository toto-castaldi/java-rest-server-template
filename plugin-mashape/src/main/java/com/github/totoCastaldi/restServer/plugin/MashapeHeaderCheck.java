package com.github.totoCastaldi.restServer.plugin;

/**
 * Created by toto on 28/11/15.
 */

import com.github.totoCastaldi.restServer.response.ApiResponse;
import com.github.totoCastaldi.restServer.response.ErrorResponse;
import com.github.totoCastaldi.restServer.response.ErrorResponseEntry;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.io.IOException;

@Slf4j
public class MashapeHeaderCheck implements ContainerRequestFilter {

    public final static String configName = "MashapeHeaderCheck.configName";
    private final String mashapeApiSecret;
    private final ApiResponse apiResponse;

    @Inject
    public MashapeHeaderCheck(
            ApiResponse apiResponse,
            Injector injector,
            @Named(configName) String configName

    ) {
        this.apiResponse = apiResponse;
        injector.getBinding(Names.named(configName)).
        this.mashapeApiSecret = System.getProperty("X-Mashape-Proxy-Secret");
    }

    public void filter(ContainerRequestContext requestContext) throws IOException {
        String proxySecret = requestContext.getHeaderString("x-mashape-proxy-secret");

        log.info("check {} on {}", proxySecret, mashapeApiSecret);

        if (!StringUtils.equals(proxySecret, mashapeApiSecret)) {
            requestContext.abortWith(apiResponse.authenticationRequired(new ErrorResponse(ErrorResponseEntry.of("invalid source", "please use the service via Mashape https://market.mashape.com/toto"))));
        }
    }
}