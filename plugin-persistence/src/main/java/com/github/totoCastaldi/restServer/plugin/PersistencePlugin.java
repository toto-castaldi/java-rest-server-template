package com.github.totoCastaldi.restServer.plugin;

import com.github.totoCastaldi.restServer.RestServerConf;

/**
 * Created by goto10 on 29/11/2015.
 */
public class PersistencePlugin implements Plugin {
    @Override
    public void config(RestServerConf.Builder builder) {
        builder.add(new PersistenModule());
    }
}
