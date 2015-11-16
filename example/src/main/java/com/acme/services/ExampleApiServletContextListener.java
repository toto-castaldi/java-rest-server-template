package com.acme.services;

import com.github.totoCastaldi.restServer.ApiServletContextListener;
import com.github.totoCastaldi.restServer.RestServerConf;
import com.github.totoCastaldi.restServer.model.CustomerDao;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Module;

import javax.ws.rs.container.ContainerRequestFilter;
import java.util.List;

/**
 * Created by github on 09/11/15.
 */

public class ExampleApiServletContextListener extends ApiServletContextListener {

    public static final String CONF_TEST = "TEST";

    @Override
    public RestServerConf getAppConf() {
        RestServerConf.Builder builder = RestServerConf.builder();
        builder.add(ExampleResource.class.getPackage());
        builder.setCustomerDao(ExampleCustomerDao.class);
        builder.setPassworSeed("13354-PWD");
        builder.add(ExampleHeaderCheckFilter.class);
        builder.add(new AbstractModule() {
            @Override
            protected void configure() {
                bind(ExampleResourceSupport.class);
                bind(ExampleHeaderCheckFilter.class);
            }
        });
        builder.addStringConf(CONF_TEST);
        return builder.build();
    }
}
