package com.acme.services;

import com.github.totoCastaldi.restServer.ApiServletContextListener;
import com.github.totoCastaldi.restServer.RestServerConf;
import com.github.totoCastaldi.restServer.plugin.PersistenModule;
import com.google.inject.AbstractModule;

/**
 * Created by github on 09/11/15.
 */

public class ExampleApiServletContextListener extends ApiServletContextListener {

    @Override
    public RestServerConf getAppConf() {
        RestServerConf.Builder builder = RestServerConf.builder();
        builder.add(ExampleResource.class.getPackage());
        builder.add(new AbstractModule() {
            @Override
            protected void configure() {
                bind(ExampleResourceSupport.class);
                bind(ExampleDao.class);
            }
        });
        builder.add(new PersistenModule());
        return builder.build();
    }
}
