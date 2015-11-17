package com.github.totoCastaldi.restServer;

import com.github.totoCastaldi.commons.GuiceInjector;
import com.github.totoCastaldi.commons.MemoryShutdownableRepository;
import com.github.totoCastaldi.commons.ShutdownableRepository;
import com.github.totoCastaldi.restServer.model.CustomerDao;
import com.github.totoCastaldi.restServer.request.BasicAuthorizationRequest;
import com.github.totoCastaldi.restServer.response.ApiErrorMessage;
import com.github.totoCastaldi.restServer.response.ApiResponse;
import com.github.totoCastaldi.restServer.response.ErrorResponseEntry;
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

import java.util.List;

/**
 * Created by github on 05/12/14.
 */
@Slf4j
public abstract class ApiServletContextListener extends GuiceServletContextListener {

    public static Injector injector;

    public ApiServletContextListener(
    ){
    }

    @Override
    protected Injector getInjector() {
        log.info("Getting injector");

        final RestServerConf appModule = getAppConf();

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

                        Iterable<RestServerConf.AppConfKey> confKeys = appModule.getConfKeys();

                        for (RestServerConf.AppConfKey confKey : confKeys) {
                            if (confKey.getClazz() == RestServerConf.TYPE.STRING) bind(String.class).annotatedWith(Names.named(confKey.getName())).toInstance(compositeConfiguration.getString(confKey.getName()));
                        }

                        bind(ApiResponse.class);
                        bind(UserProfile.class);
                        bind(ApiHeaderUtils.class);
                        bind(ApiScheduler.class);
                        bind(TimeProvider.class);
                        bind(ShutdownableRepository.class).to(MemoryShutdownableRepository.class);
                        bind(CustomerDao.class).to(appModule.getCustomerDao());
                        bind(ApiPassword.class).toInstance(new ApiPassword(appModule.getSeed()));
                        bind(ApiErrorMessage.class).toInstance(appModule.getApiErrorMessage());

                        install(factoryModuleBuilder.build(BasicAuthorizationRequest.Factory.class));
                        install(factoryModuleBuilder.build(ErrorResponseEntry.Factory.class));
                        install(factoryModuleBuilder.build(ApiCurrentExecution.Factory.class));
                    }
                }
        );

        modules.addAll(appModule.getModules());

        injector = Guice.createInjector(modules);

        GuiceInjector.setIstance(injector);

        JerseyResources.addPackages(appModule.getPackages());
        JerseyResources.addContainerRequestFilters(appModule.getFilters());

        return injector;

    }

    protected abstract RestServerConf getAppConf();

}