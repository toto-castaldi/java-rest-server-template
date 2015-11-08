package it.toto.services.restServer;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import it.toto.services.restServer.model.CustomerDao;

/**
 * Created by toto on 09/11/15.
 */

public class ExampleApiGuice extends ApiGuice {

    public ExampleApiGuice() {
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
