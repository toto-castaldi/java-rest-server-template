package com.github.totoCastaldi.restServer.plugin;

import com.github.totoCastaldi.restServer.RestServerConf;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * Created by toto on 28/11/15.
 */
public class MashapePlugin implements Plugin {

    private final String configKey;

    public MashapePlugin(String configKey) {
        super();
        this.configKey = configKey;
    }

    @Override
    public void config(RestServerConf.Builder builder) {
        builder.addStringConf(configKey);
        builder.add(new AbstractModule() {
            @Override
            protected void configure() {
                bind(String.class).annotatedWith(Names.named(MashapeHeaderCheck.configName)).toInstance(configKey);
            }
        });
        builder.add(MashapeHeaderCheck.class);
    }
}
