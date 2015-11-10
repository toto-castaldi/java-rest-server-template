package com.github.totoCastaldi.restServer;

import com.github.totoCastaldi.commons.GuiceInjector;
import com.github.totoCastaldi.restServer.filter.AuthenticationAndProfileRequestFilter;
import com.github.totoCastaldi.restServer.filter.BasicAuthenticationAbortRequestFilter;
import com.github.totoCastaldi.restServer.filter.CommonResponseHeaderFilter;
import com.github.totoCastaldi.restServer.filter.ProfileCustomerAbortRequestFilter;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import javax.inject.Inject;
import java.util.List;


/**
 * Created by github on 07/10/14.
 */
@Slf4j
public class ApiApplication extends ResourceConfig {

    @Inject
    public ApiApplication(ServiceLocator serviceLocator) {
        String packageName = ApiMapperThrowable.class.getPackage().getName();
        log.debug("resource added {}", packageName);

        packages(packageName);

        final List<Package> packageCollections = Resources.getPackageCollections();
        for (Package packageCollection : packageCollections) {
            packages(packageCollection.getName());
        }

        register(JacksonFeature.class);
        register(ApiMapperThrowable.class);
        register(LoggingFilter.class);

        register(CommonResponseHeaderFilter.class);
        register(AuthenticationAndProfileRequestFilter.class);
        register(BasicAuthenticationAbortRequestFilter.class);
        register(ProfileCustomerAbortRequestFilter.class);


        /** GUICE BRIDGE !!! **/
        GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
        serviceLocator.getService(GuiceIntoHK2Bridge.class).bridgeGuiceInjector(GuiceInjector.getIstance());

    }
}
