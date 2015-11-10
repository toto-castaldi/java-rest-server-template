package com.github.totoCastaldi.restServer.webapp; /**
 * Created by github on 05/12/14.
 */

import com.github.totoCastaldi.commons.GuiceInjector;
import com.github.totoCastaldi.commons.Shutdownable;
import com.github.totoCastaldi.commons.ShutdownableRepository;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Collection;

@Slf4j
public class ShutdownContext implements ServletContextListener {

    public ShutdownContext() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        final ShutdownableRepository shutdownableRepository = GuiceInjector.getIstance().getInstance(ShutdownableRepository.class);
        Collection<Shutdownable> services = shutdownableRepository.getServices();
        for (Shutdownable shutdownable : services) {
            log.info("shutdown of {}", shutdownable);
            shutdownable.shutdown();
        }
    }

}
