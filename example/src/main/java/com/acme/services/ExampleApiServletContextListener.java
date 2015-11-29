package com.acme.services;

import com.github.totoCastaldi.restServer.ApiServletContextListener;
import com.github.totoCastaldi.restServer.RestServerConf;
import com.google.inject.AbstractModule;

/**
 * Created by github on 09/11/15.
 */

public class ExampleApiServletContextListener extends ApiServletContextListener {

    public static final String CONF_TEST = "TEST";

    @Override
    public RestServerConf getAppConf() {
        RestServerConf.Builder builder = RestServerConf.builder();
        builder.add(ExampleResourceSemaphore.class.getPackage());
        builder.add(ExampleHeaderCheckFilter.class);
        builder.add(new AbstractModule() {
            @Override
            protected void configure() {
                bind(ExampleSemaphoreSupport.class);
                bind(ExampleHeaderCheckFilter.class);
            }
        });
        builder.addStringConf(CONF_TEST);
        builder.setBasicAutharization(ExampleBasicAuthorization.class);
        return builder.build();
    }
}
