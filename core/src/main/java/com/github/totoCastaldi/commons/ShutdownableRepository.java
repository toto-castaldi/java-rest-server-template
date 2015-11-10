package com.github.totoCastaldi.commons;

import java.util.Collection;

/**
 * Created by github on 05/12/14.
 */
public interface ShutdownableRepository {

    void register(Shutdownable shutdownable);

    Collection<Shutdownable> getServices();

}
