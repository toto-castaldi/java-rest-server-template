package com.github.totoCastaldi.restServer.plugin;

import com.github.totoCastaldi.restServer.RestServerConf;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * Created by toto on 28/11/15.
 */
public class MashapePlugin implements Plugin {

    private final String headerConfigKey;
    private final String switchConfigKey;

    public MashapePlugin(
            String headerConfigKey,
            String switchConfigKey
    ) {
        super();
        this.headerConfigKey = headerConfigKey;
        this.switchConfigKey = switchConfigKey;
    }

    @Override
    public void config(RestServerConf.Builder builder) {
        builder.addStringConf(headerConfigKey);
        builder.add(new AbstractModule() {
            @Override
            protected void configure() {
                bind(String.class).annotatedWith(Names.named(MashapeHeaderCheck.headerConfigName)).toInstance(headerConfigKey);
                bind(String.class).annotatedWith(Names.named(MashapeHeaderCheck.switchConfigName)).toInstance(switchConfigKey);
            }
        });
        builder.add(MashapeHeaderCheck.class);
    }
}
