package com.acme.services;

import com.github.totoCastaldi.restServer.ApiServletContextListener;
import com.github.totoCastaldi.restServer.RestServerConf;
import com.github.totoCastaldi.restServer.plugin.MashapePlugin;

public class ExampleApiServletContextListener extends ApiServletContextListener {

    @Override
    public RestServerConf getAppConf() {
        RestServerConf.Builder builder = RestServerConf.builder();
        builder.add(ExampleResourceViolation.class.getPackage());
        builder.add(new MashapePlugin("MASHAPE-KEY"));
        return builder.build();
    }
}
