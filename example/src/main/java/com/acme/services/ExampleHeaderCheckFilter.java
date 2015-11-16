package com.acme.services;

import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.io.IOException;

/**
 * Created by github on 08/11/15.
 */
@Slf4j
public class ExampleHeaderCheckFilter implements ContainerRequestFilter {

    private final String headerName;

    @Inject
    public ExampleHeaderCheckFilter(
            @Named(ExampleApiServletContextListener.CONF_TEST) String headerName
    ) {
        this.headerName = headerName;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String header = containerRequestContext.getHeaderString(headerName);

        log.info("ExampleHeaderCheckFilter {} = {}", headerName, header);
    }
}

