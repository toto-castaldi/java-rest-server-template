package com.github.totoCastaldi.restServer;

import com.github.totoCastaldi.commons.ConfKey;
import com.github.totoCastaldi.commons.GuiceInjector;
import com.github.totoCastaldi.commons.MemoryShutdownableRepository;
import com.github.totoCastaldi.commons.ShutdownableRepository;
import com.github.totoCastaldi.restServer.model.CustomerDao;
import com.github.totoCastaldi.restServer.request.BasicAuthorizationRequest;
import com.github.totoCastaldi.restServer.response.ApiResponse;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceServletContextListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.*;

import javax.ws.rs.container.ContainerRequestFilter;
import java.util.Collection;
import java.util.List;

/**
 * Created by github on 05/12/14.
 */
@Slf4j
public abstract class ApiServletContextListener extends GuiceServletContextListener {

    public static Injector injector;
    private final Collection<? extends Module> pluginModules;

    public ApiServletContextListener(
            Collection<? extends Module> pluginModules
    ){
        this.pluginModules = pluginModules;
    }

    public ApiServletContextListener(
    ){
        this.pluginModules = Lists.newArrayList();
    }

    @Override
    protected Injector getInjector() {
        log.info("Getting injector");

        List<Module> modules = Lists.newArrayList();

        modules.add(

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
                }
        );

        modules.addAll(pluginModules);

        injector = Guice.createInjector(modules);

        GuiceInjector.setIstance(injector);

        JerseyResources.addPackages(getPackages());
        JerseyResources.addContainerRequestFilters(getContainerRequestFilters());

        return injector;

    }

    protected abstract List<Class<? extends ContainerRequestFilter>> getContainerRequestFilters();

    protected abstract List<Package> getPackages();

    protected abstract String getPasswordSeed();

    protected abstract Class<? extends CustomerDao> getCustomerDaoClass();

    public abstract Module getAppModule();
}