package com.github.totoCastaldi.restServer.plugin;

import com.github.totoCastaldi.restServer.RestServerConf;
import com.google.common.collect.Lists;

import javax.ws.rs.container.ContainerRequestFilter;
import java.util.Collection;

/**
 * Created by toto on 28/11/15.
 */
public interface Plugin {

    public void config(RestServerConf.Builder builder);

}
