package com.acme.services;

import com.github.totoCastaldi.restServer.ApiGuice;
import com.github.totoCastaldi.restServer.model.CustomerDao;
import com.google.inject.AbstractModule;
import com.google.inject.Module;

/**
 * Created by github on 09/11/15.
 */

public class ExampleApiGuice extends ApiGuice {

    public ExampleApiGuice() {
    }

    @Override
    protected Package getPackage() {
        return ExampleResource.class.getPackage();
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
