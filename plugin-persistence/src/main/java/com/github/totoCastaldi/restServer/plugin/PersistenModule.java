package com.github.totoCastaldi.restServer.plugin;

import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;

/**
 * Created by toto on 14/11/15.
 */
public class PersistenModule extends ServletModule {

    @Override
    protected void configureServlets() {
       install(new JpaPersistModule("jpa-unit"));

       filter("/*").through(PersistFilter.class);
    }

}
