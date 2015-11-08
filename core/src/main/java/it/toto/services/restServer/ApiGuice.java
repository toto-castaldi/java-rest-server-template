package it.toto.services.restServer;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import it.toto.commons.ConfKey;
import it.toto.commons.GuiceInjector;
import it.toto.commons.MemoryShutdownableRepository;
import it.toto.commons.ShutdownableRepository;
import it.toto.services.restServer.model.CustomerDao;
import it.toto.services.restServer.request.BasicAuthorizationRequest;
import it.toto.services.restServer.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.*;

/**
 * Created by toto on 05/12/14.
 */
@Slf4j
public abstract class ApiGuice extends GuiceServletContextListener {

    public static Injector injector;

    @Override
    protected Injector getInjector() {
        log.info("Getting injector");

        injector = Guice.createInjector(
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        final FactoryModuleBuilder factoryModuleBuilder = new FactoryModuleBuilder();

                        CompositeConfiguration compositeConfiguration = new CompositeConfiguration();
                        compositeConfiguration.addConfiguration(new SystemConfiguration());
                        compositeConfiguration.addConfiguration(new EnvironmentConfiguration());
                        try {
                            compositeConfiguration.addConfiguration(new PropertiesConfiguration("default.properties"));
                        } catch (ConfigurationException e) {
                            log.warn("",e);
                        }
                        for (String confKey : ConfKey.values()) {
                            bind(String.class).annotatedWith(Names.named(confKey)).toInstance(compositeConfiguration.getString(confKey));
                        }

                        bind(ApiResponse.class);
                        bind(UserProfile.class);
                        bind(ApiHeaderUtils.class);
                        bind(ApiScheduler.class);
                        bind(TimeProvider.class);
                        bind(ShutdownableRepository.class).to(MemoryShutdownableRepository.class);
                        bind(CustomerDao.class).to(getCustomerDaoClass());
                        bind(ApiValidation.class).toInstance(new ApiValidation(getPasswordSeed()));

                        install(factoryModuleBuilder.build(BasicAuthorizationRequest.Factory.class));
                        install(getAppModule());
                    }
                },
                new ServletModule() {
                    @Override
                    protected void configureServlets() {
                    }
                }
        );

        GuiceInjector.setIstance(injector);

        return injector;

    }

    protected abstract String getPasswordSeed();

    protected abstract Class<? extends CustomerDao> getCustomerDaoClass();

    public abstract Module getAppModule();
}