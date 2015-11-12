package com.acme.services;

import com.github.totoCastaldi.restServer.ApiServletContextListener;
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

    @Override
    protected List<Class<? extends ContainerRequestFilter>> getContainerRequestFilters() {
        return Lists.newArrayList(ExampleHeaderCheckFilter.class);
    }

    @Override
    protected List<Package> getPackages() {
        return Lists.newArrayList(ExampleResource.class.getPackage());
    }

    @Override
    protected String getPasswordSeed() {
        return "13354-PWD";
    }

    @Override
    protected Class<? extends CustomerDao> getCustomerDaoClass() {
        return ExampleCustomerDao.class;
    }

    @Override
    public Module getAppModule() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(ExampleResourceSupport.class);
            }
        };
    }
}
